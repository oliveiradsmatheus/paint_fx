package matheus.bcc.paintfx.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SobreController {
    public void onFechar(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
