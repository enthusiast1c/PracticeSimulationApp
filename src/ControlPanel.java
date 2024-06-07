import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// Класс для создания панели управления
public class ControlPanel extends JPanel {
    private final JSlider speedSlider; // Слайдер для установки скорости частиц
    private final JSlider sizeSlider; // Слайдер для установки размера частиц
    private final GamePanel gamePanel; // Панель игры, на которую будут добавляться частицы
    private final Timer autoSimTimer;
    private boolean autoSimRunning;
    private int autoSimDuration;
    private int elapsedAutoSimTime;
    private final JButton startAutoSimButton;
    private final JButton stopAutoSimButton;
    private final JButton resumeAutoSimButton;
    private final Random random = new Random(); // Генератор случайных чисел

    // Конструктор класса
    public ControlPanel(GamePanel gamePanel) {
        this.autoSimTimer = new Timer(2000, new ControlPanel.AutoSimAction());
        this.autoSimRunning = false;
        this.autoSimDuration = 0;
        this.elapsedAutoSimTime = 0;

        this.gamePanel = gamePanel;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        //Создание верхней подпанели
        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        //Создание и добавление слайдеров в верхнюю подпанель
        JLabel speedLabel = new JLabel("Particle Speed:", JLabel.CENTER);
        speedLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        speedLabel.setForeground(Color.ORANGE);
        JLabel sizeLabel = new JLabel("Particle Size:",JLabel.CENTER);
        sizeLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        sizeLabel.setForeground(Color.ORANGE);
        topPanel.add(speedLabel);
        topPanel.add(sizeLabel);
        speedSlider = (JSlider) topPanel.add(new JSlider(2, 10, 5));
        sizeSlider = (JSlider) topPanel.add(new JSlider(10, 50, 25));
        topPanel.setBackground(Color.DARK_GRAY);
        speedSlider.setBackground(Color.DARK_GRAY);
        sizeSlider.setBackground(Color.DARK_GRAY);
        //Создание нижней подпанели
        JPanel bottomPanel = new JPanel(new GridLayout(1, 5));
        JLabel amountLabel = new JLabel("Amount of particles:",SwingConstants.CENTER);
        amountLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        amountLabel.setForeground(Color.ORANGE);
        JTextField amountParticleField = new JTextField("15");
        amountParticleField.setBackground(Color.LIGHT_GRAY);
        amountParticleField.setFont(new Font("Consolas", Font.BOLD, 18));
        // Кнопка для добавления воздушных частиц
        JButton addAirParticleButton = new JButton("Create Air Particles"); // Создание кнопки добавления воздушных частиц
        // Кнопка для добавления частиц порошка
        JButton addPowderParticleButton = new JButton("Create Powder Particles"); // Создание кнопки добавления частиц порошка
        // Кнопка для добавления световых частиц
        JButton addLightParticleButton = new JButton("Create Light Particles"); // Создание кнопки добавления световых частиц
        // Установление цвета кнопки
        addAirParticleButton.setBackground(Color.ORANGE);
        addPowderParticleButton.setBackground(Color.ORANGE);
        addLightParticleButton.setBackground(Color.ORANGE);

        //Добавление кнопок в нижнюю подпанель
        bottomPanel.add(amountLabel);
        bottomPanel.add(amountParticleField);
        bottomPanel.add(addAirParticleButton);
        bottomPanel.add(addPowderParticleButton);
        bottomPanel.add(addLightParticleButton);
        bottomPanel.setBackground(Color.DARK_GRAY);
        //Создание авто подпанели
        JPanel autoPanel = new JPanel(new GridLayout(1, 3));
        startAutoSimButton = new JButton("Start Auto Simulation");
        startAutoSimButton.setForeground(Color.DARK_GRAY);
        startAutoSimButton.setBackground(Color.ORANGE);
        stopAutoSimButton = new JButton("Stop Auto Simulation");
        stopAutoSimButton.setForeground(Color.DARK_GRAY);
        stopAutoSimButton.setBackground(Color.ORANGE);
        resumeAutoSimButton = new JButton("Resume Auto Simulation");
        resumeAutoSimButton.setForeground(Color.DARK_GRAY);
        resumeAutoSimButton.setBackground(Color.ORANGE);

        JTextField autoSimDurationField = new JTextField("10");
        autoSimDurationField.setBackground(Color.LIGHT_GRAY);
        autoSimDurationField.setFont(new Font("Consolas", Font.BOLD, 18));
        JLabel autoSimLabel = new JLabel("Auto Sim Duration (s):",SwingConstants.CENTER);
        autoSimLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        autoSimLabel.setForeground(Color.ORANGE);
        JButton addWaterParticleButton = new JButton("Put out the fire");
        addWaterParticleButton.setBackground(Color.ORANGE);
        //Добавление кнопок в авто подпанель
        autoPanel.add(autoSimLabel);
        autoPanel.add(autoSimDurationField);
        autoPanel.add(startAutoSimButton);
        autoPanel.add(stopAutoSimButton);
        autoPanel.add(resumeAutoSimButton);
        autoPanel.add(addWaterParticleButton);
        stopAutoSimButton.setVisible(false);
        resumeAutoSimButton.setVisible(false);
        autoPanel.setBackground(Color.DARK_GRAY);

        // Добавление элементов на панель управления
        setLayout(new GridLayout(3, 1));
        add(topPanel);
        add(autoPanel);
        add(bottomPanel);

        // Установка действий на кнопки добавления частиц
        addAirParticleButton.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(amountParticleField.getText());
                if (amount <= 1000 && amount > 0 ) {
                    addParticles(amount,AirParticle.class);
                } else JOptionPane.showMessageDialog(gamePanel, "The number of Air Particles entered is incorrect. Please try again!", "Error!", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gamePanel, "Invalid input data. Please try again!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        });
        addPowderParticleButton.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(amountParticleField.getText());
                if (amount <= 100 && amount > 0 ) {
                    addParticles(amount,PowderParticle.class);
                } else JOptionPane.showMessageDialog(gamePanel, "The number of Powder Particles entered is incorrect. Please try again!", "Error!", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gamePanel, "Invalid input data. Please try again!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        });
        addLightParticleButton.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(amountParticleField.getText());
                if (amount <= 2000 && amount > 0 ) {
                    addParticles(amount,LightParticle.class);
                } else JOptionPane.showMessageDialog(gamePanel, "The number of light particles entered is incorrect. Please try again!", "Error!", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gamePanel, "Invalid input data. Please try again!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        });
        addWaterParticleButton.addActionListener(e -> {
            // Добавляем частицы с указанным классом
            for (int i = 0; i < 20; i++) {
                Particle particle;
                int size = 30;
                int speedX = randomSpeed();
                int speedY = 3;
                int x = randomX();
                int y = 2;

                particle = new WaterParticle(x, y, size, speedX, speedY);

                // Добавление частицы на игровую панель
                gamePanel.addParticle(particle);
            }
        });

        startAutoSimButton.addActionListener(e -> {
            try {
                int limitParticles = 3500;
                if (gamePanel.particles.size() < limitParticles) {
                    int duration = Integer.parseInt(autoSimDurationField.getText());
                    if (duration <= 60 && duration > 1) {
                        startAutoSim(duration);
                        startAutoSimButton.setVisible(false);
                        stopAutoSimButton.setVisible(true);
                    } else
                        JOptionPane.showMessageDialog(gamePanel, "The duration of Auto Sim entered is incorrect. Please try again!", "Error!", JOptionPane.ERROR_MESSAGE);
                }else
                    JOptionPane.showMessageDialog(gamePanel, "The maximum number of particles on the playing field has been exceeded!", "Error!", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gamePanel, "Invalid input data. Please try again!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        });
        stopAutoSimButton.addActionListener(e -> {
            stopAutoSimButton.setVisible(false);
            resumeAutoSimButton.setVisible(true);
        });
        resumeAutoSimButton.addActionListener(e -> {
            stopAutoSimButton.setVisible(true);
            resumeAutoSimButton.setVisible(false);
        });

        stopAutoSimButton.addActionListener(e -> stopAutoSim());
        resumeAutoSimButton.addActionListener(e -> resumeAutoSim());

        // Установка шрифта для кнопок
        Border border = new LineBorder(Color.DARK_GRAY, 3);
        Font buttonFont = new Font("Consolas", Font.BOLD, 16);
        addWaterParticleButton.setFont(buttonFont);
        addWaterParticleButton.setBorder(border);
        addAirParticleButton.setFont(buttonFont);
        addAirParticleButton.setBorder(border);
        addPowderParticleButton.setFont(buttonFont);
        addPowderParticleButton.setBorder(border);
        addLightParticleButton.setFont(buttonFont);
        addLightParticleButton.setBorder(border);
        startAutoSimButton.setFont(buttonFont);
        startAutoSimButton.setBorder(border);
        stopAutoSimButton.setFont(buttonFont);
        stopAutoSimButton.setBorder(border);
        resumeAutoSimButton.setFont(buttonFont);
        resumeAutoSimButton.setBorder(border);
    }
    public void resetAutoSimButtons() {
        startAutoSimButton.setVisible(true);
        stopAutoSimButton.setVisible(false);
        resumeAutoSimButton.setVisible(false);
    }

    // Метод для добавления частиц на игровую панель
    private void addParticles(int amount, Class<? extends Particle> particleClass) {
        gamePanel.setSize(sizeSlider.getValue());
        try {
            int limitParticles = 3500;
            if (gamePanel.particles.size() < limitParticles){
                // Добавляем частицы с указанным классом
                for (int i = 0; i < amount; i++) {
                    Particle particle;
                    int size = sizeSlider.getValue();
                    int speedX = randomSpeed();
                    int speedY = randomSpeed();
                    int x = randomX();
                    int y = randomY();

                    // Создание частицы в зависимости от указанного класса
                    if (particleClass == AirParticle.class) {
                        particle = new AirParticle(x, y, size, speedX, speedY);
                    } else if (particleClass == PowderParticle.class) {
                        particle = new PowderParticle(x, y, size, speedX, speedY);
                    } else {
                        particle = new LightParticle(x, y, size, speedX, speedY);
                    }
                    // Добавление частицы на игровую панель
                    gamePanel.addParticle(particle);
                }
            }else JOptionPane.showMessageDialog(gamePanel, "The maximum number of particles on the playing field has been exceeded!", "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Метод для генерации случайной координаты по X
    private int randomX() {
        return random.nextInt(gamePanel.getWidth() - 40) + 20;
    }

    // Метод для генерации случайной координаты по Y
    private int randomY() {
        return random.nextInt(gamePanel.getHeight() - 40) + 20;
    }

    // Метод для генерации случайной скорости
    private int randomSpeed() {
        int speed = speedSlider.getValue();
        return random.nextInt(speed * 2 + 1) - speed;
    }

    public void startAutoSim(int duration) {
        this.autoSimDuration = duration;
        this.elapsedAutoSimTime = 0;
        this.autoSimRunning = true;
        this.autoSimTimer.start();
    }

    public void stopAutoSim() {
        this.autoSimRunning = false;
        this.autoSimTimer.stop();
    }

    public void resumeAutoSim() {
        this.autoSimRunning = true;
        this.autoSimTimer.start();
    }
    public void resetAutoSim() {
        this.autoSimRunning = false;
        this.autoSimTimer.stop();
        resetAutoSimButtons();
    }

    private class AutoSimAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (elapsedAutoSimTime >= autoSimDuration) {
                resetAutoSim();
                return;
            }
            try {
                int limitParticles = 3500;
                if (gamePanel.particles.size() < limitParticles) {
                    for (int i = 0; i < 5; i++) {
                        gamePanel.addParticle(new AirParticle(randomX(), randomY(), sizeSlider.getValue(), randomSpeed(), randomSpeed()));
                    }
                    for (int i = 0; i < 10; i++) {
                        gamePanel.addParticle(new LightParticle(randomX(), randomY(), sizeSlider.getValue(), randomSpeed(), randomSpeed()));
                    }
                    if (elapsedAutoSimTime % 5 == 0) {
                        for (int i = 0; i < 1; i++) {
                            gamePanel.addParticle(new PowderParticle(randomX(), randomY(), sizeSlider.getValue(), randomSpeed(), randomSpeed()));
                        }
                    }
                    elapsedAutoSimTime += 2;
                }else JOptionPane.showMessageDialog(gamePanel, "The maximum number of particles on the playing field has been exceeded!", "Error!", JOptionPane.ERROR_MESSAGE);
            }catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}