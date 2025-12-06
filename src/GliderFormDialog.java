import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GliderFormDialog extends JDialog {

    // Поля ввода
    private JTextField idField;
    private JTextField modelField;
    private JTextField priceField;
    private JTextField dateField;
    private JTextField wingspanField;
    private JTextField weightField;
    private HangGlider result = null;
    private boolean isEditMode = false; // Флаг: мы редактируем или создаем?

    // Сюда мы сохраним созданный объект, если пользователь нажмет Save

    public GliderFormDialog(Frame parent) {
        super(parent, "Add New Glider", true); // true = модальное окно
        setSize(400, 300);
        setLocationRelativeTo(parent);

        initComponents();
    }

    public GliderFormDialog(Frame parent, HangGlider gliderToEdit) {
        super(parent, "Edit Glider", true);
        this.isEditMode = true;
        this.result = gliderToEdit; // Запоминаем объект, который правим
        initUI(parent);
        fillFields(gliderToEdit); // Заполняем поля данными
    }

   private void initComponents() {
        // Используем JPanel-обертку, чтобы сделать отступы от краев окна
        JPanel mainPanel = new JPanel();
        // Отступы: 15px со всех сторон
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        // GridLayout: 7 строк, 2 колонки, отступы между ячейками 10px
        mainPanel.setLayout(new GridLayout(7, 2, 10, 10));

        // Вспомогательный шрифт
        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);

        // -- Добавляем поля --
        addLabeledField(mainPanel, "ID:", idField = new JTextField(), labelFont);
        addLabeledField(mainPanel, "Model:", modelField = new JTextField(), labelFont);
        addLabeledField(mainPanel, "Price:", priceField = new JTextField(), labelFont);
        
        dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        addLabeledField(mainPanel, "Date (yyyy-MM-dd):", dateField, labelFont);
        
        addLabeledField(mainPanel, "Wingspan:", wingspanField = new JTextField(), labelFont);
        addLabeledField(mainPanel, "Max Weight (kg):", weightField = new JTextField(), labelFont);

        // -- Кнопки --
        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(46, 204, 113)); // Зеленый
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> onSave());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(231, 76, 60)); // Красный
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> dispose());

        mainPanel.add(saveButton);
        mainPanel.add(cancelButton);

        // Добавляем панель в окно
        add(mainPanel);
    }

    // Метод для быстрого добавления лейбла и поля
    private void addLabeledField(JPanel panel, String labelText, JTextField field, Font font) {
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        panel.add(label);
        panel.add(field);
    }

    private void onSave() {
        try {
            // 1. Считываем и парсим данные
            int id = Integer.parseInt(idField.getText());
            String model = modelField.getText();
            double price = Double.parseDouble(priceField.getText());
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateField.getText());
            double wingspan = Double.parseDouble(wingspanField.getText());
            int weight = Integer.parseInt(weightField.getText());

            // 2. Валидация (простая проверка)
            if (model.isEmpty()) {
                throw new Exception("Model cannot be empty");
            }

            // 3. Обновляем существующий объект или создаем новый
            if (isEditMode) {
                // Если режим редактирования, обновляем поля существующего объекта
                result.setModel(model);
                result.setPrice(price);
                result.setProductionDate(date);
                result.setWingspan(wingspan);
                result.setPilotWeightLimit(weight);
            } else {
                // Если режим добавления, создаем новый объект
                result = new HangGlider(id, model, date, price, wingspan, weight);
            }

            // 4. Закрываем окно
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please check number fields (ID, Price, etc).", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initUI(Frame parent) {
        setSize(400, 350);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setLayout(new GridLayout(7, 2, 10, 10));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);

        addLabeledField(mainPanel, "ID:", idField = new JTextField(), labelFont);
        addLabeledField(mainPanel, "Model:", modelField = new JTextField(), labelFont);
        addLabeledField(mainPanel, "Price:", priceField = new JTextField(), labelFont);
        
        dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        addLabeledField(mainPanel, "Date (yyyy-MM-dd):", dateField, labelFont);
        
        addLabeledField(mainPanel, "Wingspan:", wingspanField = new JTextField(), labelFont);
        addLabeledField(mainPanel, "Max Weight (kg):", weightField = new JTextField(), labelFont);

        // Если режим редактирования, запрещаем менять ID (это уникальный ключ)
        if (isEditMode) {
            idField.setEditable(false);
            idField.setBackground(new Color(230, 230, 230)); // Слегка серый
        }

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(46, 204, 113));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> onSave());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> {
            result = null; // Если отмена при добавлении - возвращаем null
            dispose();
        });

        mainPanel.add(saveButton);
        mainPanel.add(cancelButton);

        add(mainPanel);
    }

    private void fillFields(HangGlider g) {
        idField.setText(String.valueOf(g.getId()));
        modelField.setText(g.getModel());
        priceField.setText(String.valueOf(g.getPrice()));
        dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(g.getProductionDate()));
        wingspanField.setText(String.valueOf(g.getWingspan()));
        weightField.setText(String.valueOf(g.getPilotWeightLimit()));
    }

    // Метод, чтобы главное окно могло забрать результат
    public HangGlider getResult() {
        return result;
    }
}