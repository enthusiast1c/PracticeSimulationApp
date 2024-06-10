import java.awt.*;
import java.util.Objects;
import javax.swing.ImageIcon;

// Класс FireParticle, представляющий частицы огня, наследуется от класса Particle
public class FireParticle extends Particle {

    // Конструктор класса FireParticle, инициализирует координаты, размер, скорость и текстуру частицы
    public FireParticle(int x, int y, int size, int speedX, int speedY) {
        super(x, y, size, speedX, speedY);
        // Загрузка текстуры огненной частицы
        this.texture = new ImageIcon(Objects.requireNonNull(getClass().getResource("textures/fire(texture).png"))).getImage();
    }


    // Переопределенный метод для отрисовки частицы
    @Override
    public void draw(Graphics g) {
        // Отрисовка изображения текстуры частицы на графическом контексте
        g.drawImage(texture, x, y, size, size, null);
    }

    // Переопределенный метод для проверки и обработки столкновения с границами экрана
    @Override
    public void checkBoundaryCollision(int width, int height) {
        super.checkBoundaryCollision(width, height); // Вызов метода проверки границ родительского класса
        // Дополнительная проверка и коррекция координат частицы, чтобы она оставалась в пределах экрана
        if(x > width - size) {x = width - size;}
        if(y > height - size) {y = height - size;}
    }
}
