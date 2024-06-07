import java.util.ArrayList;
import java.awt.Graphics;
import java.util.Objects;
import javax.swing.ImageIcon;

// Класс представляющий воздушную частицу
public class AirParticle extends Particle {
    private PowderParticle attachedPowderParticle; // Привязанная частица пороха
    private double angle; // Угол вращения
    private final double angularSpeed; // Скорость
    private final double attractionSpeed; // Скорость притяжения к частице пороха


    // Конструктор для создания воздушной частицы
    public AirParticle(int x, int y, int size, int speedX, int speedY) {
        super(x, y, size, speedX, speedY);
        this.texture = new ImageIcon(Objects.requireNonNull(getClass().getResource("textures/air(texture).png"))).getImage();
        this.angle = Math.random() * 2 * Math.PI; // Устанавливаем случайный угол
        this.angularSpeed = 0.05; // Устанавливаем скорость вращения
        this.attractionSpeed = 0.05; // Устанавливаем скорость притяжения
    }

    // Метод для перемещения воздушной частицы
    @Override
    public void move(ArrayList<Particle> particles) {
        if (attachedPowderParticle == null) {
            findAndAttachToPowderParticle(particles); // Ищем и привязываемся к частице пороха
        }
        if (attachedPowderParticle != null) {
            // Плавное перемещение к частице пороха
            angle += angularSpeed; // Увеличиваем угол
            double targetX = attachedPowderParticle.getX() + Math.cos(angle) * 65;
            double targetY = attachedPowderParticle.getY() + Math.sin(angle) * 65;

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

        for (Particle particle : particles) {
            if (particle instanceof PowderParticle) {
                double distance = distanceTo(particle); // Расстояние до частицы пороха
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestPowderParticle = particle;
                }
            }
        }

        if (nearestPowderParticle != null) {
            PowderParticle powderParticle = (PowderParticle) nearestPowderParticle;
            if (powderParticle.addAttachedAirParticle(this)) {
                this.attachedPowderParticle = powderParticle; // Устанавливаем привязанную частицу пороха
            }
        }
    }

    // Метод для отрисовки воздушной частицы
    @Override
    public void draw(Graphics g) {
        g.drawImage(texture, (int)x, (int)y, size, size, null);
    }
}