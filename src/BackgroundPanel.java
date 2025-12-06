import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Панель, которая рисует фоновое изображение.
 * Изображение масштабируется, чтобы заполнить всю область панели.
 */
public class BackgroundPanel extends JPanel {

    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        // Загружаем изображение из папки ресурсов
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            this.backgroundImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.err.println("Фоновое изображение не найдено: " + imagePath);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Рисуем изображение, масштабируя его под размер панели
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}