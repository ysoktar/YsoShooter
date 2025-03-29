package GameClasses;

import Guns.Ammo;
import Guns.Bullet;
import Guns.Explosion;
import Zombies.AcidSpit;
import Zombies.Zombie;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;

public class GamePanel extends JPanel implements Runnable {
    public static final int PANEL_WIDTH = 1200;
    public static final int PANEL_HEIGHT = 800;

    private final int fps = 30;

    private final ImageIcon background;

    GameState state;
    Collision collision;
    Player player;

    String saveFile = ".save";

    private long totalTime = 0;
    private int frameCount = 0;
    private double averageFps;

    private Thread thread;

    private BufferedImage image;
    private Graphics2D g;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        setFocusable(true);
        requestFocus();

        this.state = new GameState();
        this.player = state.player;

        collision = new Collision(player, state.zombies, state.explosions, state.ammos);

        GameInput input = new GameInput(state, this);
        addKeyListener(input);
        addMouseMotionListener(input);
        addMouseListener(input);

        background = new ImageIcon("res/background.png");
    }

    @Override
    public void run() {
        image = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        while (true) {
            long start = System.nanoTime();

            gameLoop();

            long time = Math.round((System.nanoTime() - start) / 1000000.0);
            long target = 1000 / fps;
            try {
                if (target > time)
                    Thread.sleep(target - time);
            } catch (InterruptedException e) {
                e.fillInStackTrace();
            }

            totalTime += System.nanoTime() - start;
            frameCount++;
            if (frameCount == fps) {
                averageFps = 1000.0 / ((1.0 * totalTime / frameCount) / 1000000.0);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }

    private void gameLoop() {
        if (state.running)
        {
            gameUpdate();
            gameRender();
        }
        else if (state.lost)
        {
            renderLoseMenu();
        }
        else if (state.transition)
        {
            renderTransition();
        }
        else
        {
            renderMenu();
        }

        repaint();
    }

    private void gameUpdate() {
        player.move();

        for (Zombie z : state.zombies) {
            z.move();
        }

        collision.detectCollisions();

        state.checkState();
    }

    private void gameRender() {
        g.setFont(new Font("Arial", Font.PLAIN, 18));

        g.setColor(Color.DARK_GRAY);
        g.drawImage(background.getImage(), 0, 0, PANEL_WIDTH, PANEL_HEIGHT, this);

        for (Ammo ammo : state.ammos) {
            g.setColor(Color.BLACK);
            g.fillOval(ammo.x - ammo.radius / 2, ammo.y - ammo.radius / 2, ammo.radius, ammo.radius);
            g.setColor(Color.WHITE);
            g.drawString("" + ammo.getType(), ammo.x, ammo.y);
        }

        AffineTransform backup = g.getTransform();
        AffineTransform a;
        for (Zombie z : state.zombies) {
            a = AffineTransform.getRotateInstance(z.rotation, z.x, z.y);
            a.rotate(0, 0, 0);
            g.setTransform(a);
            g.drawImage(z.getImage(), z.x - Zombie.WIDTH / 2, z.y - Zombie.HEIGHT / 2,
                    Zombie.WIDTH, Zombie.HEIGHT, this);
        }
        a = AffineTransform.getRotateInstance(player.rotation, player.x, player.y);
        a.rotate(0, 0, 0);
        g.setTransform(a);
        g.drawImage(player.getImage(), player.x - Player.WIDTH / 2, player.y - Player.HEIGHT / 2,
                Player.WIDTH, Player.HEIGHT, this);
        g.setTransform(backup);

        g.setColor(Color.GREEN);
        g.drawString("Can: " + player.getHealth(), PANEL_WIDTH - 100, 25);
        g.setColor(Color.WHITE);
        g.drawString("Toplam Puan: " + player.getScore(), PANEL_WIDTH - 300, 25);

        g.drawString("(" + (player.gunIndex + 1) + ") " + player.selectedGunName + ": ", 25, 25);
        g.drawString(player.selectedGun.getTotalBullets() + " mermi ",
                45 + 12 * player.lengthOfGunName, 25);

        g.setColor(Bullet.COLOR);
        for (Bullet bullet : player.bullets) {
            if (bullet instanceof AcidSpit)
            {
                g.setColor(AcidSpit.COLOR);
                g.fillOval(bullet.x, bullet.y, 15, 15);
                g.setColor(Bullet.COLOR);
                continue;
            }
            g.fillOval(bullet.x, bullet.y, 10, 10);
        }
        g.setColor(Color.WHITE);

        g.setColor(new Color(255, 255, 255, 64));
        for (Explosion e : state.explosions) {
            e.draw(g);
        }

        g.setColor(Color.WHITE);
        g.drawString("Fps: " + averageFps, 20, PANEL_HEIGHT - 25);
        g.drawString("Total Zombies: " + state.zombies.size(),
                PANEL_WIDTH - 200, PANEL_HEIGHT - 25);
    }

    private void renderLoseMenu() {
        g.setFont(new Font("Arial", Font.BOLD, 70));
        g.setColor(Color.RED);
        g.drawString("You Lost, Press G to start again", 50, PANEL_HEIGHT / 2);
        g.drawString("Press L to load.", 300, PANEL_HEIGHT / 2 + 100);
    }


    private void renderMenu() {
        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.setColor(Color.WHITE);
        g.drawString("You paused, press Esc to start again", 50, PANEL_HEIGHT / 2 - 100);
        g.drawString("Press F to save, L to load.", 200, PANEL_HEIGHT / 2 + 100);
    }

    private void renderTransition()
    {
        gameUpdate();
        gameRender();
        g.setFont(new Font("Arial", Font.BOLD, 45));
        g.setColor(Color.WHITE);

        g.drawString(state.getWave() + ". dalgayı atlattın. Kaydetmek için F e",
                50, PANEL_HEIGHT / 2 - 100);
        g.drawString("devam etmek için C ye bas. (Silah değişterebilirsin)",
                50, PANEL_HEIGHT / 2 );
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void save() {
        if (state.running || state.lost) {
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(state);
            oos.writeObject(collision);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void load() {
        if (!new File(saveFile).exists() || state.running)
            return;
        try {
            FileInputStream fis = new FileInputStream(saveFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            state = (GameState) ois.readObject();
            if (!state.transition) state.running = true;
            collision = (Collision) ois.readObject();
            player = state.player;
            player.clearInputs();

            GameInput input = new GameInput(state, this);
            addKeyListener(input);
            addMouseMotionListener(input);
            addMouseListener(input);

            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}