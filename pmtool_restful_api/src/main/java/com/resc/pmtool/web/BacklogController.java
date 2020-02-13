package com.resc.pmtool.web;

import com.resc.pmtool.domain.ProjectTask;
import com.resc.pmtool.services.MapValidationErrorsService;
import com.resc.pmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorsService mapValidationErrorsService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
                                            @PathVariable String backlog_id, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorsService.mapErrorsService(result);
        if(errorMap != null) return errorMap;
        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());
        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal) {
        return projectTaskService.findBacklogById(backlog_id, principal.getName());
    }

    @GetMapping("/{backlog_id}/{pt_sequence}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id
            , @PathVariable String pt_sequence, Principal principal) {
        ProjectTask projectTask = projectTaskService.findPTbyProjectSequence(backlog_id, pt_sequence, principal.getName());
        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
                                               @PathVariable String backlog_id, @PathVariable String pt_id, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorsService.mapErrorsService(result);
        if(errorMap != null) return errorMap;
        ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask, backlog_id, pt_id, principal.getName());
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal) {
        projectTaskService.deletePTbyProjectSequence(backlog_id, pt_id, principal.getName());
        return  new ResponseEntity<>("Project Task with sequence '" + pt_id +
                "' was deleted.",HttpStatus.OK);
    }
}
