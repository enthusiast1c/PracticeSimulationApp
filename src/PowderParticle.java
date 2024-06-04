import java.awt.*;
import java.util.ArrayList;

public class PowderParticle extends Particle {
    private final ArrayList<AirParticle> attachedAirParticles; // Список воздушных частиц, к которым прикреплен порох

    public PowderParticle(int x, int y, int size, int speedX, int speedY) {
        super(x, y, size, speedX, speedY);
        this.attachedAirParticles = new ArrayList<>(); // Инициализация списка прикрепленных воздушных частиц
    }

    public boolean addAttachedAirParticle(AirParticle airParticle) {
        if (attachedAirParticles.size() < 10) { // Проверка, что количество прикрепленных частиц не превышает 10
            attachedAirParticles.add(airParticle); // Добавление воздушной частицы к пороху
            return true;
        }
        return false; // Возврат false, если не удалось добавить воздушную частицу из-за превышения лимита
    }

    @Override
    public void move(ArrayList<Particle> particles) {
        super.move(particles); // Переопределенный метод для перемещения пороха
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillOval(x, y, size, size); // Отрисовка частицы пороха в виде серого овала
    }

    public ArrayList<AirParticle> getAttachedAirParticles() {
        return attachedAirParticles; // Метод для получения списка прикрепленных воздушных частиц
    }
}