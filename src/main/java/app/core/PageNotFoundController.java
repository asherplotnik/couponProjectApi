package app.core;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class PageNotFoundController {
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleError404() {
            return "redirect:/index.html";
    }
}