import java.util.ArrayList;
import java.awt.Graphics;
import java.util.Objects;
import javax.swing.ImageIcon;

// Класс, представляющий световую частицу
public class LightParticle extends Particle {
    private PowderParticle attachedPowderParticle; // Привязанная частица пороха
    private double angle; // Угол вращения
    private final double angularSpeed; // Скорость вращения
    private final double attractionSpeed; // Скорость притяжения

    // Конструктор для создания световой частицы
    public LightParticle(int x, int y, int size, int speedX, int speedY) {
        super(x, y, size, speedX, speedY);
        // Загрузка текстуры световой частицы
        this.texture = new ImageIcon(Objects.requireNonNull(getClass().getResource("textures/light(texture).png"))).getImage();
        this.angle = Math.random() * 2 * Math.PI; // Устанавливаем случайный угол
        this.angularSpeed = 0.05; // Устанавливаем скорость вращения
        this.attractionSpeed = 0.05; // Устанавливаем скорость притяжения
    }

    // Переопределенный метод для перемещения частицы
    @Override
    public void move(ArrayList<Particle> particles) {
        if (attachedPowderParticle == null) {
            findAndAttachToPowderParticle(particles); // Ищем и привязываемся к частице пороха
        }
        if (attachedPowderParticle != null) {
            // Плавное перемещение к частице пороха
            angle += angularSpeed; // Увеличиваем угол
            double targetX = attachedPowderParticle.getX() + Math.cos(angle) * 100; //100 - радиус окружности вращения
            double targetY = attachedPowderParticle.getY() + Math.sin(angle) * 100;

            // Обновляем координаты с учетом скорости притяжения и скорости привязанной частицы пороха
            x += (int) ((targetX - x) * attractionSpeed) + attachedPowderParticle.speedX;
            y += (int) ((targetY - y) * attractionSpeed) + attachedPowderParticle.speedY;
        } else {
            super.move(particles); // Если нет привязанной частицы пороха, используем обычное перемещение
        }
    }

    // Метод для поиска и привязки к частице пороха
    private void findAndAttachToPowderParticle(ArrayList<Particle> particles) {
        Particle nearestPowderParticle = null; // Ближайшая частица пороха
        double nearestDistance = Double.MAX_VALUE; // Самое большое возможное расстояние

        // Проходим по всем частицам и находим ближайшую частицу пороха
        for (Particle particle : particles) {
            if (particle instanceof PowderParticle) {
                double distance = distanceTo(particle); // Расстояние до частицы пороха
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestPowderParticle = particle;
                }
            }
        }

        // Если нашли ближайшую частицу пороха, пытаемся к ней привязаться
        if (nearestPowderParticle != null) {
            PowderParticle powderParticle = (PowderParticle) nearestPowderParticle;
            if (powderParticle.addAttachedLightParticle(this)) {
                // Устанавливаем привязанную частицу пороха
                this.attachedPowderParticle = powderParticle;
            }
        }
    }

    // Переопределенный метод для отрисовки частицы
    @Override
    public void draw(Graphics g) {
        // Отрисовка изображения текстуры частицы на графическом контексте
        g.drawImage(texture, x, y, size, size, null);
    }
}
