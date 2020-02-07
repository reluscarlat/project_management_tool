package com.resc.pmtool.services;

import com.resc.pmtool.domain.Backlog;
import com.resc.pmtool.domain.ProjectTask;
import com.resc.pmtool.exceptions.PTnotFoundException;
import com.resc.pmtool.exceptions.ProjectNotFoundException;
import com.resc.pmtool.repositories.BacklogRepository;
import com.resc.pmtool.repositories.ProjectRepository;
import com.resc.pmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        try {
            //PTs to be added to a specific project, project != null, BL exists   `
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            //set the bl to pt
            projectTask.setBacklog(backlog);
            //we want out project sequence to be like this: IDPRO-1, IDPRO-2
            Integer backlogSequence = backlog.getPTSequence();
            //Update the BL SEQUENCE
            backlogSequence++;
            backlog.setPTSequence(backlogSequence);

            //Add Sequence to the Project Task
            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            //INITIAL priority when priority is null
            if(projectTask.getPriority() == null) { // In the future we need the
                projectTask.setPriority(3);
            }
            //Initial status when status is null
            if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }
            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project with ID '" + projectIdentifier.toUpperCase() + "' doesn't exist.");
        }
    }

    public Iterable<ProjectTask> findBacklogById(String id) {
        if(projectRepository.findByProjectIdentifier(id) == null) {
            throw new ProjectNotFoundException("Project with ID '" + id.toUpperCase() + "' doesn't exist.");
        }
        return projectTaskRepository.findAllByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTbyProjectSequence(String backlog_id, String pt_sequence) {
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog == null ) {
            throw new ProjectNotFoundException("Project with ID '" + backlog_id.toUpperCase()
                    + "' doesn't exist.");
        }
        ProjectTask projectTask = projectTaskRepository.
                findByProjectIdentifierAndProjectSequence(backlog_id, pt_sequence);
        if(projectTask == null) {
            throw new PTnotFoundException("Project Task with sequence '" + pt_sequence
                    + "' doesn't exist in backlog of Project with ID '" + backlog_id + "'.");
        }
        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
        findPTbyProjectSequence(backlog_id, pt_id);
        return projectTaskRepository.save(updatedTask);
    }

    public void deletePTbyProjectSequence(String backlog_id, String pt_id) {
        ProjectTask projectTask = findPTbyProjectSequence(backlog_id, pt_id);
        projectTaskRepository.delete(projectTask);
    }
}
