import java.util.Date;
public class Director 
{
    public void constructBeginnerGlider(Builder builder) 
    {
        builder.setModel("Novice-Start")
               .setPrice(45000.0)
               .setWingspan(9.0)
               .setPilotWeightLimit(80)
               .setProductionDate(new Date());
    }

    public void constructProGlider(Builder builder) 
    {
        builder.setModel("Sky-Master-X")
               .setPrice(350000.0)
               .setWingspan(14.5)
               .setPilotWeightLimit(120)
               .setProductionDate(new Date());
    }
}
