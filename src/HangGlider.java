import java.util.Date;
import java.text.SimpleDateFormat;

public class HangGlider extends FlyingApparatus
{
    private double wingspan;
    private int pilotWeightLimit;

    public HangGlider(int id, String model, Date productionDate, double price, double wingspan, int pilotWeightLimit)
    {
        super(id, model, productionDate, price);
        this.wingspan = wingspan;
        this.pilotWeightLimit = pilotWeightLimit;
    }

    public double getWingspan()
    {
        return this.wingspan;
    }

    public void setWingspan(double wingspan)
    {
        this.wingspan = wingspan;
    }

    public int getPilotWeightLimit()
    {
        return pilotWeightLimit;
    }

    public void setPilotWeightLimit(int pilotWeightLimit)
    {
        this.pilotWeightLimit = pilotWeightLimit;
    }

    @Override
    public String getApparatusType()
    {
        return "Hang glider";
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        String header = String.format("--------[ %s: %s ]--------%n", getApparatusType(), model);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(" > %-22s: %d%n", "ID", id));
        sb.append(String.format(" > %-22s: %s%n", "Production Date", dateFormat.format(productionDate)));
        sb.append(String.format(" > %-22s: %.1fm%n", "Wingspan", wingspan));
        sb.append(String.format(" > %-22s: %dkg%n", "Pilot Weight Limit", pilotWeightLimit));
        sb.append(String.format(" > %-22s: %,.2f RUB", "Price", price));
        
        return header + sb.toString();
    }
}