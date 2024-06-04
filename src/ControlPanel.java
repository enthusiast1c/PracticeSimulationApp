import javax.swing.*;
import java.awt.*;
import java.util.Random;

// Класс для создания панели управления
public class ControlPanel extends JPanel {
    private final JSlider speedSlider; // Слайдер для установки скорости частиц
    private final JSlider sizeSlider; // Слайдер для установки размера частиц
    private final GamePanel gamePanel; // Панель игры, на которую будут добавляться частицы
    private final Random random = new Random(); // Генератор случайных чисел

    // Конструктор класса
    public ControlPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        //Создание верхней подпанели
        JPanel topPanel = new JPanel(new GridLayout(1, 1));
        //Создание и добавление слайдеров в верхнюю подпанель
        topPanel.add(new JLabel("Speed:"));
        speedSlider = (JSlider) topPanel.add(new JSlider(2, 10, 5));
        topPanel.add(new JLabel("Size:"));
        sizeSlider = (JSlider) topPanel.add(new JSlider(2, 20, 10));
        topPanel.add(new JPanel());

        //Создание нижней подпанели
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
        // Кнопка для добавления воздушных частиц
        JButton addAirParticleButton = new JButton("Add 10 Air Particles"); // Создание кнопки добавления воздушных частиц
        // Кнопка для добавления частиц порошка
        JButton addPowderParticleButton = new JButton("Add 10 Powder Particles"); // Создание кнопки добавления частиц порошка
        // Кнопка для добавления световых частиц
        JButton addLightParticleButton = new JButton("Add 10 Light Particles"); // Создание кнопки добавления световых частиц
        //Добавление кнопок в нижнюю подпанель
        bottomPanel.add(addAirParticleButton);
        bottomPanel.add(addPowderParticleButton);
        bottomPanel.add(addLightParticleButton);

        // Добавление элементов на панель управления
        setLayout(new GridLayout(2, 1));
        add(topPanel);
        add(bottomPanel);

        // Установка действий на кнопки добавления частиц
        addAirParticleButton.addActionListener(e -> addParticles(AirParticle.class));
        addPowderParticleButton.addActionListener(e -> addParticles(PowderParticle.class));
        addLightParticleButton.addActionListener(e -> addParticles(LightParticle.class));
    }

    // Метод для добавления частиц на игровую панель
    private void addParticles(Class<? extends Particle> particleClass) {
        // Добавляем 10 частиц с указанным классом
        for (int i = 0; i < 10; i++) {
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
}