package com.resc.pmtool.services;

import com.resc.pmtool.domain.ProjectTask;
import com.resc.pmtool.repositories.BacklogRepository;
import com.resc.pmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask() {
        //PTs to be added to a specific project, project != null, BL exists
        //set the bl to pt
        //we want out project sequence to be like this: IDPRO-1, IDPRO-2
        //Update the BL SEQUENCE

        //INITIAL priority when priority is null
        //Initial status when status is null

    }
}
