import java.awt.*;
import java.util.ArrayList;

public class AirParticle extends Particle {
    public AirParticle(int x, int y, int size, int speedX, int speedY) {
        super(x, y, size, speedX, speedY, Color.BLUE);
    }

    @Override
    public void move(ArrayList<Particle> particles) {
        // Simple random movement
        x += speedX;
        y += speedY;
    }
}
