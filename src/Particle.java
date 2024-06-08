import java.awt.*;
import java.util.ArrayList;

// Абстрактный класс, представляющий частицу
public abstract class Particle {
    protected int x, y; // Координаты частицы
    protected int size; // Размер частицы
    protected int speedX, speedY;// Скорость частицы по осям X и Y
    protected Image texture; // Изображение текстуры частицы

    // Конструктор класса Particle, инициализирует координаты, размер и скорость частицы
    public Particle(int x, int y, int size, int speedX, int speedY) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speedX = speedX;
        this.speedY = speedY;
    }
    // Метод для получения координаты X частицы
    public int getX() {return x;}
    // Метод для получения координаты Y частицы
    public int getY() {return y;}
    // Метод для получения размера частицы
    public int getSize() {return size;}

    // Метод для перемещения частицы, добавляет скорость к текущим координатам
    public void move(ArrayList<Particle> particles) {
        x += speedX;
        y += speedY;
    }

    // Метод для проверки и обработки столкновения с границами экрана
    public void checkBoundaryCollision(int width, int height) {
        // Проверка и инвертирование скорости при столкновении с границами по оси X
        if (x < 0 || x > width - size) {speedX = -speedX;}
        if (x > width) {x = width - size;}
        if (x < 0) {x = 0;}
        // Проверка и инвертирование скорости при столкновении с границами по оси Y
        if (y < 0 || y > height - size) {speedY = -speedY;}
        if (y > height) {y = height - size;}
        if (y < 0) {y = 0;}
    }
    // Абстрактный метод для отрисовки частицы, должен быть реализован в подклассах
    public abstract void draw(Graphics g);
    // Метод для расчета расстояния до другой частицы
    public double distanceTo(Particle other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
