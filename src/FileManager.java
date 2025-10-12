import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager 
{
    public List<HangGlider> readData(String filePath)
    {
        File file = new File(filePath);
        List<HangGlider> items = new ArrayList<>();
        int lineNumber = 0;

        try(Scanner scan = new Scanner(file))
        {
            while(scan.hasNextLine())
            {
                String line = scan.nextLine();
                lineNumber++;
                if (line.trim().isEmpty() || line.trim().startsWith("//"))
                {
                    continue;
                }
                String[] parts = line.split(";");
                if (parts.length != 6)
                {
                    System.err.println("[ERROR] Invalid data at line " + lineNumber + " Wrong number of fields");
                    continue;
                }
                try
                {
                    int ID = Integer.parseInt(parts[0].trim());
                    String model = parts[1].trim();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date productionDate = dateFormat.parse(parts[2].trim());
                    double price = Double.parseDouble(parts[3].trim());
                    double wingspan = Double.parseDouble(parts[4].trim());
                    int pilotWeightLimit = Integer.parseInt(parts[5].trim());
                    items.add(new HangGlider(ID, model, productionDate, price, wingspan, pilotWeightLimit));
                } catch (NumberFormatException e)
                {
                    System.err.println("[ERROR] Invalid number format at line " + lineNumber + " Skipping line: \"" + line + "\"");
                } catch (ParseException e)
                {
                    System.err.println("[ERROR] Invalid date format at line " + lineNumber + ". Skipping line: \"" + line + "\"");
                }
            }
        } catch (FileNotFoundException e)
        {
            System.err.println("[FATAL ERROR] Data file not found at: " + filePath);
        }

        System.out.println("Data loading complete. Successfully loaded " + items.size() + " records.");
        return items;
    }    
}
