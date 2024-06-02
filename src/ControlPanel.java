import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ControlPanel extends JPanel {
    private JSlider speedSlider;
    private JSlider sizeSlider;
    private JButton addAirParticleButton;
    private JButton addPowderParticleButton;
    private JButton addLightParticleButton;
    private GamePanel gamePanel;
    private Random random = new Random();

    public ControlPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.setLayout(new GridLayout(6, 1));

        speedSlider = new JSlider(1, 10, 5);
        sizeSlider = new JSlider(1, 20, 10);

        addAirParticleButton = new JButton("Add Air Particle");
        addPowderParticleButton = new JButton("Add Powder Particle");
        addLightParticleButton = new JButton("Add Light Particle");

        add(new JLabel("Speed:"));
        add(speedSlider);
        add(new JLabel("Size:"));
        add(sizeSlider);
        add(addAirParticleButton);
        add(addPowderParticleButton);
        add(addLightParticleButton);

        addAirParticleButton.addActionListener(e -> addParticle(new AirParticle(randomX(), randomY(), sizeSlider.getValue(), randomSpeed(), randomSpeed())));
        addPowderParticleButton.addActionListener(e -> addParticle(new PowderParticle(randomX(), randomY(), sizeSlider.getValue(), randomSpeed(), randomSpeed())));
        addLightParticleButton.addActionListener(e -> addParticle(new LightParticle(randomX(), randomY(), sizeSlider.getValue(), randomSpeed(), randomSpeed())));
    }

    private void addParticle(Particle particle) {
        gamePanel.addParticle(particle);
    }

    private int randomX() {
        return random.nextInt(gamePanel.getWidth());
    }

    private int randomY() {
        return random.nextInt(gamePanel.getHeight());
    }

    private int randomSpeed() {
        int speed = speedSlider.getValue();
        return random.nextInt(speed * 2 + 1) - speed;
    }
}
