import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DecoratorTest {

    // --- Внутренний фейковый класс для имитации записи в файл ---
    // Он просто хранит строку в памяти, вместо того чтобы писать на диск.
    static class MockDataSource implements DataSource {
        private String dataInMemory;

        @Override
        public void writeData(String data) {
            this.dataInMemory = data; // "Записываем" в переменную
        }

        @Override
        public String readData() {
            return dataInMemory; // "Читаем" из переменной
        }
    }

    @Test
    void testEncryptionDecorator() {
        // 1. Создаем фейковый источник (он пустой)
        MockDataSource mockSource = new MockDataSource();

        // 2. Оборачиваем его в Декоратор шифрования
        DataSource secureSource = new EncryptionDecorator(mockSource);

        // 3. Данные для теста
        String originalData = "My Secret Data";

        // --- ТЕСТ ЗАПИСИ ---
        // Пишем данные через декоратор
        secureSource.writeData(originalData);

        // ПРОВЕРКА 1: Данные в "файле" (mockSource) должны отличаться от оригинала,
        // так как они должны быть зашифрованы.
        String writtenData = mockSource.readData();
        assertNotEquals(originalData, writtenData, "Data in storage should be encrypted");
        assertNotNull(writtenData);

        // --- ТЕСТ ЧТЕНИЯ ---
        // Читаем данные через декоратор (он должен их расшифровать)
        String readBackData = secureSource.readData();

        // ПРОВЕРКА 2: Прочитанные данные должны совпадать с оригиналом
        assertEquals(originalData, readBackData, "Decrypted data should match original");
    }
}