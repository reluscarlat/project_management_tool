package com.resc.pmtool.repositories;

import com.resc.pmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
    List<ProjectTask> findAllByProjectIdentifierOrderByPriority(String projectIdentifier);
    ProjectTask findByProjectSequence(String sequence);
    ProjectTask findByProjectIdentifierAndProjectSequence(String id, String sequence);
}
