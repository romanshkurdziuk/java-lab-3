import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;


@XmlSeeAlso({HangGlider.class})
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
    
    protected FlyingApparatus() 
    {
        // JAXB needs a no-arg constructor
    }

    @XmlElement
    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @XmlElement
    public String getModel()
    {
        return this.model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    @XmlElement
    public Date getProductionDate()
    {
        return this.productionDate;
    }

    public void setProductionDate(Date productionDate)
    {
        this.productionDate = productionDate;
    }
    
    @XmlElement
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
