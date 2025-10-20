import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

public class ApparatusMapStorage extends ApparatusStorage<HangGlider>
{
    private final Map<Integer, HangGlider> items = new TreeMap<>();

    @Override
    public void addApparatus(HangGlider apparatus)
    {
        items.put(apparatus.getId(), apparatus);
    }

    @Override
    public List<HangGlider> getAll()
    {
        return new ArrayList<HangGlider>(items.values());
    }

    @Override
    public HangGlider getByID(int ID)
    {
        return items.get(ID);
    }

    @Override
    public void removeByID(int ID)
    {
        items.remove(ID);
    }

    @Override
    public void clear()
    {
        items.clear();
    }
}
