package bg.adastragrp.adastrafaceapp.exceptions;

public class NoInternetException extends Exception {

    public NoInternetException() {
    }

    public NoInternetException(String message) {
        super(message);
    }

    public NoInternetException(Throwable cause) {
        super(cause);
    }

    public NoInternetException(String message, Throwable cause) {
        super(message, cause);
    }
}
