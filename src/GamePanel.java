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
        for (int i = 0; i < 10; i++) {
            addParticle(new AirParticle(randomX(), randomY(), 10, randomSpeed(), randomSpeed()));
            addParticle(new PowderParticle(randomX(), randomY(), 15, randomSpeed(), randomSpeed()));
            addParticle(new LightParticle(randomX(), randomY(), 20, randomSpeed(), randomSpeed()));
        }

        this.timer.start();
        setBackground(Color.BLACK);
    }

    private int randomX() {
        return (int) (Math.random() * (getWidth() - 20));
    }

    private int randomY() {
        return (int) (Math.random() * (getHeight() - 20));
    }

    private int randomSpeed() {
        return (int) (Math.random() * 4 - 2);
    }

    public void addParticle(Particle particle) {
        particles.add(particle);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw boundary
        g.setColor(Color.RED);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

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
