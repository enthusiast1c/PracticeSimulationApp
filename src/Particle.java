import java.awt.*;
import java.util.ArrayList;

public abstract class Particle {
    protected int x, y, size, speedX, speedY;

    public Particle(int x, int y, int size, int speedX, int speedY) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public void move(ArrayList<Particle> particles) {
        x += speedX;
        y += speedY;
    }

    public void checkBoundaryCollision(int width, int height) {
        if (x < 0 || x > width - size) {
            speedX = -speedX;
        }
        if (y < 0 || y > height - size) {
            speedY = -speedY;
        }
    }

    public abstract void draw(Graphics g);

    public double distanceTo(Particle other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
