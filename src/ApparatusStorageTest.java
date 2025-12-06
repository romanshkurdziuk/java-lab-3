import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.List;

class ApparatusStorageTest 
{

    private ApparatusStorage<HangGlider> storage;

    @BeforeEach
    void setUp() 
    {
        storage = new ApparatusMapStorage();
    }

    @Test
    void testAddAndGetById() 
    {
        HangGlider glider = new HangGlider(1, "TestModel", new Date(), 100.0, 10.0, 90);

        storage.addApparatus(glider);
        HangGlider retrieved = storage.getByID(1);

        assertNotNull(retrieved, "Glider should be found by ID");
        assertEquals("TestModel", retrieved.getModel(), "Model name should match");
        assertEquals(100.0, retrieved.getPrice(), "Price should match");
    }

    @Test
    void testRemove() 
    {
        HangGlider glider = new HangGlider(2, "ToRemove", new Date(), 200.0, 12.0, 100);
        storage.addApparatus(glider);

        assertNotNull(storage.getByID(2));

        storage.removeByID(2);

        assertNull(storage.getByID(2), "Glider should be null after removal");
    }

    @Test
    void testGetAll() 
    {
        storage.addApparatus(new HangGlider(10, "A", new Date(), 10, 10, 10));
        storage.addApparatus(new HangGlider(20, "B", new Date(), 20, 20, 20));

        List<HangGlider> allItems = storage.getAll();
        assertEquals(2, allItems.size(), "Storage should contain exactly 2 items");
    }
}