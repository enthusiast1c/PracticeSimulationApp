import java.awt.*;
import java.util.ArrayList;

public class FireParticle extends Particle {

    public FireParticle(int x, int y, int size, int speedX, int speedY) {
        super(x, y, size, speedX, speedY);
    }

    @Override
    public void move(ArrayList<Particle> particles) {
        super.move(particles);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, size, size);
    }
}
