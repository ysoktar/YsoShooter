package GameClasses;

import Guns.Ammo;
import Zombies.AcidSpittingZombie;
import Zombies.CrawlerZombie;
import Zombies.TankZombie;
import Zombies.Zombie;

import java.util.Collection;
import java.util.LinkedList;

public class GameWave {
    private final LinkedList<Zombie> zombies = new LinkedList<>();
    private final LinkedList<Ammo> ammos = new LinkedList<>();

    public GameWave(int wave, Player player) {
        switch (wave) {
            case 1:
                addZombie(0, 5, player);
                break;
            case 2:
                addZombie(0, 5, player);
                addZombie(1, 1, player);

                addAmmo(2, 5);
                break;
            case 3:
                addZombie(0, 5, player);
                addZombie(1, 2, player);
                addZombie(2, 3, player);

                addAmmo(2, 6);
                addAmmo(3, 8);
                break;
            case 4:
                addZombie(0, 3, player);
                addZombie(1, 2, player);
                addZombie(2, 2, player);
                addZombie(3, 4, player);

                addAmmo(2, 3);
                addAmmo(3, 2);
                addAmmo(4, 6);
                break;
            case 5:
                addZombie(0, 4, player);
                addZombie(1, 3, player);
                addZombie(2, 3, player);
                addZombie(3, 4, player);

                addAmmo(2, 1);
                addAmmo(3, 3);
                addAmmo(4, 2);
                addAmmo(5, 5);
                break;
            default:
                addZombie(3, 10, player);
                addAmmo(2, 5);
                addAmmo(3, 5);
                addAmmo(4, 5);
                addAmmo(5, 5);
        }
    }

    public GameWave(Player player, int i)
    {
        addZombie(0, i, player);
        addZombie(1, i, player);
        addZombie(2, i, player);
        addZombie(3, i, player);
    }

    private void addZombie(int type, int n, Player player) {
        for (int i = 0; i < n; i++) {
            int x = (int) (Math.random() * (GamePanel.PANEL_WIDTH - Zombie.WIDTH)) + Zombie.WIDTH / 2;
            int y = (int) (Math.random() * (GamePanel.PANEL_HEIGHT - Zombie.WIDTH)) + Zombie.WIDTH / 2;
            switch (type) {
                case 0:
                    zombies.add(new Zombie(x, y, player));
                    break;
                case 1:
                    zombies.add(new CrawlerZombie(x, y, player));
                    break;
                case 2:
                    zombies.add(new TankZombie(x, y, player));
                    break;
                case 3:
                    zombies.add(new AcidSpittingZombie(x, y, player));
                    break;
            }
        }
    }

    private void addAmmo(int type, int n) {
        for (int i = 0; i < n; i++) {
            int x = (int) (Math.random() * (GamePanel.PANEL_WIDTH - 30)) + 15;
            int y = (int) (Math.random() * (GamePanel.PANEL_HEIGHT- 30)) + 15;
            switch (type) {
                case 1:
                    ammos.add(new Ammo(1, x, y));
                    break;
                case 2:
                    ammos.add(new Ammo(2, x, y));
                    break;
                case 3:
                    ammos.add(new Ammo(3, x, y));
                    break;
                case 4:
                    ammos.add(new Ammo(4, x, y));
                    break;
            }
        }
    }

    public LinkedList<Zombie> getZombies() {
        return zombies;
    }

    public Collection<Ammo> getAmmos() {
        return ammos;
    }
}
