import java.util.Date;

public class App 
{
    public static void main(String[] args) throws Exception 
    {
       HangGlider hangGlider1 = new HangGlider(101, "ATLANT", new Date(), 150000.0, 10.5, 90);
       System.out.println(hangGlider1);
    }
}
