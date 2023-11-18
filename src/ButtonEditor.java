import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellEditor;

class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private JButton button;
    private String value;

    public ButtonEditor() {
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(this);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.value = (value == null) ? "" : value.toString();
        button.setText(this.value);
        return button;
    }

    public Object getCellEditorValue() {
        return value;
    }

    public void actionPerformed(ActionEvent e) {
        // Unreachable
        
    }
}