package GameClasses;

public class HealthPack extends GameObject
{
    int radius = 10;
    int health;

    public HealthPack(int x, int y)
    {
        this(x, y, 1);
    }

    public HealthPack(int x, int y, int health)
    {
        super(x, y);
        this.health = health;
    }
}
