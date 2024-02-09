package fr.webcam.time.service;

import fr.webcam.time.model.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeServiceImpl implements TimeService {

    private final Time time;

    @Autowired
    public TimeServiceImpl(Time time) {
        this.time = time;
    }

    @Override
    public long getElapsedTime() {
        return time.getTimeElapsed();
    }

    @Override
    public boolean isFirstPic() {
        return time.isFirstPic();
    }

    @Override
    public void setFirstPic(Boolean bool) {
        time.setFirstPic(bool);
    }
}