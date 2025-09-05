package matheus.bcc.paintfx;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class GaussBlurController  implements Initializable {
    public Slider slNoise;
    public static int fator = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        slNoise.setValue(2);
    }

    public void onConfirmar(ActionEvent actionEvent) {
        fator = (int) slNoise.getValue();
        slNoise.getScene().getWindow().hide();
    }
}
