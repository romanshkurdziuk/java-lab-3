import java.util.List;
import javax.swing.SwingUtilities;
public class App {
    public static void main(String[] args) 
    {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Если не вышло, будет стандартный дизайн
            System.err.println("Nimbus theme not found.");
        }
        ApparatusStorage<HangGlider> storage = new ApparatusMapStorage();
        FileManager fileManager = new FileManager();

        // --- ИСПРАВЛЕНИЕ: Загружаем данные в хранилище перед запуском GUI ---
        // Укажите путь к вашему файлу с данными.
        // Вы можете выбрать любой формат: .txt, .json, .xml
        String dataFilePath = "data.json"; // Например, загружаем из JSON
        List<HangGlider> initialData = fileManager.readDataFromJson(dataFilePath);
        // Добавляем загруженные данные в хранилище
        initialData.forEach(storage::addApparatus);

        SwingUtilities.invokeLater(() -> {
            new MainWindow(storage, fileManager);
        });
    }
}