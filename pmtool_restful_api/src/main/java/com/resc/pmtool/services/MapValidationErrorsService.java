package com.resc.pmtool.services;

import com.resc.pmtool.domain.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;

@Service
public class MapValidationErrorsService {

    public ResponseEntity<?> mapErrorsService(Project project, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            HashMap<String, String> errors = new HashMap<>();
            fieldErrors.forEach(e ->
                errors.put(e.getField(), e.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return null;
    }
}
