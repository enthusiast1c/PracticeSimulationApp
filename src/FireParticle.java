import java.awt.*;
import java.util.ArrayList;
import java.awt.Image;
import java.util.Objects;
import javax.swing.ImageIcon;
public class FireParticle extends Particle {
    private final Image texture;

    public FireParticle(int x, int y, int size, int speedX, int speedY) {
        super(x, y, size, speedX, speedY);
        this.texture = new ImageIcon(Objects.requireNonNull(getClass().getResource("textures/fire(texture).png"))).getImage();
    }

    @Override
    public void move(ArrayList<Particle> particles) {
        super.move(particles);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(texture, x, y, size, size, null);
    }

    @Override
    public void checkBoundaryCollision(int width, int height) {
        super.checkBoundaryCollision(width, height);
        if(x > width-size){
            x = width - size;
        }
        if(y > height-size){
            y = height - size;
        }
    }
}
