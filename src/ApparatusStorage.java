import java.util.List;

public abstract class ApparatusStorage<T extends FlyingApparatus> 
{
    public abstract void addApparatus(T apparatus);
    public abstract List<T> getAll();
    public abstract T getByID(int ID);
    public abstract void removeByID(int ID);
    public abstract void clear();
}
