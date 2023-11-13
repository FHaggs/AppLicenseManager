import java.util.List;

public class Cliente {
    private String nome;
    private String cpf;
    private String email;

    private List<Assinatura> assinaturas;

    
    public Cliente(String nome, String cpf, String email, List<Assinatura> assinaturas) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.assinaturas = assinaturas;
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

    public void setAssinaturas(List<Assinatura> assinaturas) {
        this.assinaturas = assinaturas;
    }

    public void cancelaAssinatura(Assinatura assinatura){
       //TODO: Fazer esse metodo
    }

}
