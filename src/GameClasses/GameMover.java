package GameClasses;

import Guns.Bullet;
import Zombies.Zombie;

import java.util.ArrayList;

public abstract class GameMover extends GameObject {
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    public static double collisionRadius = 40;
    boolean up = false;
    boolean down = false;
    boolean left = false;
    boolean right = false;
    protected int speed = 5;

    private long lastTime = System.currentTimeMillis();

    public GameMover(int x, int y) {
        super(x, y);
    }

    public void collidedWith(GameMover mover, double angle) {
        if (this instanceof Player) {
            ((Player) this).changeHealth(
                    -(((Zombie) mover).getDamage() + ((Player) this).state.getWave()));
        }
    }

    public void move() {
        int dx = 0;
        int dy = 0;

        if (up) dy -= 1;
        if (down) dy += 1;
        if (left) dx -= 1;
        if (right) dx += 1;

        double l = Math.sqrt(dx * dx + dy * dy);

        dx = (int) Math.round(dx / l * speed);
        dy = (int) Math.round(dy / l * speed);

        this.x += dx;
        this.y += dy;

        if (this.x < WIDTH / 2) this.x = WIDTH / 2;
        if (this.y < HEIGHT / 2) this.y = HEIGHT / 2;
        if (this.x > GamePanel.PANEL_WIDTH - WIDTH / 2) this.x = GamePanel.PANEL_WIDTH - WIDTH / 2;
        if (this.y > GamePanel.PANEL_HEIGHT - HEIGHT / 2) this.y = GamePanel.PANEL_HEIGHT - HEIGHT / 2;

        if (this instanceof Player player) {
            if (player.mouseX != 0) {
                double rdx = player.mouseX - x;
                double rdy = player.mouseY - y;
                this.rotation = Math.atan2(rdy, rdx);
            }

            long time = System.currentTimeMillis() - lastTime;
            if (player.reload && time > 1500) {
                player.selectedGun.reload();
                lastTime = System.currentTimeMillis();
            }

            ArrayList<Bullet> removeList = new ArrayList<>();
            for (Bullet bullet : player.bullets) {
                bullet.move();
                if (bullet.x < 0 || bullet.x > GamePanel.PANEL_WIDTH) {
                    removeList.add(bullet);
                } else if (bullet.y < 0 || bullet.y > GamePanel.PANEL_HEIGHT) {
                    removeList.add(bullet);
                }
            }
            removeList.forEach(player.bullets::remove);
            if (player.shoot) {
                player.selectedGun.shoot(player.x, player.y, player.rotation, player.bullets);
            }
        }
    }
}
