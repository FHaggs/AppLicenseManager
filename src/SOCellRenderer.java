import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;

public class SOCellRenderer extends JComboBox<Aplicativo.SO> implements TableCellRenderer {
    public SOCellRenderer() {
        for (Aplicativo.SO so : Aplicativo.SO.values()) {
            addItem(so);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Use um JLabel para renderizar o valor
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setHorizontalAlignment(JLabel.CENTER);

        if (value instanceof Aplicativo.SO) {
            label.setText(value.toString());
        }

        if (isSelected) {
            label.setBackground(table.getSelectionBackground());
            label.setForeground(table.getSelectionForeground());
        } else {
            label.setBackground(table.getBackground());
            label.setForeground(table.getForeground());
        }

        return label;
    }
}
