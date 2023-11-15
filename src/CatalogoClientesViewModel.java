import javax.swing.table.AbstractTableModel;

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
            case 3 : return (Object)(cliente.getAssinaturas());        
            default: return (Object)"none";
        }
    }
    /* 
    public boolean isCellEditable(int row, int col)
        { 
            return false; // TODO: Mudar isso

        }

    public void setValueAt(Object value, int row, int col) {
        Aplicativo app = aplicativos.getProdutoNaLinha(row);
        if(col == 0){
            try{
                int novoCodigo = Integer.parseInt(value.toString());
                app.setCodigo(novoCodigo);
            }catch(NumberFormatException e) {
                erroDeFormato();
            }
        }

        if (col == 1) {
            // Coluna de Nome
            if(value.toString().isEmpty()){
                erroDeFormato(); // proibido colocar string vazia
            }else {
                app.setNome(value.toString());
            }
        } else if (col == 2) {
            // Coluna de Preço
            try {
                double novoPreco = Double.parseDouble(value.toString());
                app.setPreco(novoPreco);
            } catch (NumberFormatException e) {
                erroDeFormato();

            }
        }else if(col == 3){
            if (value instanceof Aplicativo.SO) {
                app.setSo((Aplicativo.SO) value);
            } else {
                // Unreachable
            }

        }

        
        fireTableCellUpdated(row, col);
    }    
    private void erroDeFormato(){
        showMessageDialog(null, "Formato inválido");
    } */
}
