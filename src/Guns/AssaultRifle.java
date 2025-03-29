package Guns;

import GameClasses.Player;

import java.util.Set;

public class AssaultRifle extends Gun {
    public AssaultRifle() {
        super(30, 0, 600);
        name = "Piyade Tüfeği";
    }

    @Override
    public boolean shoot(int x, int y, double rotation, Set<Bullet> bullets) {
        long delay = System.currentTimeMillis() - this.lastTime;
        if (this.bullets > 0 && delay > cooldownSeconds * 1000) {
            this.bullets--;

            double f = Math.random() / Math.nextDown(1.0);
            double spreadRad = Math.PI / 6;
            double randomRad = -spreadRad * (1.0 - f) + spreadRad * f;

            int bulletDx = (int) Math.round(Player.WIDTH / 2.0 * Math.cos(rotation + randomRad));
            int bulletDy = (int) Math.round(Player.HEIGHT / 2.0 * Math.sin(rotation + randomRad));
            bullets.add(new Bullet(x + bulletDx,
                    y + bulletDy, rotation + randomRad));
            this.lastTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }
}
