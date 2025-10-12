import java.util.Date;

public abstract class FlyingApparatus 
{
    protected int id;
    protected String model;
    protected Date productionDate;
    protected double price;

    public FlyingApparatus(int id, String model, Date productionDate, double price)
    {
        this.id = id;
        this.model = model;
        this.productionDate = productionDate;
        this.price = price;
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getModel()
    {
        return this.model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public Date getProductionDate()
    {
        return this.productionDate;
    }

    public void setProductionDate(Date productionDate)
    {
        this.productionDate = productionDate;
    }
    
    public double getPrice()
    {
        return this.price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public abstract String getApparatusType();
}


