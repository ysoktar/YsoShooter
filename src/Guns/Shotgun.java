package Guns;

import GameClasses.Player;

import java.util.Set;

public class Shotgun extends Gun {
    public Shotgun() {
        super(5, 0, 60);
        name = "Pompalı Tüfek";
    }

    @Override
    public boolean shoot(int x, int y, double rotation, Set<Bullet> bullets) {
        long delay = System.currentTimeMillis() - this.lastTime;
        if (this.bullets > 0 && delay > this.cooldownSeconds * 1000) {
            this.bullets--;

            double step = Math.PI / 36;

            double tol = 0.01d;
            for (double rad = -4 * step; rad <= 4 * step + tol; rad += step) {
                int bulletDx = (int) Math.round(Player.WIDTH / 2.0 * Math.cos(rotation + rad));
                int bulletDy = (int) Math.round(Player.HEIGHT / 2.0 * Math.sin(rotation + rad));
                bullets.add(new Bullet(x + bulletDx,
                        y + bulletDy, rotation + rad));
                this.lastTime = System.currentTimeMillis();
            }

            return true;
        }
        return false;
    }
}
