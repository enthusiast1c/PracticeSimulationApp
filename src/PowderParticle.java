import java.awt.*;
import java.util.ArrayList;

public class PowderParticle extends Particle {
    private ArrayList<AirParticle> attachedAirParticles;

    public PowderParticle(int x, int y, int size, int speedX, int speedY) {
        super(x, y, size, speedX, speedY);
        this.attachedAirParticles = new ArrayList<>();
    }

    public boolean addAttachedAirParticle(AirParticle airParticle) {
        if (attachedAirParticles.size() < 10) {
            attachedAirParticles.add(airParticle);
            return true;
        }
        return false;
    }

    @Override
    public void move(ArrayList<Particle> particles) {
        super.move(particles);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillOval(x, y, size, size);
    }

    public ArrayList<AirParticle> getAttachedAirParticles() {
        return attachedAirParticles;
    }
}
