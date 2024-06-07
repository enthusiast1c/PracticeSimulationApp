import java.awt.*;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Objects;
import javax.swing.ImageIcon;


public class LightParticle extends Particle {
    private PowderParticle attachedPowderParticle;
    private double angle;
    private final double angularSpeed;
    private final Image texture;
    private final double attractionSpeed;

    public LightParticle(int x, int y, int size, int speedX, int speedY) {
        super(x, y, size, speedX, speedY);
        this.texture = new ImageIcon(Objects.requireNonNull(getClass().getResource("textures/light(texture).png"))).getImage();
        this.angle = Math.random() * 2 * Math.PI;
        this.angularSpeed = 0.05; // скорость вращения
        this.attractionSpeed = 0.05; // Устанавливаем скорость притяжения
    }

    @Override
    public void move(ArrayList<Particle> particles) {
        if (attachedPowderParticle == null) {
            findAndAttachToPowderParticle(particles);
        }
        if (attachedPowderParticle != null) {
            // Плавное перемещение к частице пороха
            angle += angularSpeed; // Увеличиваем угол
            double targetX = attachedPowderParticle.getX() + Math.cos(angle) * 100;
            double targetY = attachedPowderParticle.getY() + Math.sin(angle) * 100;

            x += (int) ((targetX - x) * attractionSpeed) + attachedPowderParticle.speedX;
            y += (int) ((targetY - y) * attractionSpeed) + attachedPowderParticle.speedY;


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

        if (nearestPowderParticle != null) {
            PowderParticle powderParticle = (PowderParticle) nearestPowderParticle;
            if (powderParticle.addAttachedLightParticle(this)) {
                this.attachedPowderParticle = powderParticle;
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(texture, (int)x, (int)y, size, size, null);
    }
}
