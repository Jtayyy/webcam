package fr.webcam.error;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import fr.webcam.exception.GoofyException;
import fr.webcam.message.Analysis;
import fr.webcam.message.MattermostSender;
import fr.webcam.picture.PictureAlteration;
import fr.webcam.time.service.TimeService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@ControllerAdvice
public class ErrorMonitor {
    private final TimeService timeService;
    private final MattermostSender mattermostSender;
    private final PictureAlteration pictureAlteration;

    public ErrorMonitor(TimeService timeService, MattermostSender mattermostSender, PictureAlteration pictureAlteration) {
        this.timeService = timeService;
        this.mattermostSender = mattermostSender;
        this.pictureAlteration = pictureAlteration;
    }

    @ExceptionHandler(value = Throwable.class)
    public void PrisePhoto() throws InterruptedException {

        long elapsedTime = timeService.getElapsedTime();
        boolean firstPic = timeService.isFirstPic();
        int timeBeforeTakeagain = 300000; // In millisecond

        Analysis[] analyses = Analysis.values();
        Random random = new Random();
        int randomIndex = random.nextInt(analyses.length);
        Analysis randomAnalysis = analyses[randomIndex];

        TimeUnit.SECONDS.sleep(2);
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open();

        try {
            BufferedImage webcamPicture = webcam.getImage();

            if(elapsedTime > timeBeforeTakeagain || firstPic) {
                mattermostSender.messageSender(randomAnalysis, webcamPicture, "png");
                timeService.setFirstPic(false);

            } else {
                BufferedImage wastedPicture = pictureAlteration.layeringImages(webcamPicture);
                mattermostSender.messageSender(randomAnalysis, wastedPicture, "png");
            }

            throw new GoofyException("This is what I call a mistake.");

        } catch (IOException e) {
            throw new GoofyException(e);
        } finally {
            webcam.close();
        }
    }
}
