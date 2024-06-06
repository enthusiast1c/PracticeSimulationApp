import javax.swing.*;
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
        topPanel.add(new JLabel("Speed:", JLabel.CENTER));
        topPanel.add(new JLabel("Size:",JLabel.CENTER));
        speedSlider = (JSlider) topPanel.add(new JSlider(2, 10, 5));
        sizeSlider = (JSlider) topPanel.add(new JSlider(10, 50, 20));

        //Создание нижней подпанели
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
        // Кнопка для добавления воздушных частиц
        JButton addAirParticleButton = new JButton("Add 15 Air Particles"); // Создание кнопки добавления воздушных частиц
        // Кнопка для добавления частиц порошка
        JButton addPowderParticleButton = new JButton("Add 15 Powder Particles"); // Создание кнопки добавления частиц порошка
        // Кнопка для добавления световых частиц
        JButton addLightParticleButton = new JButton("Add 15 Light Particles"); // Создание кнопки добавления световых частиц
        // Установление цвета кнопки
        addAirParticleButton.setBackground(Color.CYAN);
        addPowderParticleButton.setBackground(Color.MAGENTA);
        addLightParticleButton.setBackground(Color.YELLOW);

        //Добавление кнопок в нижнюю подпанель
        bottomPanel.add(addAirParticleButton);
        bottomPanel.add(addPowderParticleButton);
        bottomPanel.add(addLightParticleButton);
        //Создание авто подпанели
        JPanel autoPanel = new JPanel(new GridLayout(1, 4));
        startAutoSimButton = new JButton("Start Auto Simulation");
        stopAutoSimButton = new JButton("Stop Auto Simulation");
        resumeAutoSimButton = new JButton("Resume Auto Simulation");
        JTextField autoSimDurationField = new JTextField("10");
        //Добавление кнопок в авто подпанель
        autoPanel.add(new JLabel("Auto Sim Duration (s):"));
        autoPanel.add(autoSimDurationField);
        autoPanel.add(startAutoSimButton);
        autoPanel.add(stopAutoSimButton);
        autoPanel.add(resumeAutoSimButton);
        stopAutoSimButton.setVisible(false);
        resumeAutoSimButton.setVisible(false);
        // Добавление элементов на панель управления
        setLayout(new GridLayout(3, 1));
        add(topPanel);
        add(autoPanel);
        add(bottomPanel);



        // Установка действий на кнопки добавления частиц
        addAirParticleButton.addActionListener(e -> addParticles(AirParticle.class));
        addPowderParticleButton.addActionListener(e -> addParticles(PowderParticle.class));
        addLightParticleButton.addActionListener(e -> addParticles(LightParticle.class));


        startAutoSimButton.addActionListener(e -> {
            int duration = Integer.parseInt(autoSimDurationField.getText());
            startAutoSim(duration);
            startAutoSimButton.setVisible(false);
            stopAutoSimButton.setVisible(true);
            resumeAutoSimButton.setVisible(true);
        });

        stopAutoSimButton.addActionListener(e -> stopAutoSim());
        resumeAutoSimButton.addActionListener(e -> resumeAutoSim());
    }
    public void resetAutoSimButtons() {
        startAutoSimButton.setVisible(true);
        stopAutoSimButton.setVisible(false);
        resumeAutoSimButton.setVisible(false);
    }

    // Метод для добавления частиц на игровую панель
    private void addParticles(Class<? extends Particle> particleClass) {
        // Добавляем 10 частиц с указанным классом
        for (int i = 0; i < 15; i++) {
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
            for (int i = 0; i < 5; i++) {
                gamePanel.addParticle(new AirParticle(randomX(), randomY(), sizeSlider.getValue(), randomSpeed(), randomSpeed()));
            }
            for (int i = 0; i < 10; i++) {
                gamePanel.addParticle(new LightParticle(randomX(), randomY(), sizeSlider.getValue(), randomSpeed(), randomSpeed()));
            }
            for (int i = 0; i < 1; i++) {
                gamePanel.addParticle(new PowderParticle(randomX(), randomY(), sizeSlider.getValue(), randomSpeed(), randomSpeed()));
            }

            elapsedAutoSimTime += 2;
        }
    }
}