import java.awt.*;
import java.util.ArrayList;

public class PowderParticle extends Particle {
    public PowderParticle(int x, int y, int size, int speedX, int speedY) {
        super(x, y, size, speedX, speedY, Color.GRAY);
    }

    @Override
    public void move(ArrayList<Particle> particles) {
        // Simple random movement
        x += speedX;
        y += speedY;
    }
}
