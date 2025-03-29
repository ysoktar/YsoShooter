package GameClasses;

import Guns.Ammo;
import Guns.Explosion;
import Zombies.Zombie;

import java.io.Serializable;
import java.util.LinkedList;

public class GameState implements Serializable
{
    final Player player;

    public boolean lost = false;

    public LinkedList<Zombie> zombies = new LinkedList<>();
    public LinkedList<Explosion> explosions = new LinkedList<>();
    public LinkedList<Ammo> ammos = new LinkedList<>();

    boolean running = true;
    private int wave = 1;
    private int unlockedGuns = 1;
    boolean transition;

    public GameState()
    {
        this.player = new Player(this);

        GameWave gw = new GameWave(wave, this.player);
        zombies.addAll(gw.getZombies());
    }

    public void checkState()
    {
        if (zombies.isEmpty() && !transition)
        {
            transition = true;
            running = false;
            player.bullets.clear();
            unlockedGuns++;
        }
    }

    public void nextWave()
    {
        if (transition)
        {
            player.INVINCIBLE = false;
            player.bullets.clear();
            wave++;

            GameWave gw = new GameWave(wave, player);
            zombies.addAll(gw.getZombies());
            ammos.addAll(gw.getAmmos());

            transition = false;
            running = true;
        }
    }

    public void lose()
    {
        running = false;
        lost = true;
    }

    public void esc()
    {
        if (!lost && !transition)
        {
            running = !running;
        }
    }

    public int unlockedGuns()
    {
        return unlockedGuns;
    }

    public void refresh()
    {
        if (running)
        {
            return;
        }
        player.refresh();
        lost = false;
        running = true;
        wave = 1;
        unlockedGuns = 1;

        zombies.clear();
        explosions.clear();
        ammos.clear();

        player.changeGun(unlockedGuns);
        GameWave gw = new GameWave(wave, player);
        zombies.addAll(gw.getZombies());
        ammos.addAll(gw.getAmmos());
    }

    public int getWave()
    {
        return wave;
    }

    public void testZombie(int i)
    {
        if (player.INVINCIBLE)
        {
            return;
        }
        player.INVINCIBLE = true;
        player.bullets.clear();

        GameWave gw = new GameWave(player, i);
        zombies.addAll(gw.getZombies());
        ammos.addAll(gw.getAmmos());

        running = true;
    }
}

