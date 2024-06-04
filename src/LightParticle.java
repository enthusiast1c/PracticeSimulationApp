import java.awt.*;
import java.util.ArrayList;

// Класс LightParticle, который наследуется от Particle
public class LightParticle extends Particle {
    // Конструктор класса LightParticle, принимающий начальные координаты, размер, скорость по X и по Y
    public LightParticle(int x, int y, int size, int speedX, int speedY) {
        super(x, y, size, speedX, speedY); // Вызов конструктора суперкласса Particle с переданными параметрами
    }

    @Override
    public void move(ArrayList<Particle> particles) {// Переопределенный метод move, обеспечивающий простое линейное движение
        x += speedX; // Обновление координаты x
        y += speedY; // Обновление координаты y
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, size, size); // Рисование желтого круга на переданных координатах
    }
}