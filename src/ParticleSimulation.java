import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParticleSimulation extends JFrame {
    private final JSlider speedSlider;
    private final JSlider sizeSlider;

    public ParticleSimulation() {
        setTitle("Particle Simulation Menu");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        speedSlider = new JSlider(2, 10, 5);
        sizeSlider = new JSlider(10, 50, 20);
        JButton startButton = new JButton("Start");
        startButton.setBackground(Color.GREEN);

        add(new JLabel("Speed:"));
        add(speedSlider);
        add(new JLabel("Size:"));
        add(sizeSlider);
        add(startButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int speed = speedSlider.getValue();
                int size = sizeSlider.getValue();
                startSimulation(speed, size);
            }
        });

        setLocationRelativeTo(null);
    }

    private void startSimulation(int speed, int size) {
        JFrame simulationFrame = new JFrame("Particle Simulation");
        GamePanel gamePanel = new GamePanel(size, speed);
        JPanel panel = getjPanel(gamePanel, simulationFrame);

        simulationFrame.setLayout(new BorderLayout());
        simulationFrame.add(gamePanel, BorderLayout.CENTER);
        simulationFrame.add(panel, BorderLayout.NORTH);
        simulationFrame.setSize(1920, 1080);
        simulationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        simulationFrame.setVisible(true);
        dispose(); // Close the menu window
    }

    private static JPanel getjPanel(GamePanel gamePanel, JFrame simulationFrame) {
        ControlPanel controlPanel = new ControlPanel(gamePanel);

        // Add restart button
        JButton restartButton = new JButton("Restart");
        restartButton.setBackground(Color.GREEN);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulationFrame.dispose();
                new ParticleSimulation().setVisible(true);
            }
        });
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(controlPanel, BorderLayout.CENTER);
        panel.add(restartButton, BorderLayout.SOUTH);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ParticleSimulation particleSimulation = new ParticleSimulation();
            particleSimulation.setVisible(true);
        });
    }
}
