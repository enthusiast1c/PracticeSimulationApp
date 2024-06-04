import javax.swing.*;
import java.awt.*;

public class ParticleSimulation extends JFrame {
    public ParticleSimulation() {
        setTitle("Particle Simulation");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel gamePanel = new GamePanel();
        ControlPanel controlPanel = new ControlPanel(gamePanel);

        add(gamePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ParticleSimulation frame = new ParticleSimulation();
            frame.setVisible(true);
        });
    }
}
