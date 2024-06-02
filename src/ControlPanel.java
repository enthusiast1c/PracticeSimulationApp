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
        this.setLayout(new GridLayout(4, 2));

        speedSlider = new JSlider(1, 20, 10);
        sizeSlider = new JSlider(1, 20, 10);

        addAirParticleButton = new JButton("Add 10 Air Particles");
        addPowderParticleButton = new JButton("Add 10 Powder Particles");
        addLightParticleButton = new JButton("Add 10 Light Particles");

        add(new JLabel("Speed:"));
        add(speedSlider);
        add(new JLabel("Size:"));
        add(sizeSlider);
        add(addAirParticleButton);
        add(addPowderParticleButton);
        add(addLightParticleButton);

        addAirParticleButton.addActionListener(e -> addParticles(AirParticle.class));
        addPowderParticleButton.addActionListener(e -> addParticles(PowderParticle.class));
        addLightParticleButton.addActionListener(e -> addParticles(LightParticle.class));
    }

    private void addParticles(Class<? extends Particle> particleClass) {
        for (int i = 0; i < 10; i++) {
            Particle particle;
            int size = sizeSlider.getValue();
            int speedX = randomSpeed();
            int speedY = randomSpeed();
            int x = randomX();
            int y = randomY();

            if (particleClass == AirParticle.class) {
                particle = new AirParticle(x, y, size, speedX, speedY);
            } else if (particleClass == PowderParticle.class) {
                particle = new PowderParticle(x, y, size, speedX, speedY);
            } else {
                particle = new LightParticle(x, y, size, speedX, speedY);
            }

            gamePanel.addParticle(particle);
        }
    }

    private int randomX() {
        return random.nextInt(gamePanel.getWidth() - 40) + 20;
    }

    private int randomY() {
        return random.nextInt(gamePanel.getHeight() - 40) + 20;
    }

    private int randomSpeed() {
        int speed = speedSlider.getValue();
        return random.nextInt(speed * 2 + 1) - speed;
    }
}
