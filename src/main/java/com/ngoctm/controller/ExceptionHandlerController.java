package com.ngoctm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception ex , Model model){
        model.addAttribute("message", ex.getMessage());
        return "error/page_error";
    }

}
