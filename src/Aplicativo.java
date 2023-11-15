import java.util.List;
import java.util.LinkedList;

public class Aplicativo {
    public enum SO { Android, IOS }; 
    private int codigo;
    private String nome;
    private double preco;
    private SO so;

    private List<Assinatura> assinaturas;
    

    public Aplicativo(int codigo, String nome, double preco, Aplicativo.SO so) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.so = so;
        this.assinaturas = new LinkedList<>();
    }

    public int getCodigo() {
        return codigo;
    }

    public void addAssinatura(Assinatura assinatura){
        assinaturas.add(assinatura);
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setSo(SO so) {
        this.so = so;
    }

    public SO getSo() {
        return so;
    }

    public String toLineFile(){
        return codigo+","+nome+","+preco+","+so;
    }

    public static Aplicativo fromLineFile(String line){
        String[] tokens = line.split(",");
        int codigo = Integer.parseInt(tokens[0]);
        String nome = tokens[1];
        double preco = Double.parseDouble(tokens[2]);
        Aplicativo.SO so = Aplicativo.SO.valueOf(Aplicativo.SO.class, tokens[3]);
        return new Aplicativo(codigo,nome,preco,so);
    }
}
