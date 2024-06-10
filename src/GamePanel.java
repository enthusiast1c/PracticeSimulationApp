import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import javax.sound.sampled.*;


// Класс для панели игры
public class GamePanel extends JPanel implements ActionListener {
    final ArrayList<Particle> particles; // Список частиц
    private final Random random = new Random(); // Генератор случайных чисел
    private final int initialSpeed; // Начальная скорость частиц
    private final Image texture; // Текстура фона
    private int size; // Размер частиц
    private Clip sound; // Звук тушения

    // Конструктор класса
    public GamePanel(int size, int speed) {
        this.initialSpeed = speed; // Установка начальной скорости
        this.particles = new ArrayList<>(); // Создание списка частиц

        // Таймер для обновления анимации
        Timer timer = new Timer(16, this); // Инициализация таймера с интервалом 16 мс

        // Добавление нескольких типов частиц в начальное состояние
        for (int i = 0; i < 5; i++) {
            addParticle(new AirParticle(400, 300, size, randomSpeed(), randomSpeed()));
            addParticle(new PowderParticle(400, 300, size, randomSpeed(), randomSpeed()));
            addParticle(new LightParticle(400, 300, size, randomSpeed(), randomSpeed()));
        }

        timer.start();
        this.texture = new ImageIcon(Objects.requireNonNull(getClass().getResource("textures/background.png"))).getImage();
        try {
            sound = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(getClass().getResourceAsStream("sounds/extinguishing.wav"))));
            sound.open(inputStream);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для генерации случайной скорости
    private int randomSpeed() {
        int randomValue;
        do {
            randomValue = random.nextInt(this.initialSpeed * 2 + 1) - this.initialSpeed;
        } while (randomValue == 0);
        return randomValue;
    }


    // Метод для добавления частицы в список
    public void addParticle(Particle particle) {
        particles.add(particle);
    }

    // Метод для установки текущего размера из слайдера
    public void setSize(int size) {
        this.size = size;
    }

    // Переопределение метода отрисовки компонента
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (texture != null) {
            g.drawImage(texture, 0, 0, getWidth(), getHeight(), this); // Отрисовка фона
        }

        g.setColor(Color.ORANGE);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1); // Отрисовка оранжевой рамки вокруг панели

        // Отрисовка каждой частицы в списке
        for (Particle particle : particles) {
            particle.draw(g);
        }
    }

    // Обработчик действий таймера
    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<Particle> toRemove = new ArrayList<>(); // Список частиц для удаления
        ArrayList<Particle> toAdd = new ArrayList<>(); // Список частиц для добавления

        for (Particle particle : particles) {
            particle.move(particles); // Перемещение частицы
            particle.checkBoundaryCollision(getWidth(), getHeight()); // Проверка на столкновение с границами

            if (particle instanceof PowderParticle powderParticle) {
                if (powderParticle.canTransformToFire()) { // Проверка на возможность трансформации в огонь
                    toRemove.add(particle); // Удаление частицы пороха
                    toRemove.addAll(powderParticle.getAttachedAirParticles()); // Удаление привязанных воздушных частиц
                    toRemove.addAll(powderParticle.getAttachedLightParticles()); // Удаление привязанных световых частиц
                    toAdd.add(new FireParticle(powderParticle.getX(), powderParticle.getY(), 110, randomSpeed(), randomSpeed())); // Добавление огненной частицы
                }
            }

            if (particle instanceof WaterParticle) {
                ArrayList<Particle> tempToRemove = ((WaterParticle) particle).removeParticles(particles); // Удаление огненных частиц при контакте с водой
                for (Particle p : tempToRemove) {
                    beginPlaying();
                    try {
                        int limitParticles = 3500;
                        if ( particles.size() < limitParticles) {
                            for (int i = 0; i < 5; i++) {
                                toAdd.add(new LightParticle(p.getX(), p.getY(), this.size, randomSpeed(), randomSpeed())); // Добавление световых частиц на место удаленных
                                toAdd.add(new AirParticle(p.getX(), p.getY(), this.size, randomSpeed(), randomSpeed())); // Добавление воздушных частиц на место удаленны
                                toAdd.add(new LightParticle(p.getX(), p.getY(), this.size, randomSpeed(), randomSpeed())); // Дополнительные световые частицы
                            }
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (particle.getY() >= 600) {
                    toRemove.add(particle); // Удаление водяной частицы, если она достигла дна панели
                }
                toRemove.addAll(tempToRemove); // Удаление временных частиц
            }
        }
        particles.removeAll(toRemove); // Удаление всех частиц из списка частиц
        particles.addAll(toAdd); // Добавление новых частиц в список частиц
        repaint(); // Перерисовка панели
    }
    private void beginPlaying() {
        if (sound.isRunning()) {sound.stop();}
        sound.setFramePosition(0);
        sound.start();
    }

}