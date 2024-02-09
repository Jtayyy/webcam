package fr.webcam.time.model;

import org.springframework.stereotype.Component;

@Component
public class Time {
    private final long startTime;
    private boolean firstPic;

    public Time() {
        this.startTime = System.currentTimeMillis();
        this.firstPic = true;
    }

    public long getTimeElapsed() {
        return System.currentTimeMillis() - startTime;
    }

    public boolean isFirstPic() {
        return firstPic;
    }

    public void setFirstPic(boolean firstPic) {
        this.firstPic = firstPic;
    }
}