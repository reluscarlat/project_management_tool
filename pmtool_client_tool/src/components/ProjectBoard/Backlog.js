import React, { Component } from "react";
import ProjectTask from "./ProjectTasks/ProjectTask";

const TO_DO = "TO_DO";
const IN_PROGRESS = "IN_PROGRESS";
const DONE = "DONE";

class Backlog extends Component {
  render() {
    const { project_tasks_prop } = this.props;
    let todo_tasks = [];
    let in_progress_tasks = [];
    let done_tasks = [];

    for (let task of project_tasks_prop) {
      switch (task.status) {
        case TO_DO:
          todo_tasks.push(task);
          break;
        case IN_PROGRESS:
          in_progress_tasks.push(task);
          break;
        case DONE:
          done_tasks.push(task);
          break;
        default:
          break;
      }
    }

    return (
      <div>
        {/* <!-- Backlog STARTS HERE --> */}
        <div className="container">
          <div className="row">
            <div className="col-md-4">
              <div className="card text-center mb-2">
                <div className="card-header bg-secondary text-white">
                  <h3>TO DO</h3>
                </div>
              </div>
              {/* <!-- SAMPLE PROJECT TASK STARTS HERE --> */}
              {todo_tasks.map(project_task => (
                <ProjectTask
                  key={project_task.id}
                  project_task={project_task}
                />
              ))}
              {/* <!-- SAMPLE PROJECT TASK ENDS HERE --> */}
            </div>
            <div className="col-md-4">
              <div className="card text-center mb-2">
                <div className="card-header bg-primary text-white">
                  <h3>In Progress</h3>
                </div>
              </div>
              {/* <!-- SAMPLE PROJECT TASK STARTS HERE --> */}
              {in_progress_tasks.map(project_task => (
                <ProjectTask
                  key={project_task.id}
                  project_task={project_task}
                />
              ))}
              {/* <!-- SAMPLE PROJECT TASK ENDS HERE --> */}
            </div>
            <div className="col-md-4">
              <div className="card text-center mb-2">
                <div className="card-header bg-success text-white">
                  <h3>Done</h3>
                </div>
              </div>
              {/* <!-- SAMPLE PROJECT TASK STARTS HERE --> */}
              {done_tasks.map(project_task => (
                <ProjectTask
                  key={project_task.id}
                  project_task={project_task}
                />
              ))}
              {/* <!-- SAMPLE PROJECT TASK ENDS HERE --> */}
            </div>
          </div>
        </div>
        {/* <!-- Backlog ENDS HERE --> */}
      </div>
    );
  }
}

export default Backlog;
