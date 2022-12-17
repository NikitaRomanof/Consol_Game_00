package org.example;

public class IllegalParametersException extends RuntimeException {

    public IllegalParametersException() {
        super();
    }
    public IllegalParametersException(String message) {
        super(message);
    }
}
