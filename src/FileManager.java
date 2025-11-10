import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


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

    public void writeData(String filePath, List<HangGlider> items)
    {
        try (FileWriter writer = new FileWriter(filePath))
        {
            writer.write("// Format: ID;Model;ProductionDate(yyyy-MM-dd);Price;Wingspan;PilotWeightLimit\n");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (HangGlider item : items)
            {
                String line = String.join(";", String.valueOf(item.getId()), item.getModel(), dateFormat.format(item.getProductionDate()), String.valueOf(item.getPrice()), String.valueOf(item.getWingspan()), String.valueOf(item.getPilotWeightLimit()));
                writer.write(line + "\n");
            }
            System.out.println("[SUCCESS] YOUR Data is completelely saved to file: [ "+ filePath + " ]");
        } catch (IOException e)
        {
            System.out.println("[ERROR]");
        }
    }

    public void writeDataToJson(String filePath, List<HangGlider> items)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath))
        {
            gson.toJson(items, writer);
            System.out.println("[SUCCESS] Data saved to file [ " + filePath + " ]");
        } catch(IOException e)
        {
            System.err.println("[ERROR] While saving data to JSON file: " + e.getMessage());
        }
    }

    public List<HangGlider> readDataFromJson(String filePath)
    {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath))
        {
            Type listType = new TypeToken<ArrayList<HangGlider>>() {}.getType();
            List<HangGlider> items = gson.fromJson(reader, listType);
            System.out.println("[SUCCESS] Data loated from [ " + filePath + " ]'");
            return items;
        } catch (FileNotFoundException e)
        {
            System.err.println("[ERROR] Data file not found at: " + filePath);
        } catch (IOException e)
        {
            System.err.println("[ERROR] While reading data from Json file" + e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<HangGlider> readDataFromXml(String filePath)
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(GliderWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            GliderWrapper wrapper = (GliderWrapper) unmarshaller.unmarshal(new File(filePath));
            System.out.println("[SUCCESS] Data loaded from: [ " + filePath + " ]");
            return wrapper.getGliders();
        } catch (JAXBException e)
        {
            System.err.println("[ERROR] While reading data from Xml file" + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void writeDataToXml(String filePath, List<HangGlider> items)
    {
        try
        {
            GliderWrapper wrapper = new GliderWrapper();
            wrapper.setGliders(items);

            JAXBContext context = JAXBContext.newInstance(GliderWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Для красивого вывода XML
            marshaller.marshal(wrapper, new File(filePath));
            System.out.println("[SUCCESS] Data saved to XML file: [ " + filePath + " ]");
        } catch (JAXBException e) 
        {
            System.err.println("[ERROR] While writing data to XML file: " + e.getMessage());
        }
    }
}
