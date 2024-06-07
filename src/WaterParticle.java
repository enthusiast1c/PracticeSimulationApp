import java.awt.*;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Objects;
import javax.swing.ImageIcon;

public class WaterParticle extends Particle{
    double angle = 0.0;
    public WaterParticle(int x, int y, int size, int speedX,int speedY){
        super(x, y, size, speedX, speedY);
        this.texture = new ImageIcon(Objects.requireNonNull(getClass().getResource("textures/air(texture).png"))).getImage();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(texture, x, y, size, size, null);
    }

    public void move(){
        angle += 90;
        if (angle >= 360)
            angle -= 360;
        int moveX = (int) (Math.cos(angle) * 65);
        if (speedY < 10)
            speedY = 10;
        x += moveX;
        y += speedY;
    }

    public ArrayList<Particle> removeParticles(ArrayList<Particle> particles) {
        ArrayList<Particle> removeList = new ArrayList<>();
        for (Particle p: particles){
            if (p instanceof FireParticle) {
                double distance = distanceTo(p);
                int sizeOther = p.getSize();
                double minimalDistance = (this.size + sizeOther) / 2.0;
                if (distance <= minimalDistance){
                    removeList.add(p);
                }
            }
        }
        return removeList;
    }
}
