package Zombies;

import GameClasses.Player;

import javax.swing.*;

public class AcidSpittingZombie extends Zombie {
    public static int explosionRadius = 150;

    long spitTime = 4000;

    long timer;
    long startTime = System.currentTimeMillis();

    public AcidSpittingZombie(int x, int y, Player player) {
        super(x, y, player);
        this.bulletsToDie = 1;
        this.speed = 3;
        this.damage = 2;

        this.icon = new ImageIcon("res/acid_spitting_zombie.png");
    }

    @Override
    public void move()
    {
        super.move();
        timer = System.currentTimeMillis() - startTime;
        if (timer > spitTime)
        {
            spit();
            startTime = System.currentTimeMillis();
        }
    }

    public void spit()
    {
        int x = this.x + (int) Math.round(Math.cos(rotation) * 50);
        int y = this.y + (int) Math.round(Math.sin(rotation) * 50);
        AcidSpit as = new AcidSpit(x, y, rotation);
        player.acidSpits.add(as);
        player.bullets.add(as);
    }
}
