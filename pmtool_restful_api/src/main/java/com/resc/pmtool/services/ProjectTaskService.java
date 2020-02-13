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

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask , String username) {

            //PTs to be added to a specific project, project != null, BL exists   `
            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();// backlogRepository.findByProjectIdentifier(projectIdentifier);
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
            if(projectTask.getPriority() == null || projectTask.getPriority() == 0) { // In the future we need the
                projectTask.setPriority(3);
            }
            //Initial status when status is null
            if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }
            return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String id,String username) {
        projectService.findProjectByIdentifier(id, username);
        return projectTaskRepository.findAllByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTbyProjectSequence(String backlog_id, String pt_sequence, String username) {
        //make sure for searching on an existing backlog
        projectService.findProjectByIdentifier(backlog_id, username);

        //make sure that task exists
        ProjectTask projectTask = projectTaskRepository.
                findByProjectSequence(pt_sequence);

        if(projectTask == null) {
            throw new PTnotFoundException("Project Task '" + pt_sequence + "' not found");
        }
        if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new PTnotFoundException("Project Task with sequence '" + pt_sequence
                    + "' doesn't exist in backlog of Project with ID '" + backlog_id + "'.");
        }
        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username) {
        findPTbyProjectSequence(backlog_id, pt_id, username);
        return projectTaskRepository.save(updatedTask);
    }

    public void deletePTbyProjectSequence(String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findPTbyProjectSequence(backlog_id, pt_id, username);
        projectTaskRepository.delete(projectTask);
    }
}
