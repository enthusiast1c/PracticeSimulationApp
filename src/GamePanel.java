import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    private ArrayList<Particle> particles;
    private Timer timer;

    public GamePanel() {
        this.particles = new ArrayList<>();
        this.timer = new Timer(16, this); // roughly 60 FPS

        // Add initial particles
        addParticle(new AirParticle(50, 50, 10, 2, 3));
        addParticle(new PowderParticle(100, 100, 15, -2, 1));
        addParticle(new LightParticle(150, 150, 20, 3, -2));

        this.timer.start();
    }

    public void addParticle(Particle particle) {
        particles.add(particle);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Particle particle : particles) {
            particle.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Particle particle : particles) {
            particle.move(particles);
            particle.checkBoundaryCollision(getWidth(), getHeight());
        }
        repaint();
    }
}
