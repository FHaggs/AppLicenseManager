import javax.swing.table.AbstractTableModel;

public class CatalogoClientesPagantesViewModel extends AbstractTableModel {
    private CatalogoClientes clientes;
    private final String[] nomesDasColunas = {
        "Nome",
        "Email",
        "Valor Mensal"
    };

    public CatalogoClientesPagantesViewModel(CatalogoClientes clientes){
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
            case 0 : return (Object)(cliente.getNome());
            case 1 : return (Object)(cliente.getEmail());
            case 2 : return (Object)(cliente.valorMensalAss());        
            default: return (Object)"none";
        }
    }
}
