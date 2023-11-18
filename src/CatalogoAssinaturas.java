import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class CatalogoAssinaturas extends AbstractCatalogo<Assinatura> {
    private CatalogoAplicativos apps;
    private CatalogoClientes clientes;
    
    public CatalogoAssinaturas(List<Assinatura> assinaturas){
        super(null);
        setItens(assinaturas);
        this.apps = null;
        this.clientes = null;
    }

    public CatalogoAssinaturas(String dataFileName, CatalogoAplicativos apps, CatalogoClientes clientes) {
        super(dataFileName);
        this.apps = apps;
        this.clientes = clientes;
    }

    @Override
    void loadFromFile() {
        Path appsFilePath = Path.of(getDataFileName());
        try (Stream<String> assinaturasStram = Files.lines(appsFilePath)) {
            assinaturasStram.forEach(str -> cadastra(Assinatura.fromLineFile(str, clientes, apps)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void saveToFile() {
        Path appsFilePath = Path.of(getDataFileName());
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(appsFilePath, StandardCharsets.UTF_8))) {
            for (Assinatura assinatura : getItens()) {
                writer.println(assinatura.toLineFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
