package mx.edu.utez.minicasino.exception.customExceptions;

public class BadRequestException extends  RuntimeException{

    public BadRequestException (String message) {
        super(message);
    }
}
