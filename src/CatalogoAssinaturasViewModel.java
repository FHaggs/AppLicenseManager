import javax.swing.table.AbstractTableModel;
import static javax.swing.JOptionPane.showMessageDialog;


public class CatalogoAssinaturasViewModel extends AbstractTableModel {
    private CatalogoAssinaturas assinaturas;
    private final String[] nomesDasColunas = {
        "Codigo",
        "Início Vigencia",
        "Fim Vigência",
        "Aplicativo",
        "Valor assinatura"
    };
    boolean chamadaDoCliente;

    public CatalogoAssinaturasViewModel(CatalogoAssinaturas assinaturas, boolean chamadaDoCliente){
        this.assinaturas = assinaturas;
        this.chamadaDoCliente = chamadaDoCliente;
        /* 
        if(chamadaDoCliente){
            String[] nomesDasColunasCliente = {
                "Codigo",
                "Início Vigencia",
                "Fim Vigência",
                "Aplicativo",
                "Valor assinatura"
            };
            nomesDasColunas = nomesDasColunasCliente;
        }else {
            String[] nomesDasColunasCliente = {
                "Codigo",
                "Cliente",
                "Início Vigencia",
                "Fim Vigência"
            };
            nomesDasColunas = nomesDasColunasCliente;
        } */
    }
    public String getColumnName(int col) {
        return nomesDasColunas[col];
    }

    public int getRowCount() { 
        return assinaturas.getQtdade(); 
    }

    public int getColumnCount() { 
        return nomesDasColunas.length;
    }

    public Object getValueAt(int row, int col) {
        Assinatura assinatura = assinaturas.getProdutoNaLinha(row);
        if(chamadaDoCliente){
            switch (col) {
                case 0 : return (Object)(assinatura.getCodigoAssinatura());
                case 1 : return (Object)(assinatura.getInicioVigencia());
                case 2 : return (Object)(assinatura.getFimVigencia());
                case 3 : return (Object)(assinatura.getAplicativo().getNome());
                case 4: return (Object)(assinatura.getAplicativo().getPreco());        
                default: return (Object)"none";
            }
        }else {
            switch (col) {
                case 0 : return (Object)(assinatura.getCodigoAssinatura());
                case 1 : return (Object)(assinatura.getCliente().getNome());
                case 2 : return (Object)(assinatura.getInicioVigencia());
                case 3 : return (Object)(assinatura.getFimVigencia());
                default: return (Object)"none";
            }
        }
    }
    
    public boolean isCellEditable(int row, int col)
        {
            return((chamadaDoCliente && col == 2) || (!chamadaDoCliente && col == 3)); // Unico campo editavel é o fim vigencia
        }

    public void setValueAt(Object value, int row, int col) {
        Assinatura assinatura = assinaturas.getProdutoNaLinha(row);
        String novoFim = value.toString();

        if(novoFim.isEmpty()){
            erroDeFormato();
        }else{
            assinatura.setFimVigencia(novoFim);
        }
        
        fireTableCellUpdated(row, col);
    }    
    private void erroDeFormato(){
        showMessageDialog(null, "Formato inválido");
    }
}
