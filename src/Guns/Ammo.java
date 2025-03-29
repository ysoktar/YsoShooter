package Guns;

import GameClasses.GameObject;

public class Ammo extends GameObject {
    public int radius;
    int type;

    public Ammo(int type, int x, int y) {
        this(type, x, y, 30);
    }

    public Ammo(int type, int x, int y, int radius) {
        super(x, y);
        this.type = type;
        this.radius = radius;
    }

    public int getType() {
        return type;
    }
}
