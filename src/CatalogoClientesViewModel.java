import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class CatalogoClientesViewModel extends AbstractTableModel {
    private CatalogoClientes clientes;
    private final String[] nomesDasColunas = {
        "CPF",
        "Nome",
        "Email",
        "Ver assinaturas"
    };

    public CatalogoClientesViewModel(CatalogoClientes clientes){
        this.clientes = clientes;
    }
    public String getColumnName(int col) {
        return nomesDasColunas[col];
    }

    public int getRowCount() { 
        return clientes.getQtdade(); 
    }

    public int getColumnCount() { 
        return nomesDasColunas.length;
    }

    public Object getValueAt(int row, int col) {
        Cliente cliente = clientes.getProdutoNaLinha(row);
        switch (col) {
            case 0 : return (Object)(cliente.getCpf());
            case 1 : return (Object)(cliente.getNome());
            case 2 : return (Object)(cliente.getEmail());
            case 3 : return (Object)("Ver assinaturas");        
            default: return (Object)"none";
        }
    }
    
    public boolean isCellEditable(int row, int col)
        { 
            return col == 0 || col == 1 || col == 2; 

        }

    public void setValueAt(Object value, int row, int col) {
        Cliente cliente = clientes.getProdutoNaLinha(row);
        if(col == 0){
            String novoCpf = value.toString();
            if(novoCpf.isEmpty()){
                erroDeFormato();
            }else{
                cliente.setCpf(novoCpf);
            }

        }

        if (col == 1) {
            
            String novoNome = value.toString();
            if(novoNome.isEmpty()){
                erroDeFormato();
            }else{
                cliente.setNome(novoNome);
            }
        } else if (col == 2) {
            String novoEmail = value.toString();
            if(novoEmail.isEmpty()){
                erroDeFormato();
            }else{
                cliente.setEmail(novoEmail);
            }
        }

        
        fireTableCellUpdated(row, col);
    }    
    private void erroDeFormato(){
        showMessageDialog(null, "Formato inválido");
    } 
    // Adiciona um botão personalizado na coluna "Ver assinaturas"
    public TableCellRenderer getTableCellRenderer(int row, int column) {
        if (column == 3) {
            return new ButtonRenderer();
        }
        return getTableCellRenderer(row, column);
    }

    // Define um editor para a célula da coluna "Ver assinaturas"
    public TableCellEditor getTableCellEditor(int row, int column) {
        if (column == 3) {
            return new ButtonEditor();
        }
        return getTableCellEditor(row, column);
    }
        // Implementa um renderer personalizado para o botão
        class ButtonRenderer extends JButton implements TableCellRenderer {
            public ButtonRenderer() {
                setOpaque(true);
            }
    
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                setText((value == null) ? "" : value.toString());
                return this;
            }
        }
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
                JOptionPane.showMessageDialog(null, "Button clicked in row " + ((JTable) button.getModel()).getRowCount());
                fireEditingStopped(); // Make sure to tell the editor that editing is finished.
            }
        }
}
