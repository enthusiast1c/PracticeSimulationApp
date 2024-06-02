import java.awt.*;
import java.util.ArrayList;

public abstract class Particle {
    protected int x, y, size;
    protected int speedX, speedY;
    protected Color color;

    public Particle(int x, int y, int size, int speedX, int speedY, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speedX = speedX;
        this.speedY = speedY;
        this.color = color;
    }

    public abstract void move(ArrayList<Particle> particles);

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    public void checkBoundaryCollision(int width, int height) {
        if (x < 0 || x > width - size) {
            speedX = -speedX;
        }
        if (y < 0 || y > height - size) {
            speedY = -speedY;
        }
    }
}
