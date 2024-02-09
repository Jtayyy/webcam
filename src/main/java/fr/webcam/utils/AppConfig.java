package fr.webcam.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${mattermostUrl}")
    private String mattermostUrl;

    @Value("${channelId}")
    private String channelId;

    @Value("${authToken}")
    private String authToken;

    public String getMattermostUrl() {
        return mattermostUrl;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getAuthToken() {
        return authToken;
    }
}