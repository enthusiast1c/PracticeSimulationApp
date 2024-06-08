import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

public class ParticleSimulation extends JFrame {
    private final JSlider speedSlider; // Слайдер для установки скорости частиц
    private final JSlider sizeSlider; // Слайдер для установки размера частиц

    // Класс для стартового меню
    public ParticleSimulation() {
        // Установка параметров стартового меню
        setTitle("Particle Simulation Menu");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(5, 1));
        // Создание слайдера для скорости частиц
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

        // Создание слайдера для размера частиц
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

        // Создание кнопки "Start"
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Consolas", Font.BOLD, 20));
        startButton.setBackground(Color.ORANGE);

        // Добавление компонентов на форму стартового окна
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

        // Обработчик нажатия кнопки "Start"
        startButton.addActionListener(e -> {
            int speed = speedSlider.getValue();
            int size = sizeSlider.getValue();
            startSimulation(speed, size);
        });

        setLocationRelativeTo(null);
        // Обработчик закрытия окна
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

    // Метод для запуска симуляции
    private void startSimulation(int speed, int size) {
        // Создание окна для симуляции
        JFrame simulationFrame = new JFrame("Particle Simulation");
        // Создание игровой панели с указанными параметрами скорости и размера частиц
        GamePanel gamePanel = new GamePanel(size, speed);
        // Получение панели с контрольными элементами
        JPanel panel = getjPanel(gamePanel, simulationFrame);

        // Настройка расположения и добавление компонентов на окно симуляции
        simulationFrame.setLayout(new BorderLayout());
        simulationFrame.add(gamePanel, BorderLayout.CENTER);
        simulationFrame.add(panel, BorderLayout.NORTH);
        simulationFrame.setSize(1920, 1080);
        simulationFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        simulationFrame.setVisible(true);
        // Закрытие стартового окна
        dispose();
        // Обработчик закрытия окна симуляции
        simulationFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Отображение диалогового окна подтверждения выхода из приложения
                int confirmed = JOptionPane.showConfirmDialog(
                        simulationFrame,
                        "Are you sure you want to exit the application?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION
                );

                // Закрытие приложения при подтверждении выхода
                if (confirmed == JOptionPane.YES_OPTION) {
                    simulationFrame.dispose();
                    System.exit(0);
                }
            }
        });
    }

    // Метод для создания панели с контрольными элементами
    private static JPanel getjPanel(GamePanel gamePanel, JFrame simulationFrame) {
        // Создание панели управления с переданной игровой панелью
        ControlPanel controlPanel = new ControlPanel(gamePanel);

        // Создание кнопки "Restart"
        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Consolas", Font.BOLD, 20));
        restartButton.setBackground(Color.ORANGE);
        restartButton.addActionListener(e -> {
            // Закрытие текущего окна симуляции и запуск нового экземпляра стартового окна ParticleSimulation
            simulationFrame.dispose();
            new ParticleSimulation().setVisible(true);
        });
        // Создание панели с контрольными элементами
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(controlPanel, BorderLayout.CENTER);
        panel.add(restartButton, BorderLayout.NORTH);
        return panel;
    }

    public static void main(String[] args) {
        // Запуск приложения
        SwingUtilities.invokeLater(() -> {
            ParticleSimulation particleSimulation = new ParticleSimulation();
            particleSimulation.setVisible(true);
        });
    }
}