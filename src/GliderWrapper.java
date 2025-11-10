import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "gliders")
public class GliderWrapper 
{
    private List<HangGlider> gliders;

    @XmlElement(name = "glider")
    public List<HangGlider> getGliders()
    {
        return gliders;
    }

    public void setGliders(List<HangGlider> gliders)
    {
        this.gliders = gliders;
    }
}
