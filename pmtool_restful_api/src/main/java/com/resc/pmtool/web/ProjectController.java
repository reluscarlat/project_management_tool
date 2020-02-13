package com.resc.pmtool.web;

import com.resc.pmtool.domain.Project;
import com.resc.pmtool.exceptions.ProjectIdException;
import com.resc.pmtool.services.MapValidationErrorsService;
import com.resc.pmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorsService mapValidationErrorsService;

    @PostMapping("")
    private ResponseEntity<?> createNewProject(@Valid @RequestBody Project project,
                                               BindingResult bindingResult, Principal principal) {

        ResponseEntity<?> responseEntity = mapValidationErrorsService.mapErrorsService(bindingResult);
        if(responseEntity != null) return responseEntity;

        projectService.saveOrUpdateProject(project, principal.getName());
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);

    }

    @GetMapping("/{projectIdentifier}")
    private ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectIdentifier, Principal principal) {

        Project project = projectService.findProjectByIdentifier(projectIdentifier.toUpperCase(), principal.getName());

        if(project == null) {
            throw new ProjectIdException("Project ID '" + projectIdentifier.toUpperCase() + "' does not exist.");
        }

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    private Iterable<Project> getAllProjects(Principal principal) {
        return projectService.findAllProjects(principal.getName());
    }

    @DeleteMapping("/{projectIdentifier}")
    private ResponseEntity<?> deleteProjectByIdentifier(@PathVariable String projectIdentifier, Principal principal) {
        projectService.deleteProjectByIdentifier(projectIdentifier.toUpperCase(), principal.getName());
        return new ResponseEntity<String>("Project with ID '" + projectIdentifier.toUpperCase() + "' was deleted.", HttpStatus.OK);
    }
}
