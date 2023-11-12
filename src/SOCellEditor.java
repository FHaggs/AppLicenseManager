import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

// Editar a celula do SO com dropdown
public class SOCellEditor extends DefaultCellEditor {
    private JComboBox<Aplicativo.SO> comboBox;

    public SOCellEditor() {
        super(new JComboBox<Aplicativo.SO>());
        comboBox = (JComboBox<Aplicativo.SO>) getComponent();
        for (Aplicativo.SO so : Aplicativo.SO.values()) {
            comboBox.addItem(so);
        }
    }

    @Override
    public Object getCellEditorValue() {
        return comboBox.getSelectedItem();
    }
}


