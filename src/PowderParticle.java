import java.util.ArrayList;
import java.awt.Graphics;
import java.util.Objects;
import javax.swing.ImageIcon;

// Класс, представляющий частицу пороха
public class PowderParticle extends Particle {
    private final ArrayList<AirParticle> attachedAirParticles; // Список привязанных воздушных частиц
    private final ArrayList<LightParticle> attachedLightParticles; // Список привязанных световых частиц

    // Конструктор для создания частицы пороха
    public PowderParticle(int x, int y, int size, int speedX, int speedY) {
        super(x, y, size, speedX, speedY);
        // Загрузка текстуры частицы пороха
        this.texture = new ImageIcon(Objects.requireNonNull(getClass().getResource("textures/powder(texture).png"))).getImage();
        this.attachedAirParticles = new ArrayList<>(); // Инициализация списка привязанных воздушных частиц
        this.attachedLightParticles = new ArrayList<>(); // Инициализация списка привязанных световых частиц
    }

    // Метод для добавления привязанной воздушной частицы
    public boolean addAttachedAirParticle(AirParticle airParticle) {
        if (attachedAirParticles.size() < 10) { // Проверка на количество привязанных частиц воздуха
            attachedAirParticles.add(airParticle);
            return true;
        }
        return false;
    }

    // Метод для добавления привязанной световой частицы
    public boolean addAttachedLightParticle(LightParticle lightParticle) {
        if (attachedLightParticles.size() < 20) { // Проверка на количество привязанных частиц света
            attachedLightParticles.add(lightParticle);
            return true;
        }
        return false;
    }

    // Метод для проверки возможности трансформации в огненную частицу
    public boolean canTransformToFire() {
        return attachedAirParticles.size() == 10 && attachedLightParticles.size() == 20; // Проверка условий трансформации
    }

    // Переопределенный метод для отрисовки частицы
    @Override
    public void draw(Graphics g) {
        // Отрисовка изображения текстуры частицы на графическом контексте
        g.drawImage(texture, (int)x, (int)y, size, size, null);
    }
    // Метод для получения списка привязанных воздушных частиц
    public ArrayList<AirParticle> getAttachedAirParticles() {return attachedAirParticles;}
    // Метод для получения списка привязанных световых частиц
    public ArrayList<LightParticle> getAttachedLightParticles() {
        return attachedLightParticles;
    }
}
