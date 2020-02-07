package com.resc.pmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PTnotFoundException extends RuntimeException {
    public PTnotFoundException(String message) {
        super(message);
    }
}
