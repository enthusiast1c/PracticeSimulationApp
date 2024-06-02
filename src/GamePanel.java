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
        for (int i = 0; i < 5; i++) {
            addParticle(new AirParticle(400, 300, 10, randomSpeed() + 4, randomSpeed() + 4));
            addParticle(new PowderParticle(400, 300, 10, randomSpeed() + 4, randomSpeed() + 4));
            addParticle(new LightParticle(400, 300, 10, randomSpeed() + 4, randomSpeed() + 4));
        }

        this.timer.start();
        setBackground(Color.BLACK);
    }

    private int getInitialCenterX() {
        return (getWidth() - 20) / 2;
    }

    private int getInitialCenterY() {
        return (getHeight() - 20) / 2;
    }

    private int randomSpeed() {
        return (int) (Math.random() * 6) - 3;
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
