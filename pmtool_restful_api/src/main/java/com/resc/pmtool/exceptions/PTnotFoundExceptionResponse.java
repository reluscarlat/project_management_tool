package com.resc.pmtool.exceptions;


public class PTnotFoundExceptionResponse {
    private String projectTaskNotFound;

    public PTnotFoundExceptionResponse(String projectTaskNotFound) {
        this.projectTaskNotFound = projectTaskNotFound;
    }

    public String getProjectTaskNotFound() {
        return projectTaskNotFound;
    }

    public void setProjectTaskNotFound(String projectTaskNotFound) {
        this.projectTaskNotFound = projectTaskNotFound;
    }
}
