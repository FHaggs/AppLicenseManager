import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractCatalogo<T> {
    private List<T> itens;
    private String dataFileName;

    public AbstractCatalogo(String dataFileName) {
        itens = new ArrayList<>();
        this.dataFileName = dataFileName;
    }

    public void cadastra(T p) {
        itens.add(p);
    }
    public List<T> getItens(){
        return itens;
    }
    public void setItens(List<T> itens){
        this.itens = itens;
    }

    public T getProdutoNaLinha(int linha) {
        if (linha >= itens.size()) {
            return null;
        }
        return itens.get(linha);
    }

    public String getDataFileName(){
        return dataFileName;
    }

    public int getQtdade() {
        return itens.size();
    }

    public Stream<T> getStream() {
        return itens.stream();
    }

    abstract void loadFromFile();


    abstract public void saveToFile();
}
