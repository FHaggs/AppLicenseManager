import javax.swing.table.AbstractTableModel;
import static javax.swing.JOptionPane.showMessageDialog;


public class CatalogoAplicativosViewModel extends AbstractTableModel {
    private CatalogoAplicativos aplicativos;
    private final String[] nomesDasColunas = {
        "Codigo",
        "Nome",
        "Preco",
        "Sist. Operacional"
    };

    public CatalogoAplicativosViewModel(CatalogoAplicativos aplicativos){
        this.aplicativos = aplicativos;
    }
    public String getColumnName(int col) {
        return nomesDasColunas[col];
    }

    public int getRowCount() { 
        return aplicativos.getQtdade(); 
    }

    public int getColumnCount() { 
        return nomesDasColunas.length;
    }

    public Object getValueAt(int row, int col) {
        Aplicativo app = aplicativos.getProdutoNaLinha(row);
        switch (col) {
            case 0 : return (Object)(app.getCodigo());
            case 1 : return (Object)(app.getNome());
            case 2 : return (Object)(app.getPreco());
            case 3 : return (Object)(app.getSo());        
            default: return (Object)"none";
        }
    }
    
    public boolean isCellEditable(int row, int col)
        { 
            return true;

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
    }
}
