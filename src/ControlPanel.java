import javax.swing.*;
import java.awt.*;
import java.util.Random;

// Класс для создания панели управления
public class ControlPanel extends JPanel {
    private JSlider speedSlider; // Слайдер для установки скорости частиц
    private JSlider sizeSlider; // Слайдер для установки размера частиц
    private JButton addAirParticleButton; // Кнопка для добавления воздушных частиц
    private JButton addPowderParticleButton; // Кнопка для добавления частиц порошка
    private JButton addLightParticleButton; // Кнопка для добавления световых частиц
    private GamePanel gamePanel; // Панель игры, на которую будут добавляться частицы
    private Random random = new Random(); // Генератор случайных чисел

    // Конструктор класса
    public ControlPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.setLayout(new GridLayout(4, 2));

        speedSlider = new JSlider(1, 20, 10); // Создание слайдера скорости
        sizeSlider = new JSlider(1, 20, 10); // Создание слайдера размера

        addAirParticleButton = new JButton("Add 10 Air Particles"); // Создание кнопки добавления воздушных частиц
        addPowderParticleButton = new JButton("Add 10 Powder Particles"); // Создание кнопки добавления частиц порошка
        addLightParticleButton = new JButton("Add 10 Light Particles"); // Создание кнопки добавления световых частиц

        // Добавление элементов на панель управления
        add(new JLabel("Speed:"));
        add(speedSlider);
        add(new JLabel("Size:"));
        add(sizeSlider);
        add(addAirParticleButton);
        add(addPowderParticleButton);
        add(addLightParticleButton);

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