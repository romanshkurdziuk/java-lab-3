import javax.swing.*;
import java.awt.*;
import java.util.ArrayList; // Не забудь добавить

public class MainWindow extends JFrame {

    private final ApparatusStorage<HangGlider> storage;
    private final FileManager fileManager;
    
    // Новые поля для таблицы
    private JTable table;
    private GliderTableModel tableModel;

    public MainWindow(ApparatusStorage<HangGlider> storage, FileManager fileManager) {
        this.storage = storage;
        this.fileManager = fileManager;

        setTitle("HangGlider Factory Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- УСТАНОВКА ФОНА ---
        // 1. Создаем нашу панель с картинкой
        BackgroundPanel bgPanel = new BackgroundPanel("/background.jpg");
        // 2. Устанавливаем layout (менеджер расположения), так как мы заменили стандартную панель
        bgPanel.setLayout(new BorderLayout());
        // 3. Говорим окну использовать эту панель как главную
        setContentPane(bgPanel);
        // ----------------------

        // Иконка (твой старый код)
        java.net.URL iconURL = getClass().getResource("/icon.png");
        if (iconURL != null) {
            setIconImage(new ImageIcon(iconURL).getImage());
        }

        initComponents();
        createMenuBar();
        refreshTable();
        setVisible(true);
    }

    private void initComponents() {
        // --- 1. НАСТРОЙКА ТАБЛИЦЫ ---
        // Создаем модель и саму таблицу
        tableModel = new GliderTableModel(new ArrayList<>());
        table = new JTable(tableModel);
        // 1. Создаем наш собственный сортировщик, чтобы управлять им
        javax.swing.table.TableRowSorter<GliderTableModel> sorter = new javax.swing.table.TableRowSorter<>(tableModel);
        // 2. ВАЖНО: Привязываем наш сортировщик к таблице
        table.setRowSorter(sorter);

        // Стилизация таблицы
        table.setRowHeight(30); // Высота строки (чтобы текст не слипался)
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Шрифт данных
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14)); // Шрифт заголовка
        table.setSelectionBackground(new Color(189, 195, 199)); // Цвет выделения (светло-серый)
        
        // Оборачиваем таблицу в панель прокрутки
        JScrollPane scrollPane = new JScrollPane(table);
        
        // ВАЖНО: Делаем отступы (20px) вокруг таблицы, чтобы видеть фон
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // ВАЖНО: Делаем подложку таблицы прозрачной, чтобы фон был виден в отступах
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); 
        // Сама таблица (белая часть) останется непрозрачной

        // Добавляем таблицу в центр окна
        add(scrollPane, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        searchPanel.setOpaque(false); // Прозрачная, чтобы видеть фон

        JLabel searchLabel = new JLabel("Search Model:");
        // Улучшение стиля: белый цвет текста для лучшей читаемости на фоне
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchLabel.setForeground(Color.WHITE);
        
        JTextField searchField = new JTextField(20); // Поле на 20 символов
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        // Улучшение стиля: небольшие отступы внутри поля ввода
        searchField.setMargin(new Insets(2, 5, 2, 5));

        // Добавляем слушатель нажатий клавиш
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null); // Сброс фильтра
                } else {
                    // (?i) означает регистронезависимый поиск
                    // 1 - это индекс колонки "Model"
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
                    } catch (Exception ex) {
                        // Игнорируем ошибки регулярных выражений
                    }
                }
            }
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        
        add(searchPanel, BorderLayout.NORTH);

        // --- 2. ПАНЕЛЬ КНОПОК ---
        JPanel buttonPanel = new JPanel();
        // Располагаем кнопки по центру с отступами
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        
        // ВАЖНО: Делаем панель кнопок прозрачной, чтобы видеть фон под ними
        buttonPanel.setOpaque(false);

        // --- Кнопка ADD (Зеленая) ---
        JButton addButton = new JButton("Add Glider");
        styleButton(addButton, new Color(46, 204, 113)); // Emerald Green
        addButton.addActionListener(e -> {
            // Открываем диалог добавления
            GliderFormDialog dialog = new GliderFormDialog(this);
            dialog.setVisible(true);
            
            // Получаем результат после закрытия окна
            HangGlider newGlider = dialog.getResult();
            if (newGlider != null) {
                // Проверяем на дубликат ID
                if (storage.getByID(newGlider.getId()) != null) {
                    JOptionPane.showMessageDialog(this, 
                        "Error: ID " + newGlider.getId() + " already exists!", 
                        "Duplicate ID", 
                        JOptionPane.ERROR_MESSAGE);
                } else {
                    // Добавляем в хранилище и обновляем таблицу
                    storage.addApparatus(newGlider);
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Glider added successfully!");
                }
            }
        });

        // --- Кнопка REMOVE (Красная) ---
        JButton removeButton = new JButton("Remove Selected");
        styleButton(removeButton, new Color(231, 76, 60)); // Alizarin Red
        removeButton.addActionListener(e -> removeSelectedGlider());

        JButton editButton = new JButton("Edit Selected");
        styleButton(editButton, new Color(243, 156, 18)); // Orange color
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                // 1. Получаем ID из таблицы
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                // 2. Находим реальный объект в хранилище
                HangGlider gliderToEdit = storage.getByID(id);
                
                if (gliderToEdit != null) {
                    // 3. Открываем диалог в режиме РЕДАКТИРОВАНИЯ (передаем объект)
                    GliderFormDialog dialog = new GliderFormDialog(this, gliderToEdit);
                    dialog.setVisible(true);
                    
                    // 4. После закрытия просто обновляем таблицу (объект обновился внутри диалога по ссылке)
                    refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to edit.");
            }
        });


        // --- Кнопка EXIT (Серая) ---
        JButton exitButton = new JButton("Exit");
        styleButton(exitButton, new Color(149, 165, 166)); // Concrete Gray
        exitButton.addActionListener(e -> System.exit(0));

        // Добавляем кнопки на панель
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(exitButton);

        // Добавляем панель вниз окна
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE); // Белый текст
        button.setFocusPainted(false); // Убираем рамку фокуса
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorderPainted(false); // Плоская кнопка без выпуклостей
        button.setOpaque(true); // Нужно для корректного цвета фона
    }

    // Метод для обновления таблицы свежими данными из хранилища
    private void refreshTable() {
        tableModel.updateData(storage.getAll());
    }

    // Логика удаления выделенной строки
    private void removeSelectedGlider() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            storage.removeByID(id);
            
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row first.");
        }
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // --- Меню FILE ---
        JMenu fileMenu = new JMenu("File");

        // 1. JSON
        JMenuItem saveJsonItem = new JMenuItem("Save to JSON");
        saveJsonItem.addActionListener(e -> {
            new Thread(() -> {
                fileManager.writeDataToJson("data.json", storage.getAll());
                SwingUtilities.invokeLater(() -> 
                    JOptionPane.showMessageDialog(this, "Data saved to JSON successfully!")
                );
            }).start();
        });

        JMenuItem loadJsonItem = new JMenuItem("Load from JSON");
        loadJsonItem.addActionListener(e -> {
            new Thread(() -> {
                java.util.List<HangGlider> loaded = fileManager.readDataFromJson("data.json");
                SwingUtilities.invokeLater(() -> {
                    if (loaded != null && !loaded.isEmpty()) {
                        for (HangGlider g : loaded) storage.addApparatus(g);
                        refreshTable();
                        JOptionPane.showMessageDialog(this, "Data loaded from JSON!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to load JSON or file is empty.");
                    }
                });
            }).start();
        });

        // 2. XML (НОВЫЕ ПУНКТЫ)
        JMenuItem saveXmlItem = new JMenuItem("Save to XML");
        saveXmlItem.addActionListener(e -> {
            new Thread(() -> {
                // Вызываем метод сохранения в XML
                fileManager.writeDataToXml("data.xml", storage.getAll());
                
                SwingUtilities.invokeLater(() -> 
                    JOptionPane.showMessageDialog(this, "Data saved to XML successfully!")
                );
            }).start();
        });

        JMenuItem loadXmlItem = new JMenuItem("Load from XML");
        loadXmlItem.addActionListener(e -> {
            new Thread(() -> {
                // Вызываем метод загрузки из XML
                java.util.List<HangGlider> loaded = fileManager.readDataFromXml("data.xml");
                
                SwingUtilities.invokeLater(() -> {
                    if (loaded != null && !loaded.isEmpty()) {
                        for (HangGlider g : loaded) storage.addApparatus(g);
                        refreshTable(); // Обновляем таблицу
                        JOptionPane.showMessageDialog(this, "Data loaded from XML!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to load XML or file is empty.");
                    }
                });
            }).start();
        });

        // 3. BACKUP
        JMenuItem backupItem = new JMenuItem("Backup to ZIP");
        backupItem.addActionListener(e -> {
            new Thread(() -> {
                // Сохраняем актуальные версии перед архивацией
                fileManager.writeDataToJson("data.json", storage.getAll());
                fileManager.writeDataToXml("data.xml", storage.getAll());
                
                fileManager.backupToZip("backup.zip", "data.json", "data.xml");
                
                SwingUtilities.invokeLater(() -> 
                    JOptionPane.showMessageDialog(this, "Backup created: backup.zip")
                );
            }).start();
        });

        // Добавляем все пункты в меню File
        fileMenu.add(saveJsonItem);
        fileMenu.add(loadJsonItem);
        fileMenu.addSeparator(); // Разделитель
        fileMenu.add(saveXmlItem); // <-- XML Save
        fileMenu.add(loadXmlItem); // <-- XML Load
        fileMenu.addSeparator(); // Разделитель
        fileMenu.add(backupItem);
        
        menuBar.add(fileMenu);

        // --- Меню PATTERNS (Lab 4) ---
        // (Оставь этот кусок кода без изменений, если он у тебя уже есть)
        JMenu patternsMenu = new JMenu("Patterns (Lab 4)");
        
        JMenuItem directorItem = new JMenuItem("Director: Generate Standard Models");
        directorItem.addActionListener(e -> {
            Director director = new Director();
            HangGliderBuilder builder = new HangGliderBuilder();

            builder.setId(generateUniqueId());
            director.constructBeginnerGlider(builder);
            storage.addApparatus(builder.build());

            builder = new HangGliderBuilder();
            builder.setId(generateUniqueId());
            director.constructProGlider(builder);
            storage.addApparatus(builder.build());

            refreshTable();
            JOptionPane.showMessageDialog(this, "Director generated 2 models!");
        });
        patternsMenu.add(directorItem);

        patternsMenu.addSeparator();

        JMenuItem saveEncryptedItem = new JMenuItem("Decorator: Save Encrypted");
        saveEncryptedItem.addActionListener(e -> {
            com.google.gson.Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
            String dataStr = gson.toJson(storage.getAll());
            DataSource source = new EncryptionDecorator(new FileDataSource("secure_data.txt"));
            source.writeData(dataStr);
            JOptionPane.showMessageDialog(this, "Encrypted data saved to 'secure_data.txt'!");
        });
        patternsMenu.add(saveEncryptedItem);
        
        // ... (можешь добавить Load Encrypted, если нужно) ...

        menuBar.add(patternsMenu);

        setJMenuBar(menuBar);
    }

    private int generateUniqueId() 
    {
        int id = 1000;
        while (storage.getByID(id) != null) 
        {
            id++;
        }
        return id;
    }
}