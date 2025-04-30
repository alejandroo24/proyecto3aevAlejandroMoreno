package exceptions;

public class DatoIncorrectoException extends RuntimeException {
    public DatoIncorrectoException(String message) {
        super("El dato ingresado es incorrecto: " + message);
    }
}
