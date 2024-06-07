import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

public class ParticleSimulation extends JFrame {
    private final JSlider speedSlider;
    private final JSlider sizeSlider;

    public ParticleSimulation() {
        setTitle("Particle Simulation Menu");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(5, 1));

        speedSlider = new JSlider(2, 10, 5);
        speedSlider.setBackground(Color.DARK_GRAY);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setMinorTickSpacing(2);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        // Создаем Hashtable для цвета меток слайдера скорости
        Hashtable<Integer, JLabel> speedLabels = new Hashtable<>();
        for (int i = speedSlider.getMinimum(); i <= speedSlider.getMaximum(); i += 2) {
            JLabel label = new JLabel(String.valueOf(i));
            label.setForeground(Color.ORANGE); // Устанавливаем оранжевый цвет меток
            speedLabels.put(i, label);
        }
        speedSlider.setLabelTable(speedLabels);
        sizeSlider = new JSlider(10, 50, 25);
        sizeSlider.setBackground(Color.DARK_GRAY);
        sizeSlider.setMajorTickSpacing(5);
        sizeSlider.setMinorTickSpacing(10);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setPaintLabels(true);
        // Создаем Hashtable для цвета меток слайдера размера
        Hashtable<Integer, JLabel> sizeLabels = new Hashtable<>();
        for (int i = sizeSlider.getMinimum(); i <= sizeSlider.getMaximum(); i += 10) {
            JLabel label = new JLabel(String.valueOf(i));
            label.setForeground(Color.ORANGE); // Устанавливаем оранжевый цвет меток
            sizeLabels.put(i, label);
        }
        sizeSlider.setLabelTable(sizeLabels);
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Consolas", Font.BOLD, 20));
        startButton.setBackground(Color.ORANGE);


        JLabel speedLabel = new JLabel("Particle Speed:", JLabel.CENTER);
        speedLabel.setFont(new Font("Consolas", Font.BOLD, 17));
        speedLabel.setForeground(Color.ORANGE);
        add(speedLabel);
        add(speedSlider);
        JLabel sizeLabel = new JLabel("Particle Size:",JLabel.CENTER);
        sizeLabel.setFont(new Font("Consolas", Font.BOLD, 17));
        sizeLabel.setForeground(Color.ORANGE);
        add(sizeLabel);
        add(sizeSlider);
        add(startButton);
        getContentPane().setBackground(Color.DARK_GRAY);


        startButton.addActionListener(e -> {
            int speed = speedSlider.getValue();
            int size = sizeSlider.getValue();
            startSimulation(speed, size);
        });

        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(
                        ParticleSimulation.this,
                        "Are you sure you want to exit the application?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmed == JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                }
            }
        });
    }

    private void startSimulation(int speed, int size) {
        JFrame simulationFrame = new JFrame("Particle Simulation");
        GamePanel gamePanel = new GamePanel(size, speed);
        JPanel panel = getjPanel(gamePanel, simulationFrame);

        simulationFrame.setLayout(new BorderLayout());
        simulationFrame.add(gamePanel, BorderLayout.CENTER);
        simulationFrame.add(panel, BorderLayout.NORTH);
        simulationFrame.setSize(1920, 1080);
        simulationFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        simulationFrame.setVisible(true);
        dispose(); // Close the menu window

        simulationFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(
                        simulationFrame,
                        "Are you sure you want to exit the application?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmed == JOptionPane.YES_OPTION) {
                    simulationFrame.dispose();
                    System.exit(0);
                }
            }
        });
    }

    private static JPanel getjPanel(GamePanel gamePanel, JFrame simulationFrame) {
        ControlPanel controlPanel = new ControlPanel(gamePanel);

        // Add restart button
        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Consolas", Font.BOLD, 20));
        restartButton.setBackground(Color.ORANGE);
        restartButton.addActionListener(e -> {
            simulationFrame.dispose();
            new ParticleSimulation().setVisible(true);
        });
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(controlPanel, BorderLayout.CENTER);
        panel.add(restartButton, BorderLayout.NORTH);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ParticleSimulation particleSimulation = new ParticleSimulation();
            particleSimulation.setVisible(true);
        });
    }
}
