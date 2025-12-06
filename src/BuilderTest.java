import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BuilderTest {

    @Test
    void testCustomBuild() {
        // Проверяем, что цепочка вызовов (method chaining) работает корректно
        HangGliderBuilder builder = new HangGliderBuilder();
        
        HangGlider glider = builder.setId(100)
                                   .setModel("Custom-X")
                                   .setPrice(999.99)
                                   .setWingspan(15.0)
                                   .build();

        // Проверяем, что созданный объект содержит именно те данные, которые мы дали
        assertEquals(100, glider.getId());
        assertEquals("Custom-X", glider.getModel());
        assertEquals(999.99, glider.getPrice());
        assertEquals(15.0, glider.getWingspan());
    }

    @Test
    void testDirectorBeginnerGlider() {
        // Проверяем работу Директора (рецепт для новичка)
        Director director = new Director();
        HangGliderBuilder builder = new HangGliderBuilder();
        
        // Директор должен заполнить поля стандартными значениями
        director.constructBeginnerGlider(builder);
        HangGlider glider = builder.build();

        assertEquals("Novice-Start", glider.getModel());
        assertEquals(45000.0, glider.getPrice());
        assertEquals(80, glider.getPilotWeightLimit());
    }

    @Test
    void testDirectorProGlider() {
        // Проверяем работу Директора (рецепт для профи)
        Director director = new Director();
        HangGliderBuilder builder = new HangGliderBuilder();

        director.constructProGlider(builder);
        HangGlider glider = builder.build();

        assertEquals("Sky-Master-X", glider.getModel());
        assertEquals(350000.0, glider.getPrice());
    }
}