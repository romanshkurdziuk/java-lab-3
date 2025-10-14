import java.util.Date;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception 
    {

        /*ApparatusListStorage storageList = new ApparatusListStorage();
        storageList.addApparatus(new HangGlider(101, "Athlete", new Date(), 150000.0, 10.5, 90));        
        storageList.addApparatus(new HangGlider(102, "Windrider", new Date(System.currentTimeMillis() - 1000000000), 185000.0, 11.2, 100));

        ApparatusMapStorage storageMap = new ApparatusMapStorage();
        storageMap.addApparatus(new HangGlider(205, "Sky-King", new Date(), 250000.0, 12.0, 110));
        storageMap.addApparatus(new HangGlider(201, "Falcon", new Date(System.currentTimeMillis() - 2000000000), 210000.0, 11.8, 105));

        printItems(storageList.getAll());
        printItems(storageMap.getAll());*/

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

    public static void printItems(List<HangGlider> items)
    {
        for (HangGlider item : items)
        {
            System.out.println(item); 
            System.out.println("----------------------------------------"); 
        }
        System.out.println("=========== End of Report ===========");
    }

}