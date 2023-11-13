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

    
}
