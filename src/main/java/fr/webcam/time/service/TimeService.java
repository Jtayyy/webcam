package fr.webcam.time.service;

public interface TimeService {
    long getElapsedTime();
    boolean isFirstPic();
    void setFirstPic(Boolean bool);
}