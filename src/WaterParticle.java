import java.util.ArrayList;
import java.awt.Graphics;
import java.util.Objects;
import javax.swing.ImageIcon;

// Класс, представляющий водяную частицу
public class WaterParticle extends Particle {
    // Конструктор для создания водяной частицы
    public WaterParticle(int x, int y, int size, int speedX, int speedY) {
        super(x, y, size, speedX, speedY);
        // Загрузка текстуры водяной частицы
        this.texture = new ImageIcon(Objects.requireNonNull(getClass().getResource("textures/water(texture).png"))).getImage();
    }


    // Переопределенный метод для отрисовки частицы
    @Override
    public void draw(Graphics g) {
        // Отрисовка изображения текстуры частицы на графическом контексте
        g.drawImage(texture, x, y, size, size, null);
    }

    // Метод для удаления частиц огня
    public ArrayList<Particle> removeParticles(ArrayList<Particle> particles) {
        ArrayList<Particle> removeList = new ArrayList<>(); // Список частиц для удаления
        // Проходим по всем частицам и находим те, которые нужно удалить
        for (Particle p : particles) {
            if (p instanceof FireParticle) { // Если частица является огненной
                double distance = distanceTo(p); // Расстояние до огненной частицы
                int sizeOther = p.getSize(); // Размер огненной частицы
                double minimalDistance = (this.size + sizeOther) / 2.0; // Минимальное расстояние для удаления
                if (distance <= minimalDistance) { // Если расстояние меньше или равно минимальному
                    removeList.add(p); // Добавляем огненную частицу в список для удаления
                }
            }
        }
        return removeList; // Возвращаем список частиц для удаления
    }
}
