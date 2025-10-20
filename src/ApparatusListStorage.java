import java.util.List;
import java.util.ArrayList;

public class ApparatusListStorage extends ApparatusStorage<HangGlider>
{
    private final List<HangGlider> items = new ArrayList<>();

    @Override
    public void addApparatus(HangGlider apparatus)
    {
        items.add(apparatus);
    }

    @Override
    public List<HangGlider> getAll()
    {
        return items;
    }

    @Override
    public HangGlider getByID(int ID)
    {
        for (HangGlider item : items)
        {
            if (item.getId() == ID)
            {
                return item;
            }
        }
        return null;
    }

    @Override
    public void removeByID(int ID)
    {
        if(items.removeIf(item -> item.getId() == ID))
        System.out.println("[SUCCESS] Hang Glider with " + ID + "ID was successfuly removed");
    }

    @Override
    public void clear()
    {
        items.clear();
    }
}
