package Zombies;

import GameClasses.Player;

import javax.swing.*;

public class CrawlerZombie extends Zombie {
    public static double jumpRadius = 175;

    public boolean jumped;
    private final int jumpLength = 120;

    public CrawlerZombie(int x, int y, Player player) {
        super(x, y, player);
        this.bulletsToDie = 1;
        this.speed = 6;
        this.damage = 2;

        this.icon = new ImageIcon("res/crawler_zombie.png");

        points = 6;
    }

    public void jump() {
        double dx = Math.cos(rotation) * jumpLength;
        double dy = Math.sin(rotation) * jumpLength;

        this.x += (int) Math.round(dx);
        this.y += (int) Math.round(dy);
        jumped = true;
    }
}
