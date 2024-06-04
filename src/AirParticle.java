import java.awt.*;
import java.util.ArrayList;

// Класс представляющий воздушную частицу
public class AirParticle extends Particle {
    private PowderParticle attachedPowderParticle; // Привязанная частица пороха
    private double angle; // Угол вращения
    private double angularSpeed; // Скорость вращения

    // Конструктор для создания воздушной частицы
    public AirParticle(int x, int y, int size, int speedX, int speedY) {
        super(x, y, size, speedX, speedY);
        this.angle = Math.random() * 2 * Math.PI; // Устанавливаем случайный угол
        this.angularSpeed = 0.05; // Устанавливаем скорость вращения
    }

    // Метод для перемещения воздушной частицы
    @Override
    public void move(ArrayList<Particle> particles) {
        if (attachedPowderParticle == null) {
            findAndAttachToPowderParticle(particles); // Ищем и привязываемся к частице пороха
        }
        if (attachedPowderParticle != null) {
            angle += angularSpeed; // Увеличиваем угол
            x = attachedPowderParticle.getX() + (int) (Math.cos(angle) * 30); // Перемещаем по x
            y = attachedPowderParticle.getY() + (int) (Math.sin(angle) * 30); // Перемещаем по y
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

        if (nearestPowderParticle != null && nearestPowderParticle instanceof PowderParticle) {
            PowderParticle powderParticle = (PowderParticle) nearestPowderParticle;
            if (powderParticle.addAttachedAirParticle(this)) {
                this.attachedPowderParticle = powderParticle; // Устанавливаем привязанную частицу пороха
            }
        }
    }

    // Метод для отрисовки воздушной частицы
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x, y, size, size); // Рисуем круг в цвете синего
    }
}