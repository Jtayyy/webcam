package fr.webcam.exception;

public class GoofyException extends RuntimeException {
    public GoofyException() { }

    public GoofyException(Exception e) { e.printStackTrace(); }
    public GoofyException(String message) { super(message); }
}