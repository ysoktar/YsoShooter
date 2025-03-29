package Zombies;

import GameClasses.GameMover;
import GameClasses.GamePanel;
import GameClasses.Player;

import javax.swing.*;
import java.awt.*;

public class Zombie extends GameMover {
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    protected int bulletsToDie = 2;
    protected int speed = 4;
    protected int damage = 2;
    protected int points = 10;

    ImageIcon icon;
    Player player;

    private final int sight = 600;


    public Zombie(int x, int y, Player player) {
        super(x, y);
        this.icon = new ImageIcon("res/zombie.png");
        this.player = player;
    }

    @Override
    public void collidedWith(GameMover mover, double angle) {
        angle -= Math.PI / 2;
        int force = 10;
        if (mover instanceof Zombie) {
            force = 8;
        }
        double dx = Math.cos(angle) * force;
        double dy = Math.sin(angle) * force;

        this.x += (int) Math.round(dx);
        this.y += (int) Math.round(dy);
    }

    @Override
    public void move() {
        int dx = player.getX() - this.x;
        int dy = player.getY() - this.y;

        double l = Math.sqrt(dx * dx + dy * dy);

        if (l < sight && l > GameMover.collisionRadius) {
            this.x += (int) Math.round(dx / l * speed);
            this.y += (int) Math.round(dy / l * speed);

            double rdx = player.getX() - this.x;
            double rdy = player.getY() - this.y;
            this.rotation = Math.atan2(rdy, rdx);
        }

        if (this.x < WIDTH / 2) {
            this.x = WIDTH / 2 + 20;
            rotation += Math.PI;
        }
        if (this.y < HEIGHT / 2) {
            this.y = HEIGHT / 2 + 20;
            rotation += Math.PI;
        }
        if (this.x > GamePanel.PANEL_WIDTH - WIDTH / 2) {
            this.x = GamePanel.PANEL_WIDTH - WIDTH / 2 - 20;
            rotation += Math.PI;
        }
        if (this.y > GamePanel.PANEL_HEIGHT - HEIGHT / 2) {
            this.y = GamePanel.PANEL_HEIGHT - HEIGHT / 2 - 20;
            rotation += Math.PI;
        }

        if (l > sight) {
            this.rotation += Math.PI / 10 * (Math.random() - 0.5);

            this.x += (int) Math.round(Math.cos(rotation) * speed);
            this.y += (int) Math.round(Math.sin(rotation) * speed);
        }
    }

    public boolean hit() {
        bulletsToDie--;
        return bulletsToDie == 0;
    }

    public Image getImage() {
        return icon.getImage();
    }

    public int getDamage() {
        return damage;
    }

    public int getPoints() {
        return points;
    }
}
