import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class GliderTableModel extends AbstractTableModel {

    // Названия колонок
    private final String[] columnNames = {"ID", "Model", "Price", "Date", "Wingspan", "Max Weight"};
    // Список данных для отображения
    private List<HangGlider> gliders;

    public GliderTableModel(List<HangGlider> gliders) {
        this.gliders = gliders;
    }

    // Метод для обновления данных в таблице (вызовем его, когда добавим/удалим что-то)
    public void updateData(List<HangGlider> newGliders) {
        this.gliders = newGliders;
        // Эта команда говорит таблице: "Данные изменились, перерисуйся!"
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return gliders.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // Самый главный метод: он говорит таблице, что писать в конкретной ячейке
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        HangGlider glider = gliders.get(rowIndex);

        switch (columnIndex) {
            case 0: return glider.getId();
            case 1: return glider.getModel();
            case 2: return glider.getPrice();
            case 3: 
                // Форматируем дату красиво
                return new SimpleDateFormat("yyyy-MM-dd").format(glider.getProductionDate());
            case 4: return glider.getWingspan();
            case 5: return glider.getPilotWeightLimit();
            default: return null;
        }
    }

    // ... внутри GliderTableModel ...

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return Integer.class; // ID
            case 1: return String.class;  // Model
            case 2: return Double.class;  // Price
            case 3: return String.class;  // Date (пока оставим как строку для простоты)
            case 4: return Double.class;  // Wingspan
            case 5: return Integer.class; // Weight
            default: return Object.class;
        }
    }
}