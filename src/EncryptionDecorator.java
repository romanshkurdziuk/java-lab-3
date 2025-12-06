public class EncryptionDecorator extends DataSourceDecorator 
{
    public EncryptionDecorator(DataSource source) 
    {
        super(source);
    }

    @Override
    public void writeData(String data) 
    {
        String encryptedData = encode(data);
        System.out.println("   [EncryptionDecorator] Data encrypted.");
        super.writeData(encryptedData);
    }

    @Override
    public String readData() 
    {
        String encryptedData = super.readData();
        String decryptedData = decode(encryptedData);
        System.out.println("   [EncryptionDecorator] Data decrypted.");
        
        return decryptedData;
    }

    private String encode(String data) 
    {
        try 
        {
            return CryptoProcessor.encrypt(data);
        } catch (Exception e) 
        {
            System.err.println("Encryption failed: " + e.getMessage());
            return data;
        }
    }

    private String decode(String data) 
    {
        try 
        {
            return CryptoProcessor.decrypt(data);
        } catch (Exception e) 
        {
            System.err.println("Decryption failed: " + e.getMessage());
            return data;
        }
    }
}