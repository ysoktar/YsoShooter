package Guns;

import GameClasses.Player;

import java.io.Serializable;
import java.util.Set;

public abstract class Gun implements Serializable {
    final int magazineCapacity;
    final double cooldownSeconds;
    public String name = "Gun";
    long lastTime;
    int bullets;
    int magazines;
    boolean infiniteMagazines;

    public Gun(int magazineCapacity, int magazines, double cooldownSeconds) {
        this.magazineCapacity = magazineCapacity;
        this.magazines = magazines;
        this.cooldownSeconds = cooldownSeconds;

        this.bullets = this.magazineCapacity;
    }

    public Gun(int magazineCapacity, int magazines, int shotsPerMinute) {
        this(magazineCapacity, magazines, 60.0 / shotsPerMinute);
    }

    public Gun(int magazineCapacity, int shotsPerMinute) {
        this(magazineCapacity, -1, shotsPerMinute);
        this.infiniteMagazines = true;
    }

    public boolean reload() {
        if (infiniteMagazines) {
            bullets = magazineCapacity;
            return true;
        }

        if (magazines > 0) {
            magazines--;
            bullets = magazineCapacity;
            return true;
        } else {
            return false;
        }
    }

    public boolean shoot(int x, int y, double rotation, Set<Bullet> bullets) {
        long delay = System.currentTimeMillis() - this.lastTime;
        if (this.bullets > 0 && delay > cooldownSeconds * 1000) {
            this.bullets--;

            int bulletDx = (int) Math.round(Player.WIDTH / 2.0 * Math.cos(rotation));
            int bulletDy = (int) Math.round(Player.HEIGHT / 2.0 * Math.sin(rotation));
            bullets.add(new Bullet(x + bulletDx,
                    y + bulletDy, rotation));
            this.lastTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public void addMagazine() {
        magazines++;
    }

    public String getTotalBullets() {
        if (infiniteMagazines) return "Sonsuz";
        return Integer.toString(bullets + magazines * magazineCapacity);
    }

    public int getBullets() {
        return bullets;
    }
}
