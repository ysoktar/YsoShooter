package Guns;

import GameClasses.GameObject;

import java.awt.*;

public class Explosion extends GameObject
{
    public int radius;
    public int damage = 15;

    public Explosion(int x, int y, int radius) {
        super(x, y);

        this.radius = radius;
    }

    public void draw(Graphics2D g) {
        g.fillOval(x - radius / 2, y - radius / 2, radius, radius);
    }
}
