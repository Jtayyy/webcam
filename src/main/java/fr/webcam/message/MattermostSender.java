package fr.webcam.message;

import fr.webcam.utils.AppConfig;
import okhttp3.*;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class MattermostSender {

    private final AppConfig appConfig;
    public MattermostSender(AppConfig appConfig) { this.appConfig = appConfig; }

    public void messageSender(Analysis randomAnalysis, BufferedImage imageToWrite, String fileExtension) throws IOException {

        final String MESSAGE = randomAnalysis.getDescription();
        final String filePath = String.format("src/main/resources/assets/toMattermost.%s", fileExtension);

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse(String.format("image/%s", fileExtension));

        File outputfile = new File(filePath);
        ImageIO.write(imageToWrite, fileExtension, outputfile);

        // File request part building
        RequestBody fileBody = RequestBody.create(outputfile, mediaType);

        // Formulary part building
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("channel_id", appConfig.getChannelId())
                .addFormDataPart("files", filePath, fileBody)
                .build();

        // Request to send the file building
        Request requestUpload = new Request.Builder()
                .url(appConfig.getMattermostUrl() + "/api/v4/files")
                .addHeader("Authorization", "Bearer " + appConfig.getAuthToken())
                .post(requestBody)
                .build();

        // Sending HTTP request below
        String responseString;
        try(Response responseUpload = client.newCall(requestUpload).execute()) {
            responseString = responseUpload.body().string();
        }

        // Getting file ID (on mattermost server) in HTTP response
        String fileId = responseString.substring(responseString.indexOf("\"id\":\"") + 6);
        fileId = fileId.substring(0, fileId.indexOf("\""));

        // Request building for message sending with attachment
        RequestBody messageBody = RequestBody.create(null, "{\"channel_id\":\"" + appConfig.getChannelId() + "\",\"message\":\"" + MESSAGE + "\",\"file_ids\":[\"" + fileId + "\"]}");
        Request requestMessage = new Request.Builder()
                .url(appConfig.getMattermostUrl() + "/api/v4/posts")
                .addHeader("Authorization", "Bearer " + appConfig.getAuthToken())
                .post(messageBody)
                .build();

        // Sending HTTP request below
        try(Response responseMessage = client.newCall(requestMessage).execute()) {
            System.out.println(responseMessage.body().string());
        }
    }
}