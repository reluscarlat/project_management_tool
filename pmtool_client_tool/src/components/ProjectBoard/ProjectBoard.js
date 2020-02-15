import React, { Component } from "react";
import { Link } from "react-router-dom";
import Backlog from "./Backlog";
import { connect } from "react-redux";
import { getBacklog } from "../../actions/backlogActions";
import PropTypes from "prop-types";

class ProjectBoard extends Component {
  constructor() {
    super();
    this.state = {
      errors: {}
    };
  }
  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getBacklog(id);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  render() {
    const { id } = this.props.match.params;
    const { project_tasks } = this.props.backlog;
    let BoardContent, AddTaskButton;
    const createBoardContent = (project_tasks, errors) => {
      if (project_tasks.length < 1) {
        if (errors.projectNotFound) {
          return (
            <div className="alert alert-danger text-center" role="alert">
              {errors.projectNotFound}
            </div>
          );
        } else if (errors.projectIdentifier) {
          return (
            <div className="alert alert-danger text-center" role="alert">
              {errors.projectIdentifier}
            </div>
          );
        } else {
          return (
            <div className="alert alert-info text-center" role="alert">
              No Tasks on this project board.
            </div>
          );
        }
      }
      return <Backlog key={id} project_tasks_prop={project_tasks} />;
    };
    BoardContent = createBoardContent(project_tasks, this.state.errors);

    const createAddTaskButton = (id, errors) => {
      if (!(errors.projectNotFound || errors.projectIdentifier)) {
        return (
          <Link to={`/addProjectTask/${id}`} className="btn btn-primary mb-3">
            <i className="fas fa-plus-circle"> Create Project Task</i>
          </Link>
        );
      }
    };
    AddTaskButton = createAddTaskButton(id, this.state.errors);

    return (
      <div className="container">
        {AddTaskButton}
        <br />
        <hr />
        {BoardContent}
      </div>
    );
  }
}

ProjectBoard.propTypes = {
  backlog: PropTypes.object.isRequired,
  getBacklog: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => {
  return {
    backlog: state.backlog,
    errors: state.errors
  };
};

export default connect(mapStateToProps, { getBacklog })(ProjectBoard);
