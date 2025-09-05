package matheus.bcc.paintfx;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class InfoController {
    public static Button btConfirmar;
    public static Label tamanho;
    public static Label local;
    public static Label tipo;
    public static Label nome;

    public void onConfirmar(ActionEvent actionEvent) {
        btConfirmar.getScene().getWindow().hide();
    }
}
