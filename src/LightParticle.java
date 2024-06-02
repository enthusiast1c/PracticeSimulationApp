import java.awt.*;
import java.util.ArrayList;

public class LightParticle extends Particle {
    public LightParticle(int x, int y, int size, int speedX, int speedY) {
        super(x, y, size, speedX, speedY);
    }

    @Override
    public void move(ArrayList<Particle> particles) {
        // Simple linear movement
        x += speedX;
        y += speedY;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, size, size);
    }
}
