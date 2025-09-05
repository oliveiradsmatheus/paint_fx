module matheus.bcc.paintfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
    requires ij;
    requires javafx.graphics;

    opens matheus.bcc.paintfx to javafx.fxml;
    exports matheus.bcc.paintfx;
}