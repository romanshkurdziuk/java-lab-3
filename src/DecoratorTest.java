import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DecoratorTest 
{
    static class MockDataSource implements DataSource{
        private String dataInMemory;

        @Override
        public void writeData(String data){
            this.dataInMemory = data;
        }

        @Override
        public String readData() 
        {
            return dataInMemory;
        }
    }

    @Test
    void testEncryptionDecorator() 
    {
        MockDataSource mockSource = new MockDataSource();
        DataSource secureSource = new EncryptionDecorator(mockSource);
        String originalData = "My Secret Data";
        secureSource.writeData(originalData);
        String writtenData = mockSource.readData();
        assertNotEquals(originalData, writtenData, "Data in storage should be encrypted");
        assertNotNull(writtenData);
        String readBackData = secureSource.readData();
        assertEquals(originalData, readBackData, "Decrypted data should match original");
    }
}