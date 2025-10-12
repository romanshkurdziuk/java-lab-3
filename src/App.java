import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {

        HangGlider glider1 = new HangGlider(101, "Athlete", new Date(), 150000.0, 10.5, 90);
        HangGlider glider2 = new HangGlider(102, "Windrider", new Date(System.currentTimeMillis() - 1000000000), 185000.0, 11.2, 100);

        List<HangGlider> gliders = new ArrayList<>();
        gliders.add(glider1);
        gliders.add(glider2);
        System.out.println("====== Factory Production Report ======");
        for (HangGlider glider : gliders) {
            System.out.println(glider); 
            System.out.println("----------------------------------------"); 
        }
        System.out.println("=========== End of Report ===========");
    }
}