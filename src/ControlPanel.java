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
    private final Timer autoSimTimer; // Таймер для автоматической симуляции
    private boolean autoSimRunning; // Флаг для проверки работы авто симуляции
    private int autoSimDuration; // Продолжительность авто симуляции
    private int elapsedAutoSimTime; // Прошедшее время авто симуляции
    private final JButton startAutoSimButton; // Кнопка для запуска авто симуляции
    private final JButton stopAutoSimButton; // Кнопка для остановки авто симуляции
    private final JButton resumeAutoSimButton; // Кнопка для возобновления авто симуляции
    private final Random random = new Random(); // Генератор случайных чисел

    // Конструктор класса
    public ControlPanel(GamePanel gamePanel) {
        this.autoSimTimer = new Timer(2000, new ControlPanel.AutoSimAction()); // Инициализация таймера для авто симуляции
        this.autoSimRunning = false; // Изначально авто симуляция не запущена
        this.autoSimDuration = 0; // Изначально продолжительность авто симуляции равна 0
        this.elapsedAutoSimTime = 0; // Изначально прошедшее время авто симуляции равно 0

        this.gamePanel = gamePanel;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Создание верхней подпанели
        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        // Создание слайдеров
        JLabel speedLabel = new JLabel("Particle Speed:", JLabel.CENTER); // Метка для слайдера скорости
        JLabel sizeLabel = new JLabel("Particle Size:", JLabel.CENTER); // Метка для слайдера размера
        speedLabel.setForeground(Color.ORANGE);
        sizeLabel.setForeground(Color.ORANGE);
        // Добавление меток и слайдеров в верхнюю подпанель
        topPanel.add(speedLabel);
        topPanel.add(sizeLabel);
        speedSlider = (JSlider) topPanel.add(new JSlider(2, 10, 5)); // Создание и добавление слайдера скорости
        sizeSlider = (JSlider) topPanel.add(new JSlider(10, 50, 25)); // Создание и добавление слайдера размера
        topPanel.setBackground(Color.DARK_GRAY);
        speedSlider.setBackground(Color.DARK_GRAY);
        sizeSlider.setBackground(Color.DARK_GRAY);

        // Создание нижней подпанели
        JPanel bottomPanel = new JPanel(new GridLayout(1, 5));
        JLabel amountLabel = new JLabel("Amount of particles:", SwingConstants.CENTER); // Метка для ввода количества частиц
        JTextField amountParticleField = new JTextField("15"); // Поле ввода количества частиц
        amountLabel.setForeground(Color.ORANGE);
        amountParticleField.setBackground(Color.LIGHT_GRAY);
        // Кнопка для добавления воздушных частиц
        JButton addAirParticleButton = new JButton("Create Air Particles"); // Создание кнопки добавления воздушных частиц
        // Кнопка для добавления частиц порошка
        JButton addPowderParticleButton = new JButton("Create Powder Particles"); // Создание кнопки добавления частиц пороха
        // Кнопка для добавления световых частиц
        JButton addLightParticleButton = new JButton("Create Light Particles"); // Создание кнопки добавления световых частиц
        // Установление цвета кнопок
        addAirParticleButton.setBackground(Color.ORANGE);
        addPowderParticleButton.setBackground(Color.ORANGE);
        addLightParticleButton.setBackground(Color.ORANGE);
        // Добавление метки, поля ввода и кнопок в нижнюю подпанель
        bottomPanel.add(amountLabel);
        bottomPanel.add(amountParticleField);
        bottomPanel.add(addAirParticleButton);
        bottomPanel.add(addPowderParticleButton);
        bottomPanel.add(addLightParticleButton);
        bottomPanel.setBackground(Color.DARK_GRAY);

        // Создание панели для авто симуляции
        JPanel autoPanel = new JPanel(new GridLayout(1, 3));
        startAutoSimButton = new JButton("Start Auto Simulation"); // Кнопка для запуска авто симуляции
        startAutoSimButton.setForeground(Color.DARK_GRAY);
        startAutoSimButton.setBackground(Color.ORANGE);
        stopAutoSimButton = new JButton("Pause Auto Simulation"); // Кнопка для паузы авто симуляции
        stopAutoSimButton.setForeground(Color.DARK_GRAY);
        stopAutoSimButton.setBackground(Color.ORANGE);
        resumeAutoSimButton = new JButton("Resume Auto Simulation"); // Кнопка для возобновления авто симуляции
        resumeAutoSimButton.setForeground(Color.DARK_GRAY);
        resumeAutoSimButton.setBackground(Color.ORANGE);
        JTextField autoSimDurationField = new JTextField("10"); // Поле ввода продолжительности авто симуляции
        autoSimDurationField.setBackground(Color.LIGHT_GRAY);
        JLabel autoSimLabel = new JLabel("Auto Sim Duration (s):", SwingConstants.CENTER); // Метка для ввода продолжительности авто симуляции
        autoSimLabel.setForeground(Color.ORANGE);
        JButton addWaterParticleButton = new JButton("Put out the fire"); // Кнопка для добавления водяных частиц
        addWaterParticleButton.setBackground(Color.ORANGE);
        // Добавление метки, поля ввода и кнопок в панель авто симуляции
        autoPanel.add(autoSimLabel);
        autoPanel.add(autoSimDurationField);
        autoPanel.add(startAutoSimButton);
        autoPanel.add(stopAutoSimButton);
        autoPanel.add(resumeAutoSimButton);
        autoPanel.add(addWaterParticleButton);
        autoPanel.setBackground(Color.DARK_GRAY);
        // Установка видимости кнопок авто симуляции
        stopAutoSimButton.setVisible(false);
        resumeAutoSimButton.setVisible(false);

        // Добавление верхней, нижней и панели авто симуляции на панель управления
        setLayout(new GridLayout(3, 1));
        add(topPanel);
        add(autoPanel);
        add(bottomPanel);

        // Установка действий на кнопки добавления частиц света
        addAirParticleButton.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(amountParticleField.getText()); // Получение введенного количества частиц
                if (amount <= 1000 && amount > 0) { // Проверка на допустимый ввод
                    addParticles(amount, AirParticle.class); // Добавление воздушных частиц
                } else {
                    JOptionPane.showMessageDialog(gamePanel, "The number of Air Particles entered is incorrect. Please try again!", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gamePanel, "Invalid input data. Please try again!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        });
        // Добавление слушателя событий для кнопки добавления частиц пороха
        addPowderParticleButton.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(amountParticleField.getText()); // Получение введенного количества частиц
                if (amount <= 100 && amount > 0) { // Проверка на допустимый ввод
                    addParticles(amount, PowderParticle.class); // Добавление частиц порошка
                } else {
                    JOptionPane.showMessageDialog(gamePanel, "The number of Powder Particles entered is incorrect. Please try again!", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gamePanel, "Invalid input data. Please try again!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        });
        // Добавление слушателя событий для кнопки добавления световых частиц
        addLightParticleButton.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(amountParticleField.getText()); // Получение введенного количества частиц
                if (amount <= 2000 && amount > 0) { // Проверка на допустимый ввод
                    addParticles(amount, LightParticle.class); // Добавление световых частиц
                } else {
                    JOptionPane.showMessageDialog(gamePanel, "The number of light particles entered is incorrect. Please try again!", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gamePanel, "Invalid input data. Please try again!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        });
        // Добавление слушателя событий для кнопки добавления водяных частиц
        addWaterParticleButton.addActionListener(e -> {
            // Добавляем частицы с указанным классом и параметрами
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
        // Добавление слушателя событий для кнопки запуска авто симуляции
        startAutoSimButton.addActionListener(e -> {
            try {
                int limitParticles = 3500;
                if (gamePanel.particles.size() < limitParticles) {
                    int duration = Integer.parseInt(autoSimDurationField.getText());
                    if (duration <= 60 && duration > 1) { // Проверка на допустимы ввод
                        startAutoSim(duration);
                        startAutoSimButton.setVisible(false);
                        stopAutoSimButton.setVisible(true);
                    } else
                        JOptionPane.showMessageDialog(gamePanel, "The duration of Auto Sim entered is incorrect. Please try again!", "Error!", JOptionPane.ERROR_MESSAGE);
                } else
                    JOptionPane.showMessageDialog(gamePanel, "The maximum number of particles on the playing field has been exceeded!", "Error!", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gamePanel, "Invalid input data. Please try again!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        });
        // Добавление слушателя событий для кнопки остановки авто симуляции
        stopAutoSimButton.addActionListener(e -> {
            stopAutoSimButton.setVisible(false);
            resumeAutoSimButton.setVisible(true);
        });
        // Добавление слушателя событий для кнопки возобновления авто симуляции
        resumeAutoSimButton.addActionListener(e -> {
            stopAutoSimButton.setVisible(true);
            resumeAutoSimButton.setVisible(false);
        });
        // Добавление слушателя событий для кнопки остановки авто симуляции
        stopAutoSimButton.addActionListener(e -> stopAutoSim());
        // Добавление слушателя событий для кнопки возобновления авто симуляции
        resumeAutoSimButton.addActionListener(e -> resumeAutoSim());

        // Установка шрифта для кнопок
        Font buttonFont = new Font("Consolas", Font.BOLD, 16);
        sizeLabel.setFont(buttonFont);
        speedLabel.setFont(buttonFont);
        amountLabel.setFont(buttonFont);
        amountParticleField.setFont(buttonFont);
        addWaterParticleButton.setFont(buttonFont);
        addAirParticleButton.setFont(buttonFont);
        addPowderParticleButton.setFont(buttonFont);
        addLightParticleButton.setFont(buttonFont);
        autoSimDurationField.setFont(buttonFont);
        autoSimLabel.setFont(buttonFont);
        startAutoSimButton.setFont(buttonFont);
        stopAutoSimButton.setFont(buttonFont);
        resumeAutoSimButton.setFont(buttonFont);

        // Установка обводки у кнопок
        Border border = new LineBorder(Color.DARK_GRAY, 3);
        addWaterParticleButton.setBorder(border);
        addAirParticleButton.setBorder(border);
        addPowderParticleButton.setBorder(border);
        addLightParticleButton.setBorder(border);
        startAutoSimButton.setBorder(border);
        stopAutoSimButton.setBorder(border);
        resumeAutoSimButton.setBorder(border);

    }
    // Метод для сброса состояния кнопок авто симуляци
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
            if (gamePanel.particles.size() < limitParticles){ // Проверка на лимит частиц игрового поля
                // Добавляем частицы с указанным классом и параметрами
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
    private int randomX() {return random.nextInt(gamePanel.getWidth() - 40) + 20;}
    // Метод для генерации случайной координаты по Y
    private int randomY() {return random.nextInt(gamePanel.getHeight() - 40) + 20;}
    // Метод для генерации случайной скорости
    private int randomSpeed() {
        int speed = speedSlider.getValue();
        return random.nextInt(speed * 2 + 1) - speed;
    }
    // Метод для запуска авто симуляции
    public void startAutoSim(int duration) {
        this.autoSimDuration = duration;
        this.elapsedAutoSimTime = 0;
        this.autoSimRunning = true;
        this.autoSimTimer.start();
    }
    // Метод для остановки авто симуляции
    public void stopAutoSim() {
        this.autoSimRunning = false;
        this.autoSimTimer.stop();
    }
    // Метод для возобновления авто симуляции
    public void resumeAutoSim() {
        this.autoSimRunning = true;
        this.autoSimTimer.start();
    }
    // Метод для сброса состояния авто симуляции
    public void resetAutoSim() {
        this.autoSimRunning = false;
        this.autoSimTimer.stop();
        resetAutoSimButtons();
    }
    // Внутренний класс для выполнения действий авто симуляции
    private class AutoSimAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Проверяем, достигнут ли лимит времени авто симуляции
            if (elapsedAutoSimTime >= autoSimDuration) {
                // Сбрасываем авто симуляцию
                resetAutoSim();
                return;
            }
            try {
                int limitParticles = 3500;
                // Проверяем, не превышен ли лимит частиц на панели
                if (gamePanel.particles.size() < limitParticles) {
                    // Добавляем воздушные частицы
                    for (int i = 0; i < 5; i++) {
                        gamePanel.addParticle(new AirParticle(randomX(), randomY(), sizeSlider.getValue(), randomSpeed(), randomSpeed()));
                    }
                    // Добавляем световые частицы
                    for (int i = 0; i < 10; i++) {
                        gamePanel.addParticle(new LightParticle(randomX(), randomY(), sizeSlider.getValue(), randomSpeed(), randomSpeed()));
                    }
                    // Добавляем частицы пороха каждые 5 шагов времени авто симуляции
                    if (elapsedAutoSimTime % 5 == 0) {
                        for (int i = 0; i < 1; i++) {
                            gamePanel.addParticle(new PowderParticle(randomX(), randomY(), sizeSlider.getValue(), randomSpeed(), randomSpeed()));
                        }
                    }
                    // Увеличиваем время авто симуляции на 2 единицы
                    elapsedAutoSimTime += 2;
                } else
                    // Показываем сообщение об ошибке, если превышен лимит частиц на панели
                    JOptionPane.showMessageDialog(gamePanel, "The maximum number of particles on the playing field has been exceeded!", "Error!", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                // Бросаем исключение, если возникла ошибка
                throw new RuntimeException(ex);
            }
        }
    }
}