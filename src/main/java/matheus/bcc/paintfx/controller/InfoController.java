package matheus.bcc.paintfx.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.File;

public class InfoController {
    public Button btConfirmar;
    public Label tamanho;
    public Label local;
    public Label tipo;
    public Label nome;

    public void onConfirmar(ActionEvent actionEvent) {
        btConfirmar.getScene().getWindow().hide();
    }

    public void setInformacoes(File arquivo) {
        if (arquivo != null) {
            String nomeStr = arquivo.getName();
            String localStr = arquivo.getAbsolutePath();
            long tamanhoBytes = arquivo.length();
            String tipoStr = nomeStr.substring(nomeStr.lastIndexOf(".") + 1);

            String tamanhoFormatado;
            if (tamanhoBytes < 1024)
                tamanhoFormatado = tamanhoBytes + " Bytes";
            else if (tamanhoBytes < 1024 * 1024)
                tamanhoFormatado = (tamanhoBytes / 1024) + " KB";
            else
                tamanhoFormatado = String.format("%.2f MB", (double) tamanhoBytes / (1024 * 1024));

            nome.setText(nomeStr);
            tamanho.setText(tamanhoFormatado);
            local.setText(localStr);
            tipo.setText(tipoStr.toUpperCase());
        }
    }
}
