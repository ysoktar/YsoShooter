package Guns;

import java.awt.*;
import java.io.Serializable;

public class Explosion implements Serializable {
    public int x;
    public int y;
    public int radius;
    public int damage = 15;

    public Explosion(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void draw(Graphics2D g) {
        g.fillOval(x - radius / 2, y - radius / 2, radius, radius);
    }
}
