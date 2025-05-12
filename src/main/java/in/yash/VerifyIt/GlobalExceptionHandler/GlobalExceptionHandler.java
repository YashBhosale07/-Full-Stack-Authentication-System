package in.yash.VerifyIt.GlobalExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage>handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){
        ErrorMessage errorMessage=new ErrorMessage(ex.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorMessage,HttpStatus.CONFLICT);
    }

}
