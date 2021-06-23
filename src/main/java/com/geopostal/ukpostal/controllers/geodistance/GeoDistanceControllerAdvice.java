package com.geopostal.ukpostal.controllers.geodistance;

import com.geopostal.ukpostal.exceptions.PostcodeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GeoDistanceControllerAdvice {

    //This controller is used for to throw PostcodeNotFoundException
    //It will redirect to HTTP 404 Not found.

    @ResponseBody
    @ExceptionHandler(PostcodeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(PostcodeNotFoundException ex) {
        return ex.getMessage();
    }
}
