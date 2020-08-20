package com.ngoctm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@ControllerAdvice
public class ExceptionHandlerController {

   // @ExceptionHandler(Exception.class)
    @ResponseBody
    public String exceptionHandler(Exception ex){

        return ex.getMessage();
    }

}
