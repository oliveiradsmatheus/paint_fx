package matheus.bcc.paintfx.util;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ConversorImageJ {
    public static Image detectarBordas(Image image) {
        ImagePlus imagePlus = new ImagePlus();
        imagePlus.setImage(SwingFXUtils.fromFXImage(image, null));
        ImageProcessor imageProcessor = imagePlus.getProcessor();
        imageProcessor.findEdges();
        return SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
    }

    public static Image desfoqueGaussiano (Image image, double fator) {
        ImagePlus imagePlus = new ImagePlus();
        imagePlus.setImage(SwingFXUtils.fromFXImage(image, null));
        ImageProcessor imageProcessor = imagePlus.getProcessor();
        imageProcessor.blurGaussian(fator);
        return SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
    }

    public static Image dilatar(Image image) {
        ImagePlus imagePlus = new ImagePlus();
        imagePlus.setImage(SwingFXUtils.fromFXImage(image, null));
        ImageProcessor imageProcessor = imagePlus.getProcessor();
        imageProcessor.dilate();
        return SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
    }

    public static Image calorificar(Image image) {
        ImagePlus imagePlus = new ImagePlus();
        imagePlus.setImage(SwingFXUtils.fromFXImage(image, null));
        ImageProcessor imageProcessor = imagePlus.getProcessor();
        imageProcessor.autoThreshold();
        return SwingFXUtils.toFXImage(imagePlus.getBufferedImage(),null);
    }


    public static Image noise(Image image, double fator) {
        ImagePlus imagePlus = new ImagePlus();
        imagePlus.setImage(SwingFXUtils.fromFXImage(image, null));
        ImageProcessor imageProcessor = imagePlus.getProcessor();
        imageProcessor.noise(fator);
        return SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
    }
}
