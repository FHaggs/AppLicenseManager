public class Assinatura {
    private Cliente cliente;
    private Aplicativo aplicativo;
    private int codigoAssinatura;
    private String inicioVigencia;
    private String fimVigencia;
    
    
    
    public Assinatura(Cliente cliente, Aplicativo aplicativo, int codigoAssinatura, String inicioVigencia, String fimVigencia) {
        this.cliente = cliente;
        this.aplicativo = aplicativo;
        this.codigoAssinatura = codigoAssinatura;
        this.inicioVigencia = inicioVigencia;
        this.fimVigencia = "00/00";
        avisarClienteApp();
    }
    
    private void avisarClienteApp(){
        this.cliente.addAssinaturas(this);
        this.aplicativo.addAssinatura(this);
    }

    public String getCpf() {
        return cliente.getCpf();
    }
    public int getCodigoAplicativo() {
        return aplicativo.getCodigo();
    }
    public int getCodigoAssinatura() {
        return codigoAssinatura;
    }
    public void setCodigoAssinatura(int codigo) {
        this.codigoAssinatura = codigo;
    }
    public String getInicioVigencia() {
        return inicioVigencia;
    }
    public void setInicioVigencia(String inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }
    public String getFimVigencia() {
        return fimVigencia;
    }
    public void setFimVigencia(String fimVigencia) {
        this.fimVigencia = fimVigencia;
    }
    public boolean isAtiva(){
        return fimVigencia.equals("00/00");
    }

    public String toLineFile(){
        return codigoAssinatura+","+cliente.getCpf()+","+aplicativo.getCodigo()+","+inicioVigencia+","+fimVigencia;
    }

    public static Assinatura fromLineFile(String line, CatalogoClientes catClientes, CatalogoAplicativos catApps){
        String[] tokens = line.split(",");
        int codigoAssinatura = Integer.parseInt(tokens[0]);
        String clienteCpf = tokens[1];
        int codigoAplicativo = Integer.parseInt(tokens[2]);
        String inicioVigencia = tokens[3];
        String fimVigencia = tokens[4];

        Cliente cliente = catClientes.getClienteByCpf(clienteCpf);
        
        Aplicativo app = catApps.getCodigoAplicativo(codigoAplicativo);

        return new Assinatura(cliente, app, codigoAssinatura, inicioVigencia, fimVigencia);

    }

    
}
