package GameClasses;

import Guns.*;
import Zombies.AcidSpit;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player extends GameMover {
    public boolean INVINCIBLE = false;

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    final GameState state;

    protected boolean shoot;
    protected boolean reload;

    protected int mouseX;
    protected int mouseY;

    public Set<Bullet> bullets = new HashSet<>();
    public Set<AcidSpit> acidSpits = new HashSet<>();
    ArrayList<Gun> inventory = new ArrayList<>();
    Gun selectedGun;
    String selectedGunName;
    int lengthOfGunName;
    int gunIndex = -1;

    private ArrayList<ImageIcon> icons;
    private ImageIcon currentIcon;

    private int health = 100;
    private int score = 0;

    public Player(GameState state) {
        this(GamePanel.PANEL_WIDTH / 2, GamePanel.PANEL_HEIGHT / 2, state);

        icons = new ArrayList<>();
        icons.add(new ImageIcon("res/player_pistol.png"));
        icons.add(new ImageIcon("res/assault_rifle_player.png"));
        icons.add(new ImageIcon("res/shotgun_player.png"));
        icons.add(new ImageIcon("res/sniper_rifle_player.png"));
        icons.add(new ImageIcon("res/rocket_launcher_player.png"));

        inventory.add(new Pistol());
        inventory.add(new AssaultRifle());
        inventory.add(new Shotgun());
        inventory.add(new SniperRifle());
        inventory.add(new RocketLauncher());

        gunIndex = 0;
        selectedGun = inventory.get(gunIndex);
        selectedGunName = selectedGun.name;
        lengthOfGunName = selectedGunName.length();
        currentIcon = icons.get(gunIndex);
    }

    public Player(int x, int y, GameState state) {
        super(x, y);
        this.state = state;
    }

    protected void changeGun(int i) {
        i--;
        if (i == gunIndex) return;
        if (i < inventory.size() && i < state.unlockedGuns()) {
            selectedGun = inventory.get(i);
            selectedGunName = selectedGun.name;
            lengthOfGunName = selectedGunName.length();
            gunIndex = i;
            currentIcon = icons.get(i);
        }
    }

    public void changeGunLeft()
    {
        if (gunIndex > 0)
        {
            changeGun(gunIndex);
        }
    }

    public void changeGunRight()
    {
        if (gunIndex < state.unlockedGuns() - 1)
        {
            changeGun(gunIndex+2);
        }
    }

    public void changeScore(int change) {
        score += change;
    }

    public void changeHealth(int change) {
        if (INVINCIBLE)
        {
            health = 100;
            return;
        }
        health += change;
        if (health <= 0) {
            health = 0;
            state.lose();
        }
        System.out.println("Health: " + health);
    }

    public void clearInputs() {
        up = false;
        down = false;
        left = false;
        right = false;

        shoot = false;
    }

    public void refresh() {
        clearInputs();
        x = GamePanel.PANEL_WIDTH / 2;
        y = GamePanel.PANEL_HEIGHT / 2;
        health = 100;
        score = 0;
        bullets.clear();

        inventory = new ArrayList<>();
        inventory.add(new Pistol());
        inventory.add(new AssaultRifle());
        inventory.add(new Shotgun());
        inventory.add(new SniperRifle());
        inventory.add(new RocketLauncher());

        changeGun(1);
    }

    public Image getImage() {
        return currentIcon.getImage();
    }

    public int getHealth() {
        return health;
    }

    public int getScore()
    {
        return score;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}