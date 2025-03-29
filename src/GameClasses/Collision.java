package GameClasses;

import Guns.*;
import Zombies.AcidSpit;
import Zombies.AcidSpittingZombie;
import Zombies.CrawlerZombie;
import Zombies.Zombie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Collision implements Serializable {
    private final Player player;
    LinkedList<Zombie> zombies;
    LinkedList<Explosion> explosions;
    LinkedList<Ammo> ammos;

    public Collision(Player player, LinkedList<Zombie> zombies, LinkedList<Explosion> explosions, LinkedList<Ammo> ammos) {
        this.player = player;
        this.zombies = zombies;
        this.explosions = explosions;
        this.ammos = ammos;
    }

    public void detectCollisions() {
        List<Zombie> toHitZ = new ArrayList<>();
        List<Bullet> toRemoveB = new ArrayList<>();
        List<Explosion> toRemoveE = new ArrayList<>();
        List<Ammo> toRemoveA = new ArrayList<>();

        int length = zombies.size();
        Iterator<Zombie> iter = zombies.iterator();
        int i = 1;
        while (iter.hasNext()) {
            Zombie z1 = iter.next();
            boolean dead = false;
            for (Bullet bullet : player.bullets) {
                if (checkBullet(bullet, z1)) {
                    dead = true;
                    if (!bullet.canPenetrate()) {
                        toRemoveB.add(bullet);
                    }
                    break;
                }
            }

            if (!dead) {
                checkCollision(player, z1);
            } else {
                toHitZ.add(z1);
            }

            if (i < length) {
                Iterator<Zombie> iter2 = zombies.listIterator(i++);
                while (iter2.hasNext()) {
                    Zombie z2 = iter2.next();
                    checkCollision(z1, z2);
                }
            }
        }

        for (Explosion e : explosions) {
            for (Zombie z1 : zombies) {
                double distance = Math.sqrt((e.x - z1.x) * (e.x - z1.x) + (e.y - z1.y) * (e.y - z1.y));
                if (distance < e.radius) {
                    toHitZ.add(z1);
                }
            }

            double distance = Math.sqrt((e.x - player.x) * (e.x - player.x) + (e.y - player.y) * (e.y - player.y));
            if (distance < e.radius) {
                player.changeHealth(-e.damage);
            }
            toRemoveE.add(e);
        }

        for (AcidSpit as : player.acidSpits)
        {
            double distance = Math.sqrt((as.x - player.x) * (as.x - player.x) + (as.y - player.y) * (as.y - player.y));
            if (distance < GameMover.collisionRadius)
            {
                player.changeHealth(-as.damage);
            }
        }

        Gun gun = player.selectedGun;
        int gunType = player.gunIndex + 1;
        for (Ammo ammo : ammos) {
            double distance = Math.sqrt((ammo.x - player.x) * (ammo.x - player.x) +
                    (ammo.y - player.y) * (ammo.y - player.y));
            if (distance < ammo.radius && gunType == ammo.getType()) {
                gun.addMagazine();
                toRemoveA.add(ammo);
            }
        }

        for (Zombie z : toHitZ) {
            if (z.hit()) {
                player.changeScore(z.getPoints());
                if (z instanceof AcidSpittingZombie) {
                    explosions.add(new Explosion(z.x, z.y, AcidSpittingZombie.explosionRadius));
                }
                zombies.remove(z);
            }
        }
        for (Bullet b : toRemoveB) {
            if (b instanceof ExplodingBullet) {
                explosions.add(new Explosion(b.x, b.y, ExplodingBullet.radius));
            }
            player.bullets.remove(b);
        }
        for (Explosion e : toRemoveE) {
            explosions.remove(e);
        }
        for (Ammo ammo : toRemoveA) {
            ammos.remove(ammo);
        }
    }

    private void checkCollision(GameMover o1, GameMover o2) {
        double distance = Math.sqrt((o1.x - o2.x) * (o1.x - o2.x) + (o1.y - o2.y) * (o1.y - o2.y));

        double angle = Math.atan2((o1.x - o2.x), (o1.y - o2.y));

        if (distance < GameMover.collisionRadius) {
            o1.collidedWith(o2, angle);
            o2.collidedWith(o1, -angle);
        } else if (o1 instanceof Player && o2 instanceof CrawlerZombie &&
                distance < CrawlerZombie.jumpRadius && !((CrawlerZombie) o2).jumped) {
            ((CrawlerZombie) o2).jump();
        }
    }

    private boolean checkBullet(Bullet bullet, Zombie z) {
        return Math.sqrt((z.x - bullet.x) * (z.x - bullet.x) +
                (z.y - bullet.y) * (z.y - bullet.y)) < GameMover.collisionRadius;
    }
}
