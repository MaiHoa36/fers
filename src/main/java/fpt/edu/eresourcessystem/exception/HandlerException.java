package fpt.edu.eresourcessystem.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {
    @ExceptionHandler(value = {AccountNotExistedException.class})
    public String handler(AccountNotExistedException exception) {
        return "invalid";
    }
}
