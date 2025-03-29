package Guns;

import GameClasses.GameObject;

import java.awt.*;

public class Bullet extends GameObject {
    public static Color COLOR = new Color(239, 201, 19);
    protected boolean penetrate;
    int speed = 30;

    public Bullet(int x, int y, double rotation, boolean penetrate) {
        super(x, y);
        this.rotation = rotation;
        this.penetrate = penetrate;
    }

    public Bullet(int x, int y, double rotation) {
        this(x, y, rotation, false);
    }

    public void move() {
        int dx = (int) Math.round(Math.cos(this.rotation) * speed);
        int dy = (int) Math.round(Math.sin(this.rotation) * speed);

        this.x += dx;
        this.y += dy;
    }

    public boolean canPenetrate() {
        return penetrate;
    }
}