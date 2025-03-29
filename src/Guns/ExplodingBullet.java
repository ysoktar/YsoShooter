package Guns;

public class ExplodingBullet extends Bullet {
    public static int radius = 150;

    public ExplodingBullet(int x, int y, double rotation) {
        super(x, y, rotation, false);
    }
}
