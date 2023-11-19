import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;

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

}
