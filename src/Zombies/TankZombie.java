package Zombies;

import GameClasses.Player;

import javax.swing.*;

public class TankZombie extends Zombie {
    public TankZombie(int x, int y, Player player) {
        super(x, y, player);
        this.bulletsToDie = 5;
        this.speed = 2;
        this.damage = 5;

        this.icon = new ImageIcon("res/tank_zombie.png");
    }
}
