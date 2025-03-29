package Zombies;


import Guns.ExplodingBullet;

import java.awt.*;

public class AcidSpit extends ExplodingBullet
{
    public static final Color COLOR = Color.GREEN;
    public int damage = 10;

    public AcidSpit(int x, int y, double rotation)
    {
        super(x, y, rotation);
    }
}
