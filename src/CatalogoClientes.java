import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class CatalogoClientes extends AbstractCatalogo<Cliente>{
    

    public CatalogoClientes(String dataFileName) {
        super(dataFileName);
    
    }



    @Override
    public void loadFromFile() {
        Path appsFilePath = Path.of(getDataFileName());
        try (Stream<String> appsStream = Files.lines(appsFilePath)) {
            appsStream.forEach(str -> cadastra(Cliente.fromLineFile(str)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveToFile() {
        Path appsFilePath = Path.of(getDataFileName());
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(appsFilePath, StandardCharsets.UTF_8))) {
            for (Cliente client : getItens()) {
                writer.println(client.toLineFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
