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
        }
        repaint();
    }
}
