import java.io.*;

public class FileDataSource implements DataSource 
{
    private String name;

    public FileDataSource(String name) 
    {
        this.name = name;
    }

    @Override
    public void writeData(String data) 
    {
        try (FileWriter writer = new FileWriter(name)) 
        {
            writer.write(data);
            System.out.println("   [FileDataSource] Data written to " + name);
        } catch (IOException e) 
        {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    @Override
    public String readData() 
    {
        StringBuilder buffer = new StringBuilder();
        File file = new File(name);
        try (FileReader reader = new FileReader(file)) 
        {
            char[] temp = new char[(int) file.length()];
            reader.read(temp);
            return new String(temp);
        } catch (IOException e) 
        {
            System.err.println("Error reading file: " + e.getMessage());
            return "";
        }
    }
}