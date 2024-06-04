import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Objects;
import javax.swing.ImageIcon;

public class PowderParticle extends Particle {
    private final ArrayList<AirParticle> attachedAirParticles;
    private final ArrayList<LightParticle> attachedLightParticles;
    private final Image texture;

    public PowderParticle(int x, int y, int size, int speedX, int speedY) {
        super(x, y, size, speedX, speedY);
        this.texture = new ImageIcon(Objects.requireNonNull(getClass().getResource("textures/powder(texture).png"))).getImage();
        this.attachedAirParticles = new ArrayList<>();
        this.attachedLightParticles = new ArrayList<>();
    }

    public boolean addAttachedAirParticle(AirParticle airParticle) {
        if (attachedAirParticles.size() < 10) {
            attachedAirParticles.add(airParticle);
            return true;
        }
        return false;
    }

    public boolean addAttachedLightParticle(LightParticle lightParticle) {
        if (attachedLightParticles.size() < 20) {
            attachedLightParticles.add(lightParticle);
            return true;
        }
        return false;
    }

    public boolean canTransformToFire() {
        return attachedAirParticles.size() == 10 && attachedLightParticles.size() == 20;
    }

    @Override
    public void move(ArrayList<Particle> particles) {
        super.move(particles);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(texture, (int)x, (int)y, size, size, null);
    }

    public ArrayList<AirParticle> getAttachedAirParticles() {
        return attachedAirParticles;
    }

    public ArrayList<LightParticle> getAttachedLightParticles() {
        return attachedLightParticles;
    }
}
