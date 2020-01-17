package com.resc.pmtool.services;

import com.resc.pmtool.domain.Project;
import com.resc.pmtool.exceptions.ProjectIdException;
import com.resc.pmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectIdentifier) {
        return projectRepository.findByProjectIdentifier(projectIdentifier);
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

}
