package in.yash.VerifyIt.GlobalExceptionHandler;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
