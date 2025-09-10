package matheus.bcc.paintfx.controller;

import javafx.scene.input.MouseEvent;

public class FormasController {
    public static int forma = -1;

    public void onLivre(MouseEvent mouseEvent) {
        forma = 1;
    }

    public void onCirculo(MouseEvent mouseEvent) {
        forma = 2;
    }

    public void onRetangulo(MouseEvent mouseEvent) {
        forma = 3;
    }

    public void onTexto(MouseEvent mouseEvent) {
        forma = 4;
    }
}
