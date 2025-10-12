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
    public String toString()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return "Type: " + getApparatusType() +
                "\nID: " + getId() +
                "\nModel: " + getModel() +
                "\nProduction date: " + dateFormat.format(getProductionDate()) +
                "\nCost: " + getPrice() +
                "\nWingspan: " + wingspan + " m" +
                "\nPilot weight limit: " + pilotWeightLimit + " kg";
    }






}