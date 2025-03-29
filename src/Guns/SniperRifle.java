package Guns;

import GameClasses.Player;

import java.util.Set;

public class SniperRifle extends Gun {
    public SniperRifle() {
        super(5, 0, 30);
        name = "Keskin Nişancı Tüfeği";
    }

    public boolean shoot(int x, int y, double rotation, Set<Bullet> bullets) {
        long delay = System.currentTimeMillis() - this.lastTime;
        if (this.bullets > 0 && delay > cooldownSeconds * 1000) {
            this.bullets--;

            int bulletDx = (int) Math.round(Player.WIDTH / 2.0 * Math.cos(rotation));
            int bulletDy = (int) Math.round(Player.HEIGHT / 2.0 * Math.sin(rotation));
            bullets.add(new Bullet(x + bulletDx,
                    y + bulletDy, rotation, true));
            this.lastTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }
}
