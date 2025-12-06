import java.util.Date;

public class HangGliderBuilder implements Builder 
{
     private int id;
    private String model;
    private Date productionDate;
    private double price;
    private double wingspan;
    private int pilotWeightLimit;

    @Override
    public Builder setId(int id) 
    {
        this.id = id;
        return this; 
    }

    @Override
    public Builder setModel(String model) 
    {
        this.model = model;
        return this;
    }

    @Override
    public Builder setProductionDate(Date date) 
    {
        this.productionDate = date;
        return this;
    }

    @Override
    public Builder setPrice(double price) 
    {
        this.price = price;
        return this;
    }

    @Override
    public Builder setWingspan(double wingspan) 
    {
        this.wingspan = wingspan;
        return this;
    }

    @Override
    public Builder setPilotWeightLimit(int weight) 
    {
        this.pilotWeightLimit = weight;
        return this;
    }
    
    @Override
    public HangGlider build() 
    {
        return new HangGlider(id, model, productionDate, price, wingspan, pilotWeightLimit);
    }
}
