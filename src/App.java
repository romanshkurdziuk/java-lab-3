import java.util.List;

public class App {
    public static void main(String[] args) throws Exception 
    {
        String filePath = "D:\\JAVA_VS_CODE\\Lab_3\\Lab3_Collections\\data.txt";
        ApparatusStorage<HangGlider> items = new ApparatusMapStorage();
        FileManager fileManager = new FileManager();
        List<HangGlider> data = fileManager.readData(filePath);
        for (HangGlider item : data)
        {
            items.addApparatus(item);
        }
        Menu menu = new Menu(items, fileManager);
        menu.run();
    }
}