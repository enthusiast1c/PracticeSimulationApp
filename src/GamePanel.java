import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

// Класс для панели
public class GamePanel extends JPanel implements ActionListener {
    private final ArrayList<Particle> particles; // Список частиц
    private final Random random = new Random(); // Генератор случайных чисел
    private final int initialSpeed;

    // Конструктор класса
    public GamePanel(int size, int speed) {
        this.initialSpeed = speed;
        this.particles = new ArrayList<>(); // Создание списка частиц
        // Таймер для обновления анимации
        Timer timer = new Timer(16, this); // Инициализация таймера с интервалом 16 мс

        // Добавление нескольких типов частиц в начальное состояние
        for (int i = 0; i < 5; i++) {
            addParticle(new AirParticle(400, 300, size, randomSpeed(), randomSpeed()));
            addParticle(new PowderParticle(400, 300, size, randomSpeed(), randomSpeed()));
            addParticle(new LightParticle(400, 300, size, randomSpeed(), randomSpeed()));
        }

        timer.start(); // Запуск таймера для начала анимации
        setBackground(Color.DARK_GRAY); // Установка цвета фона панели
    }

    // Метод для генерации случайной скорости
    private int randomSpeed() {
        return random.nextInt(this.initialSpeed * 2 + 1) - this.initialSpeed;
    }

    // Метод для добавления частицы в список
    public void addParticle(Particle particle) {
        particles.add(particle);
    }

    // Переопределение метода отрисовки компонента
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        // Отрисовка каждой частицы в списке
        for (Particle particle : particles) {
            particle.draw(g);
        }
    }

    // Обработчик действий таймера
    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<Particle> toRemove = new ArrayList<>();
        ArrayList<Particle> toAdd = new ArrayList<>();

        for (Particle particle : particles) {
            particle.move(particles);
            particle.checkBoundaryCollision(getWidth(), getHeight());

            if (particle instanceof PowderParticle powderParticle) {
                if (powderParticle.canTransformToFire()) {
                    toRemove.add(particle);
                    toRemove.addAll(powderParticle.getAttachedAirParticles());
                    toRemove.addAll(powderParticle.getAttachedLightParticles());
                    toAdd.add(new FireParticle(powderParticle.getX(), powderParticle.getY(), 110, randomSpeed()+5, randomSpeed()+5));
                }
            }
        }

        particles.removeAll(toRemove);
        particles.addAll(toAdd);
        repaint();
    }
}