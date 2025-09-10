package matheus.bcc.paintfx.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import matheus.bcc.paintfx.Main;
import matheus.bcc.paintfx.util.ConversorImageJ;
import matheus.bcc.paintfx.util.ConversorImagem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Optional;

public class PrincipalController {
    public BorderPane painel;

    public ImageView imageView;
    public Label lbFileImage;
    public Slider slZoom;

    public MenuItem menuRecarregar;
    public MenuItem menuSalvar;
    public MenuItem menuSalvarComo;
    public MenuItem menuFechar;
    public MenuItem menuCinza;
    public MenuItem menuPB;
    public MenuItem menuInv;
    public MenuItem menuHor;
    public MenuItem menuVer;
    public MenuItem menuInfo;
    public MenuItem menuBorda;
    public MenuItem menuGauss;
    public MenuItem menuDilatar;
    public MenuItem menuCalorificar;
    public MenuItem miInfo;

    public Button btRecarregar;
    public Button btSalvar;
    public Button btSalvarComo;
    public Button btPincel;
    public Button btTonsCinza;
    public Button btHor;
    public Button btVer;
    public Button btInfo;

    public boolean alterada = false, modoDesenho = false;
    public Image original;
    public double largura, xi, yi, xf, yf;
    public String caminho;
    public MenuItem menuNoise;
    File arquivo;

    public Button btCor;

    public Button btPreto;
    public Button btVermelho;
    public Button btLaranja;
    public Button btAmarelo;
    public Button btBranco;
    public Button btVerde;
    public Button btRosa;
    public Button btTurquesa;
    public Button btRoxo;
    public Button btCinza;

    Color cor = Color.BLACK;

    public void onAbrir(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Abrir");
        fc.setInitialDirectory(new File("../"));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "Imagens (*.png, *.jpg, *.bmp)",
                        "*.png",
                        "*.jpg",
                        "*.jpeg",
                        "*.bmp"
                )
        );
        arquivo = fc.showOpenDialog(null);
        if (arquivo != null) {
            desabilitarBotoes(false);
            caminho = arquivo.getAbsolutePath();
            Image imagem = new Image(arquivo.toURI().toString());
            original = imagem;
            // Set fit limita o zoom da imagem
            //imageView.setFitWidth(imagem.getWidth());
            //imageView.setFitHeight(imagem.getHeight());
            imageView.setImage(imagem);

            largura = imagem.getWidth();

            lbFileImage.setText(arquivo.getAbsolutePath() + " (" + imagem.getWidth() + "x" + imagem.getHeight() + ")");
            slZoom.setValue(100);
            // slZoom.setOnMouseMoved(e -> imageView.setFitWidth(largura * slZoom.getValue() / 100));
            // Abaixo funciona ao arrastar
            slZoom.valueProperty().addListener((obs, antigo, novo) -> imageView.setFitWidth(largura * novo.doubleValue() / 100.));
        }
    }

    public void onFechar(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sair");
        if (alterada) {
            alert.setHeaderText("A imagem foi alterada");
            alert.setContentText("Deseja salvar as alterações antes de fechar?");

            ButtonType botaoSalvar = new ButtonType("Salvar");
            ButtonType botaoSalvarComo = new ButtonType("Salvar como...");
            ButtonType botaoNaoSalvar = new ButtonType("Não Salvar");
            ButtonType botaoCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(botaoSalvar, botaoSalvarComo, botaoNaoSalvar, botaoCancelar);

            Optional<ButtonType> resultado = alert.showAndWait();
            if (resultado.get() == botaoSalvar) {
                boolean salvo = onSalvar(actionEvent);
                if (salvo) {
                    imageView.setImage(null);
                    alterada = false;
                    if (modoDesenho) {
                        modoDesenho = false;
                        FormasController.forma = -1;
                        btPincel.setStyle("-fx-background-image: url('i-pincel.png'); -fx-background-color: transparent; -fx-background-position: center center; -fx-background-repeat: no-repeat; -fx-background-size: contain;");
                        painel.setRight(null);
                    }
                    desabilitarBotoes(true);
                } else
                    System.err.println("Falha ao salvar a imagem. Fechamento cancelado.");
            } else if (resultado.get() == botaoSalvarComo) {
                boolean salvo = onSalvarComo(actionEvent);
                if (salvo) {
                    imageView.setImage(null);
                    alterada = false;
                    if (modoDesenho) {
                        modoDesenho = false;
                        FormasController.forma = -1;
                        btPincel.setStyle("-fx-background-image: url('i-pincel.png'); -fx-background-color: transparent; -fx-background-position: center center; -fx-background-repeat: no-repeat; -fx-background-size: contain;");
                        painel.setRight(null);
                    }
                    desabilitarBotoes(true);
                } else
                    System.err.println("Falha ao salvar a imagem. Fechamento cancelado.");
            }else if (resultado.get() == botaoNaoSalvar) {
                imageView.setImage(null);
                alterada = false;
                if (modoDesenho) {
                    modoDesenho = false;
                    FormasController.forma = -1;
                    btPincel.setStyle("-fx-background-image: url('i-pincel.png'); -fx-background-color: transparent; -fx-background-position: center center; -fx-background-repeat: no-repeat; -fx-background-size: contain;");
                    painel.setRight(null);
                }
                desabilitarBotoes(true);
            }
        } else {
            alert.setContentText("Deseja realmente sair?");
            ButtonType botaoSim = new ButtonType("Sim");
            ButtonType botaoCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(botaoSim, botaoCancelar);
            if (alert.showAndWait().get() == botaoSim) {
                imageView.setImage(null);
                if (modoDesenho) {
                    modoDesenho = false;
                    FormasController.forma = -1;
                    btPincel.setStyle("-fx-background-image: url('i-pincel.png'); -fx-background-color: transparent; -fx-background-position: center center; -fx-background-repeat: no-repeat; -fx-background-size: contain;");
                    painel.setRight(null);
                }
                desabilitarBotoes(true);
            }
        }
        lbFileImage.setText("");
    }

    public void onPressionarMouse(MouseEvent mouseEvent) {
        if (modoDesenho && imageView.getImage() != null) {
            Image image = imageView.getImage();

            double viewLargura = imageView.getBoundsInLocal().getWidth();
            double viewAltura = imageView.getBoundsInLocal().getHeight();
            double escalaX = image.getWidth() / viewLargura;
            double escalaY = image.getHeight() / viewAltura;

            xi = mouseEvent.getX() * escalaX;
            yi = mouseEvent.getY() * escalaY;

            boolean cliqueDentroDaImagem = xi >= 0 && xi < image.getWidth() && yi >= 0 && yi < image.getHeight();
            if (cliqueDentroDaImagem && FormasController.forma == 4) {
                TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("Adicionar Texto");
                dialog.setContentText("Texto:");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent() && !result.get().isEmpty()){
                    Font fonte = new Font("Arial", Font.PLAIN, 24);
                    imageView.setImage(ConversorImagem.adicionarTexto(image, result.get(), fonte, cor, (int) xi, (int) yi));
                    alterada = true;
                }
            }
        }
    }

    public void onArrastarMouse(MouseEvent mouseEvent) {
        if (modoDesenho && imageView.getImage() != null) { // Apenas para o modo Mão Livre
            Image image = imageView.getImage();

            double x, y;
            double viewLargura = imageView.getBoundsInLocal().getWidth();
            double viewAltura = imageView.getBoundsInLocal().getHeight();
            double escalaX = image.getWidth() / viewLargura;
            double escalaY = image.getHeight() / viewAltura;

            x = mouseEvent.getX() * escalaX;
            y = mouseEvent.getY() * escalaY;
            if (FormasController.forma == 1) {
                imageView.setImage(ConversorImagem.desenharNaImagem(image, x, y, 50, cor));
                alterada = true;
            }
        }
    }

    public void onSoltarMouse(MouseEvent mouseEvent) {
        if (modoDesenho && imageView.getImage() != null) { // Apenas para o modo Retângulo
            Image image = imageView.getImage();

            double viewLargura = imageView.getBoundsInLocal().getWidth();
            double viewAltura = imageView.getBoundsInLocal().getHeight();
            double escalaX = image.getWidth() / viewLargura;
            double escalaY = image.getHeight() / viewAltura;

            xf = mouseEvent.getX() * escalaX;
            yf = mouseEvent.getY() * escalaY;

            if (FormasController.forma == 2)
                image = ConversorImagem.desenharCirculo(image, xi, yi, xf, yf, 5, cor);
            else if (FormasController.forma == 3)
                image = ConversorImagem.desenharRetangulo(image, xi, yi, xf, yf, 5, cor);
            imageView.setImage(image);
            alterada = true;
        }
    }

    public void onAtivarDesenho(ActionEvent actionEvent) throws Exception {
        modoDesenho = !modoDesenho;
        if (modoDesenho) {
            FormasController.forma = 1;
            btPincel.setStyle("-fx-background-image: url('i-pincel-ativo.png'); -fx-background-color: transparent; -fx-background-position: center center; -fx-background-repeat: no-repeat; -fx-background-size: contain;");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("formas-view.fxml"));
            VBox vBox = fxmlLoader.load();
            painel.setRight(vBox);
        } else {
            FormasController.forma = -1;
            btPincel.setStyle("-fx-background-image: url('i-pincel.png'); -fx-background-color: transparent; -fx-background-position: center center; -fx-background-repeat: no-repeat; -fx-background-size: contain;");
            painel.setRight(null);
        }
    }

    public void onRecarregar(ActionEvent actionEvent) {
        imageView.setImage(original);
        slZoom.setValue(100);
        alterada = false;
        if (modoDesenho) {
            modoDesenho = false;
            FormasController.forma = -1;
            btPincel.setStyle("-fx-background-image: url('i-pincel.png'); -fx-background-color: transparent; -fx-background-position: center center; -fx-background-repeat: no-repeat; -fx-background-size: contain;");
            painel.setRight(null);
        }
    }

    public boolean onSalvar(ActionEvent actionEvent) {
        File arquivo = new File(caminho);
        if (arquivo != null) {
            BufferedImage bimg = SwingFXUtils.fromFXImage(imageView.getImage(), null);
            String ext = caminho.substring(caminho.lastIndexOf(".") + 1).toLowerCase();
            if (ext.equals("jpg") || ext.equals("jpeg"))
                bimg = removerTransparencia(bimg);
            try {
                if (ImageIO.write(bimg, ext, arquivo))
                    alterada = false;
                return true;
            } catch( Exception e) {
                return false;
            }
        }
        return false;
    }

    public boolean onSalvarComo(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Salvar");
        fc.setInitialDirectory(new File("../"));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "Imagens (*.png, *.jpg, *.bmp)",
                        "*.png",
                        "*.jpg",
                        "*.jpeg",
                        "*.bmp"
                )
        );
        File arquivo = fc.showSaveDialog(null);
        if (arquivo != null) {
            BufferedImage bimg = SwingFXUtils.fromFXImage(imageView.getImage(), null);
            try {
                String ext = caminho.substring(caminho.lastIndexOf(".") + 1).toLowerCase();
                if (ext.equals("jpg") || ext.equals("jpeg"))
                    bimg = removerTransparencia(bimg);
                arquivo = new File(arquivo.getAbsolutePath().substring(0, arquivo.getAbsolutePath().lastIndexOf(".") + 1) + ext);
                if (ImageIO.write(bimg, ext, arquivo)) {
                    caminho = arquivo.getAbsolutePath();
                    original = imageView.getImage();
                    lbFileImage.setText(arquivo.getAbsolutePath() + " (" + original.getWidth() + "x" + original.getHeight() + ")");
                    alterada = false;
                }
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return false;
    }

    private BufferedImage removerTransparencia(BufferedImage bimg) {
        BufferedImage semAlpha = new BufferedImage(bimg.getWidth(), bimg.getHeight(), BufferedImage.TYPE_INT_RGB);
        // Por algum motivo o ImaegIO.write não aceita transparência em arquivos .jpg/.jpeg
        Graphics2D g2d = semAlpha.createGraphics();
        g2d.setBackground(Color.WHITE); // Fundo branco para transparência
        g2d.clearRect(0, 0, bimg.getWidth(), bimg.getHeight());
        g2d.drawImage(bimg, 0, 0, null);
        g2d.dispose();
        return semAlpha;
    }

    public void onSobre(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("sobre-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.setTitle("Sobre o Paint FX");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); // Bloqueia interação com a janela principal

            InputStream caminhoIcone = getClass().getResourceAsStream("/icones/icone.png");
            if (caminhoIcone != null) {
                Image icon = new Image(caminhoIcone);
                stage.getIcons().add(icon);
            }

            stage.showAndWait();
        } catch (Exception e) {
            System.err.println("Erro ao carregar o ícone: " + e.getMessage());
        }
    }

    public void onInfo(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("info-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Informações da imagem");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        InfoController controller = fxmlLoader.getController();
        controller.setInformacoes(arquivo);

        InputStream caminhoIcone = getClass().getResourceAsStream("/icones/icone.png");
        if (caminhoIcone != null) {
            Image icon = new Image(caminhoIcone);
            stage.getIcons().add(icon);
        }

        stage.showAndWait();
    }

    private void desabilitarBotoes(boolean flag) {
        menuRecarregar.setDisable(flag);
        menuSalvar.setDisable(flag);
        menuSalvarComo.setDisable(flag);
        menuFechar.setDisable(flag);
        menuCinza.setDisable(flag);
        menuPB.setDisable(flag);
        menuInv.setDisable(flag);
        menuHor.setDisable(flag);
        menuVer.setDisable(flag);
        menuInfo.setDisable(flag);
        menuBorda.setDisable(flag);
        menuGauss.setDisable(flag);
        menuDilatar.setDisable(flag);
        menuCalorificar.setDisable(flag);
        menuNoise.setDisable(flag);
        miInfo.setDisable(flag);

        btRecarregar.setDisable(flag);
        btSalvar.setDisable(flag);
        btSalvarComo.setDisable(flag);
        btPincel.setDisable(flag);
        btTonsCinza.setDisable(flag);
        btHor.setDisable(flag);
        btVer.setDisable(flag);
        btInfo.setDisable(flag);

        slZoom.setDisable(flag);

        modoDesenho = false;
    }

    public void onEscalaCinza(ActionEvent actionEvent) {
        Image nova = ConversorImagem.escalaCinza(imageView.getImage());
        imageView.setImage(nova);
        alterada = true;
    }

    public void onPretoBranco(ActionEvent actionEvent) {
        Image nova = ConversorImagem.pretoBranco(imageView.getImage());
        imageView.setImage(nova);
        alterada = true;
    }

    public void onInverter(ActionEvent actionEvent) {
        Image nova = ConversorImagem.inverterCores(imageView.getImage());
        imageView.setImage(nova);
        alterada = true;
    }

    public void onEspelharHorizontal(ActionEvent actionEvent) {
        Image nova = ConversorImagem.espelharHorizontal(imageView.getImage());
        imageView.setImage(nova);
        alterada = true;
    }

    public void onEspelharVertical(ActionEvent actionEvent) {
        Image nova = ConversorImagem.espelharVertical(imageView.getImage());
        imageView.setImage(nova);
        alterada = true;
    }

    public void onDetectarBordas(ActionEvent actionEvent) {
        Image nova = ConversorImageJ.detectarBordas(imageView.getImage());
        imageView.setImage(nova);
        alterada = true;
    }

    public void onDesfoque(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("gaussblur-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Desfoque");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        InputStream caminhoIcone = getClass().getResourceAsStream("/icones/icone.png");
        if (caminhoIcone != null) {
            Image icon = new Image(caminhoIcone);
            stage.getIcons().add(icon);
        }

        stage.showAndWait();
        int fator = GaussBlurController.fator;
        if (fator != -1) {
            Image nova = ConversorImageJ.desfoqueGaussiano(imageView.getImage(), fator);
            imageView.setImage(nova);
            alterada = true;
        }
    }

    public void onDilatar(ActionEvent actionEvent) {
        Image nova = ConversorImageJ.dilatar(imageView.getImage());
        imageView.setImage(nova);
        alterada = true;
    }

    public void onCalorificar(ActionEvent actionEvent) {
        Image nova = ConversorImageJ.calorificar(imageView.getImage());
        imageView.setImage(nova);
        alterada = true;
    }

    public void onNoise(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("noise-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Noise");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        InputStream caminhoIcone = getClass().getResourceAsStream("/icones/icone.png");
        if (caminhoIcone != null) {
            Image icon = new Image(caminhoIcone);
            stage.getIcons().add(icon);
        }

        stage.showAndWait();
        int fator = NoiseController.fator;
        if (fator != -1) {
            Image nova = ConversorImageJ.noise(imageView.getImage(), fator);
            imageView.setImage(nova);
            alterada = true;
        }
    }

    public void onTrocarCor(ActionEvent actionEvent) {
        Button botaoSelecionado = (Button) actionEvent.getSource();
        String styleCor = botaoSelecionado.getStyle();
        String backgroundColor = styleCor.replaceAll(".*-fx-background-color:\\s*([^;]+).*", "$1");
        btCor.setStyle("-fx-background-color: " + backgroundColor + ";");

        System.out.println(botaoSelecionado.getId());

        switch (botaoSelecionado.getId()) {
            case "btPreto":
                cor = Color.BLACK;
                break;
            case "btVermelho":
                cor = Color.RED;
                break;
            case "btLaranja":
                cor = Color.ORANGE;
                break;
            case "btAmarelo":
                cor = Color.YELLOW;
                break;
            case "btVerde":
                cor = Color.GREEN;
                break;
            case "btBranco":
                cor = Color.WHITE;
                break;
            case "btCinza":
                cor = Color.GRAY;
                break;
            case "btRosa":
                cor = Color.PINK;
                break;
            case "btTurquesa":
                cor = Color.CYAN;
                break;
            case "btRoxo":
                cor = Color.MAGENTA;
                break;
        }
    }
}
