import java.util.List;
import java.util.LinkedList;

public class Cliente {
    private String nome;
    private String cpf;
    private String email;

    private List<Assinatura> assinaturas;

    
    public Cliente(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.assinaturas = new LinkedList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Assinatura> getAssinaturas() {
        return assinaturas;
    }

    public void addAssinaturas(Assinatura assinatura) {
        this.assinaturas.add(assinatura);
    }
    public boolean isPagante(){
        for(Assinatura a : assinaturas){
            if(a.isAtiva()){
                return true;
            }
        }
        return false;
    }
    public double valorMensalAss(){
        return assinaturas.stream().filter(a -> a.isAtiva()).mapToDouble(a -> a.getAplicativo().getPreco()).sum();
    }
    

    

    public String toLineFile(){
        return cpf+","+nome+","+email;
    }

    public static Cliente fromLineFile(String line){
        String[] tokens = line.split(",");
        String cpf = tokens[0];
        String nome = tokens[1];
        String email = tokens[2];
        return new Cliente(nome, cpf,email);
    }

}
