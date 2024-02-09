package fr.webcam.picture;

import fr.webcam.exception.GoofyException;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class PictureAlteration {

    public BufferedImage layeringImages(BufferedImage beforeFilter) throws IOException {

        BufferedImage blackAndWhitePicture = applyBlackAndWhite(beforeFilter);

        BufferedImage filter = loadImage("src/main/resources/assets/wasted.png");

        if (blackAndWhitePicture == null || filter == null) { throw new GoofyException("Fail during images loading."); }
        int x = (blackAndWhitePicture.getWidth() - filter.getWidth()) / 2;

        BufferedImage imageSuperposee = new BufferedImage(blackAndWhitePicture.getWidth(), blackAndWhitePicture.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imageSuperposee.createGraphics();
        g2d.drawImage(blackAndWhitePicture, 0, 0, null);

        float alpha = 0.5f; // Transparence, 0 = transparent, 1 = opaque
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(filter, x, 0, null);
        g2d.dispose();

        return(imageSuperposee);
    }

    private BufferedImage applyBlackAndWhite(BufferedImage beforeFilter) {
        return Scalr.apply(beforeFilter, Scalr.OP_GRAYSCALE);
    }

    private BufferedImage loadImage(String fileName) {
        try {
            return ImageIO.read(new File(fileName));
        } catch (IOException e) {
            throw new GoofyException(e);
        }
    }
}
