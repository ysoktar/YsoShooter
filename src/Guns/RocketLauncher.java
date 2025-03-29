package Guns;

import GameClasses.Player;

import java.util.Set;

public class RocketLauncher extends Gun {
    public RocketLauncher() {
        super(1, 0, 10);
        name = "Roketatar";
    }

    @Override
    public boolean shoot(int x, int y, double rotation, Set<Bullet> bullets) {
        long delay = System.currentTimeMillis() - this.lastTime;
        if (this.bullets > 0 && delay > cooldownSeconds * 1000) {
            this.bullets--;

            int bulletDx = (int) Math.round(Player.WIDTH / 2.0 * Math.cos(rotation));
            int bulletDy = (int) Math.round(Player.HEIGHT / 2.0 * Math.sin(rotation));
            bullets.add(new ExplodingBullet(x + bulletDx,
                    y + bulletDy, rotation));
            this.lastTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }
}
