package matheus.bcc.paintfx.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ConversorImagem {
    private static BufferedImage drawingImage;
    private static Graphics2D graphics;
    public static Image escalaCinza(Image img) {


        BufferedImage bimg = SwingFXUtils.fromFXImage(img, null);

        int[] pixel = {0, 0, 0, 0}; // {R, G, B, transparência} de 0 a 255 em cada posição.
        int media;

        WritableRaster raster = bimg.getRaster();
        for (int i = 0; i < raster.getWidth(); i++)
            for (int j = 0; j < raster.getHeight(); j++) {
                raster.getPixel(i, j, pixel);
                media = (int)(pixel[0]*0.2126 + pixel[1]*0.7152 + pixel[2]*0.0722);
                pixel[0] = pixel[1] = pixel[2] = media;
                raster.setPixel(i, j, pixel);
            }

        return SwingFXUtils.toFXImage(bimg, null);
    }

    public static Image pretoBranco(Image img) {
        img = escalaCinza(img);
        BufferedImage bimg = SwingFXUtils.fromFXImage(img, null);

        int[] pixel = {0, 0, 0, 0};
        int media = 0;

        WritableRaster raster = bimg.getRaster();
        for (int i = 0; i < raster.getWidth(); i++)
            for (int j = 0; j < raster.getHeight(); j++) {
                raster.getPixel(i, j, pixel);
                media += pixel[0];
            }

        media /= raster.getWidth() * raster.getHeight();

        for (int i = 0; i < raster.getWidth(); i++)
            for (int j = 0; j < raster.getHeight(); j++) {
                raster.getPixel(i, j, pixel);
                if (pixel[0] < media/2)
                    pixel[0] = pixel[1] = pixel[2] = 0;
                else
                    pixel[0] = pixel[1] = pixel[2] = 255;
                raster.setPixel(i, j, pixel);
            }

        return SwingFXUtils.toFXImage(bimg, null);
    }

    public static Image inverterCores(Image img) {
        BufferedImage bimg = SwingFXUtils.fromFXImage(img, null);

        int[] pixel = {0, 0, 0, 0};

        WritableRaster raster = bimg.getRaster();
        for (int i = 0; i < raster.getWidth(); i++)
            for (int j = 0; j < raster.getHeight(); j++) {
                raster.getPixel(i, j, pixel);
                pixel[0] = 255 - pixel[0];
                pixel[1] = 255 - pixel[1];
                pixel[2] = 255 - pixel[2];
                raster.setPixel(i, j, pixel);
            }

        return SwingFXUtils.toFXImage(bimg, null);
    }

    public static Image espelharHorizontal(Image img) {
        BufferedImage bimg = SwingFXUtils.fromFXImage(img, null);

        int[] pixel = {0, 0, 0, 0}, aux = new int[4];

        WritableRaster raster = bimg.getRaster();
        for (int i = 0; i < raster.getWidth()/2; i++)
            for (int j = 0; j < raster.getHeight(); j++) {
                raster.getPixel(i, j, pixel);
                raster.getPixel(raster.getWidth() - i - 1, j, aux);
                raster.setPixel(raster.getWidth() - i - 1, j, pixel);
                raster.setPixel(i, j, aux);
            }

        return SwingFXUtils.toFXImage(bimg, null);
    }

    public static Image espelharVertical(Image img) {
        BufferedImage bimg = SwingFXUtils.fromFXImage(img, null);

        int[] pixel = {0, 0, 0, 0}, aux = new int[4];

        WritableRaster raster = bimg.getRaster();
        for (int i = 0; i < raster.getWidth(); i++)
            for (int j = 0; j < raster.getHeight() / 2; j++) {
                raster.getPixel(i, j, pixel);
                raster.getPixel(i, raster.getHeight() - j - 1, aux);
                raster.setPixel(i, raster.getHeight() - j - 1, pixel);
                raster.setPixel(i, j, aux);
            }

        return SwingFXUtils.toFXImage(bimg, null);
    }

    public static Image desenharNaImagem(Image img, double col, double lin, double tamanho, Color cor) {
        BufferedImage bimg = SwingFXUtils.fromFXImage(img, null);
        Graphics2D g2d = bimg.createGraphics();

        g2d.setColor(cor);

        int largura = (int) (10 * tamanho / 100);
        g2d.fillOval((int)(col - (double) largura / 2), (int)(lin - (double) largura / 2), largura, largura);
        g2d.dispose();
        return SwingFXUtils.toFXImage(bimg, null);
    }

    public static Image desenharRetangulo(Image img, double x1, double y1, double x2, double y2, int espessura, Color cor) {
        BufferedImage bimg = SwingFXUtils.fromFXImage(img, null);
        Graphics2D g2d = bimg.createGraphics();

        g2d.setColor(cor);
        g2d.setStroke(new BasicStroke(espessura));

        int x = (int) Math.min(x1, x2);
        int y = (int) Math.min(y1, y2);
        int largura = (int) Math.abs(x1 - x2);
        int altura = (int) Math.abs(y1 - y2);

        g2d.drawRect(x, y, largura, altura);
        g2d.dispose();

        return SwingFXUtils.toFXImage(bimg, null);
    }

    public static Image desenharCirculo(Image img, double x1, double y1, double x2, double y2, int espessura, Color cor) {
        BufferedImage bimg = SwingFXUtils.fromFXImage(img, null);
        Graphics2D g2d = bimg.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(cor);
        g2d.setStroke(new BasicStroke(espessura));

        int x = (int) Math.min(x1, x2);
        int y = (int) Math.min(y1, y2);
        int largura = (int) Math.abs(x1 - x2);
        int altura = (int) Math.abs(y1 - y2);

        g2d.drawOval(x, y, largura, altura);
        g2d.dispose();

        return SwingFXUtils.toFXImage(bimg, null);
    }

    public static Image adicionarTexto(Image img, String texto, Font fonte, Color cor, int x, int y) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(img, null);

        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(fonte);
        g2d.setColor(cor);
        g2d.drawString(texto, x, y);
        g2d.dispose();
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }
}
