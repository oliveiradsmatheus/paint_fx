package matheus.bcc.paintfx;

import  javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("principal-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Paint FX");
        stage.setScene(scene);

        try {
            InputStream caminhoIcone = getClass().getResourceAsStream("/icones/icone.png");
            if (caminhoIcone != null) {
                Image icon = new Image(caminhoIcone);
                stage.getIcons().add(icon);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar o ícone: " + e.getMessage());
        }

        stage.setMaximized(true);
        stage.show();
    }
}
