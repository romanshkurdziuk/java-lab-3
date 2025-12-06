import java.util.Date;

public interface Builder 
{
    Builder setId(int id);
    Builder setModel(String model);
    Builder setProductionDate(Date date);
    Builder setPrice(double price);
    Builder setWingspan(double wingspan);
    Builder setPilotWeightLimit(int weight);
    HangGlider build(); 
}
