import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BuilderTest {

    @Test
    void testCustomBuild() 
    {
        HangGliderBuilder builder = new HangGliderBuilder();
        
        HangGlider glider = builder.setId(100)
                                   .setModel("Custom-X")
                                   .setPrice(999.99)
                                   .setWingspan(15.0)
                                   .build();

        assertEquals(100, glider.getId());
        assertEquals("Custom-X", glider.getModel());
        assertEquals(999.99, glider.getPrice());
        assertEquals(15.0, glider.getWingspan());
    }

    @Test
    void testDirectorBeginnerGlider() 
    {
        Director director = new Director();
        HangGliderBuilder builder = new HangGliderBuilder();
        
        director.constructBeginnerGlider(builder);
        HangGlider glider = builder.build();

        assertEquals("Novice-Start", glider.getModel());
        assertEquals(45000.0, glider.getPrice());
        assertEquals(80, glider.getPilotWeightLimit());
    }

    @Test
    void testDirectorProGlider() 
    {
        Director director = new Director();
        HangGliderBuilder builder = new HangGliderBuilder();

        director.constructProGlider(builder);
        HangGlider glider = builder.build();

        assertEquals("Sky-Master-X", glider.getModel());
        assertEquals(350000.0, glider.getPrice());
    }
}