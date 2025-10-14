import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Menu 
{
    private final ApparatusStorage<HangGlider> storage;
    private final Scanner scanner;  
    private final FileManager fileManager;
    public Menu(ApparatusStorage<HangGlider> storage, FileManager fileManager)
    {
        this.storage = storage;
        this.scanner = new Scanner(System.in);
        this.fileManager = fileManager;
    } 
    public void run()
    {
        while (true)
        {
            displayMenu();
            System.out.println("Enter your choice");
            String choice = scanner.nextLine();
            switch(choice)
            {
                case "1":
                    DisplayAllHangGlinders();
                    break;
                case "2":
                    AddNewHangGlinder();
                    break;
                case "3":
                //
                break;
                case "4":
                //
                break;
                case "0":
                    System.out.println("Exiting the program. Goodbye!");
                    SaveDataANDExit();
                    return;
            }
        }
    }

    private void displayMenu()
    {
        System.out.println("\n====[ FACTORY CONTROL MENU ]====");
        System.out.println("1. Display all hang glinders");
        System.out.println("2. Add a new hang glider");
        System.out.println("3. Edit an existing hang glider");
        System.out.println("4. Remove a hang glider");
        System.out.println("0. Exit and save data to file");
        System.out.println("==============================");
    }

    private void DisplayAllHangGlinders()
    {
        List<HangGlider> items = storage.getAll();

        if (items.isEmpty())
        {
            System.out.println("The storage is empty. No glinders to display");
        }
        else
        {
            printReport(items);
        }
    }

    private void printReport(List<HangGlider> items)
    {
        for (HangGlider item : items)
        {
            System.out.println(item); 
            System.out.println("----------------------------------------");
        }
        System.out.println("=========== End of Report ===========");
    }

    private void AddNewHangGlinder()
    {
        System.out.println("---[ADD NEW HANG GLINDER]---");
        try
        {
            System.out.println("Enter ID: ");
            int ID = Integer.parseInt(scanner.nextLine());
            if (storage.getByID(ID) != null)
            {
                System.out.println("[ERROR] A glinder with this ID already exists");
                return;
            }
            System.out.println("Enter model: ");
            String model = scanner.nextLine();
            System.out.println("Enter production date (yyyy-MM-dd)");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date productionDate = dateFormat.parse(scanner.nextLine());
            System.out.println("Enter price: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.println("Enter wingspan: ");
            double wingspan = Double.parseDouble(scanner.nextLine());
            System.out.println("Enter pilot weight limit: ");
            int pilotWeightLimit = Integer.parseInt(scanner.nextLine());
            storage.addApparatus(new HangGlider(ID, model, productionDate, price, wingspan, pilotWeightLimit));
            System.out.println("Successfully added new hang glider: " + model);
        } catch (ParseException e)
        {
            System.out.println("[ERROR]  Invalid date format. Please use 'yyyy-MM-dd'.");
        } catch (NumberFormatException e) 
        {
            System.err.println("[ERROR] Invalid number format. Please enter a valid number.");
        }
    }
    private void SaveDataANDExit()
    {
        List<HangGlider> items = storage.getAll();
        String filePath = "D:\\JAVA_VS_CODE\\Lab_3\\Lab3_Collections\\data.txt";
        fileManager.writeData(filePath, items);
    }

}
