package com.example.shopping.exception;

import com.example.shopping.dto.ProductDto;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GenericExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            GenericExceptionHandler.class);

    @ExceptionHandler(JDBCConnectionException.class)
    public String handleConnectionError(Exception ex,Model model) {

        LOGGER.error(ex.getMessage(), ex);
        model.addAttribute("message",ex.getMessage());
        return "error";
    }
    @ExceptionHandler({Exception.class})
    public String handleGeneralError(HttpServletRequest request,
                                     HttpServletResponse response, Exception ex,Model model) {
        model.addAttribute("message",ex.getMessage());
        LOGGER.error(ex.getMessage(), ex);

        // do something with request and response
        return "error";
    }
    @ExceptionHandler({ProductNotFoundException.class})
    public String handleProductNotFoundError(Exception ex, Model model) {
        model.addAttribute("message",ex.getMessage());
        LOGGER.error(ex.getMessage(),ex);
        return "error";
    }
    @ExceptionHandler({ConstraintViolationException.class})
    public String handleConstraintViolation(Exception e,Model model){
        model.addAttribute("violation",e.getLocalizedMessage());
        model.addAttribute("product",new ProductDto());
        return "add-product";
    }
}
