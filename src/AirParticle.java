import java.awt.*;
import java.util.ArrayList;

public class AirParticle extends Particle {
    private PowderParticle attachedPowderParticle;
    private double angle;
    private double angularSpeed;

    public AirParticle(int x, int y, int size, int speedX, int speedY) {
        super(x, y, size, speedX, speedY);
        this.angle = Math.random() * 2 * Math.PI;
        this.angularSpeed = 0.05; // скорость вращения
    }

    @Override
    public void move(ArrayList<Particle> particles) {
        if (attachedPowderParticle == null) {
            findAndAttachToPowderParticle(particles);
        }
        if (attachedPowderParticle != null) {
            angle += angularSpeed;
            x = attachedPowderParticle.getX() + (int) (Math.cos(angle) * 30);
            y = attachedPowderParticle.getY() + (int) (Math.sin(angle) * 30);
        } else {
            super.move(particles);
        }
    }

    private void findAndAttachToPowderParticle(ArrayList<Particle> particles) {
        Particle nearestPowderParticle = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Particle particle : particles) {
            if (particle instanceof PowderParticle) {
                double distance = distanceTo(particle);
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestPowderParticle = particle;
                }
            }
        }

        if (nearestPowderParticle != null && nearestPowderParticle instanceof PowderParticle) {
            PowderParticle powderParticle = (PowderParticle) nearestPowderParticle;
            if (powderParticle.addAttachedAirParticle(this)) {
                this.attachedPowderParticle = powderParticle;
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x, y, size, size);
    }
}
