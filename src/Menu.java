import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Comparator;

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
                    editHangGlider();
                    break;
                case "4":
                    removeByID();
                    break;
                case "5":
                    sortAllHangGliders();
                    break;
                case "0":
                    System.out.println("[EXIT] Exiting the program. Goodbye!");
                    SaveDataANDExit();
                    return;
            }
        }
    }

    private void displayMenu()
    {
        System.out.println("\n=====[ FACTORY CONTROL MENU ]=====");
        System.out.println("1. -DISPLAY- all hang gliders");
        System.out.println("2. -ADD- a new hang glider");
        System.out.println("3. -CHANGE- an existing hang glider");
        System.out.println("4. -REMOVE- a hang glider");
        System.out.println("5. -SORT- all hang gliders");
        System.out.println("0. -EXIT- and -SAVE- data to file");
        System.out.println("====================================");
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

    public void printReport(List<HangGlider> items)
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
        System.out.println("----[ADD NEW HANG GLIDER]----");
        try
        {
            System.out.println("Enter ID [DEC]: ");
            int ID = Integer.parseInt(scanner.nextLine());
            if (storage.getByID(ID) != null)
            {
                System.out.println("[ERROR] A glinder with this ID already exists");
                return;
            }
            System.out.println("Enter model [A - Z, a - z]: ");
            String model = scanner.nextLine();
            System.out.println("Enter production date [yyyy-MM-dd]: ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date productionDate = dateFormat.parse(scanner.nextLine());
            System.out.println("Enter price [RUB]: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.println("Enter wingspan [M]: ");
            double wingspan = Double.parseDouble(scanner.nextLine());
            System.out.println("Enter pilot weight limit [KG]: ");
            int pilotWeightLimit = Integer.parseInt(scanner.nextLine());
            storage.addApparatus(new HangGlider(ID, model, productionDate, price, wingspan, pilotWeightLimit));
            System.out.println("[SUCCESS] Added a new hang glider: [ " + model + " ]");
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

    private void removeByID()
    {
        System.out.println("----[REMOVE THE HANG GLIDER]----");
        try{
            System.out.println("Enter ID for remove item: ");
            int ID = Integer.parseInt(scanner.nextLine());
            storage.removeByID(ID);
            System.out.println("[SUCCESS] Hang Glider with [ " + ID + " ] ID was successfuly removed");
        } catch (NumberFormatException e)
        {
            System.err.println("[ERROR] Invalid ID. Please enter a valid number");
        }
    }

    private void editHangGlider()
    {
        System.out.println("----[CHANGE AN EXISTING HANG GLIDER]----");
        System.out.println("Enter the ID of the Hang Glider you want to edit: ");

        int ID = Integer.parseInt(scanner.nextLine());
        HangGlider item = storage.getByID(ID);
        if (item == null)
        {
            System.out.println("[ERROR]: Glider with this id " + ID + " not found.");
        }
        try
        {
            System.out.println("You're changing the hang glider: [ MODEL " + item.getModel() + " ]");
            System.out.println("Enter model [A - Z, a - z]: ");
            String newModel = scanner.nextLine();
            item.setModel(newModel);
            System.out.println("Enter new Date production [yyyy-MM-dd]: ");
            String newDateStr = scanner.nextLine();
            item.setProductionDate(new SimpleDateFormat("yyyy-MM-dd").parse(newDateStr));
            System.out.println("Enter price [RUB]: ");
            item.setPrice(Double.parseDouble(scanner.nextLine()));
            System.out.println("Enter wingspam [M]: ");
            item.setWingspan(Double.parseDouble(scanner.nextLine()));
            System.out.println("Enter weight limit [KG]: ");
            item.setPilotWeightLimit(Integer.parseInt(scanner.nextLine()));
            System.out.println("[SUCCESS] Added a new hang glider: [ " + newModel + " ]");
        } catch (ParseException e)
        {
            System.out.println("[ERROR] not correct date format");
        }
    }
    private void sortAllHangGliders()
    {
        System.out.println("----[SORT HANG GLIDERS]----");
        System.out.println("1. SORT by model [A - Z]");
        System.out.println("2. SORT by date production [Newest first]");
        System.out.println("3. SORT by price [Low to High]");
        System.out.println("4. SORT by wingspam [Low to High]");
        System.out.println("5. SORT by pilot weight limit [Low to High]");
        List<HangGlider> items = storage.getAll();
        String choice = scanner.nextLine();
        switch (choice)
        {
            case "1":
                items.sort(Comparator.comparing(HangGlider::getModel));
                System.out.println("....Sort by model....");
                break;
            case "2":
                items.sort(Comparator.comparing(HangGlider::getProductionDate));
                System.out.println("....Sort by date production....");
                break;
            case "3":
                items.sort(Comparator.comparing(HangGlider::getPrice));
                System.out.println("....Sort by price....");
                break;
            case "4":
                items.sort(Comparator.comparing(HangGlider::getWingspan));
                System.out.println("....Sort by wingspam....");
                break;
            case "5":
                items.sort(Comparator.comparing(HangGlider::getPilotWeightLimit));
                System.out.println("....Sort by pilot weight limit....");
                break;
            default:
                System.out.println("[ERROR] Invalid sorting choice.");
                return;
        }
        printReport(items);
    }

}
