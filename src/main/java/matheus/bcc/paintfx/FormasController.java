package matheus.bcc.paintfx;

import javafx.scene.input.MouseEvent;

public class FormasController {
    public static int forma = -1;

    // 1 - Circulo
    // 2 - Retangulo
    // 3 - Texto
    // 4 - Livre

    public void onCirculo(MouseEvent mouseEvent) {
        forma = 1;
    }

    public void onRetangulo(MouseEvent mouseEvent) {
        forma = 2;
    }

    public void onTexto(MouseEvent mouseEvent) {
        forma = 3;
    }

    public void onLivre(MouseEvent mouseEvent) {
        forma = 4;
    }
}
