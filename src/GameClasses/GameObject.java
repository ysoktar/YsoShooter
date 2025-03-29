package GameClasses;

import java.io.Serializable;

public abstract class GameObject implements Serializable {
    protected int x;
    protected int y;
    protected double rotation;

    public GameObject(int x, int y) {
        this(x, y, 0);
    }

    public GameObject(int x, int y, int rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }
}
