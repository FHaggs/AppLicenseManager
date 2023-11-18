import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class CatalogoAplicativos extends AbstractCatalogo<Aplicativo>{
    

    public CatalogoAplicativos(String dataFileName) {
        super(dataFileName);
    }

    public Aplicativo getCodigoAplicativo(int codigo){
        return getStream().filter(app -> app.getCodigo() == codigo).findFirst().get();
    }

    @Override
    public void loadFromFile() {
        Path appsFilePath = Path.of(getDataFileName());
        try (Stream<String> appsStream = Files.lines(appsFilePath)) {
            appsStream.forEach(str -> cadastra(Aplicativo.fromLineFile(str)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveToFile() {
        Path appsFilePath = Path.of(getDataFileName());
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(appsFilePath, StandardCharsets.UTF_8))) {
            for (Aplicativo app : getItens()) {
                writer.println(app.toLineFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
