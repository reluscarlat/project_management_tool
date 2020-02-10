import axios from "axios";
import {
  GET_ERRORS,
  GET_BACKLOG,
  GET_PROJECT_TASK,
  DELETE_PROJECT_TASK
} from "./types";

export const createProjectTask = (
  backlog_id,
  project_task,
  history
) => async dispatch => {
  try {
    await axios.post(`/api/backlog/${backlog_id}`, project_task);
    history.push(`/projectBoard/${backlog_id}`);
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const getBacklog = backlog_id => async dispatch => {
  try {
    const res = await axios.get(`/api/backlog/${backlog_id}`);
    dispatch({
      type: GET_BACKLOG,
      payload: res.data
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const getProjectTask = (
  backlog_id,
  pt_sequence,
  history
) => async dispatch => {
  try {
    const res = await axios.get(`/api/backlog/${backlog_id}/${pt_sequence}`);
    dispatch({
      type: GET_PROJECT_TASK,
      payload: res.data
    });
  } catch (err) {
    history.push("/dashboard");
  }
};

export const updateProjectTask = (
  backlog_id,
  pt_sequence,
  project_task,
  history
) => async dispatch => {
  try {
    await axios.patch(
      `/api/backlog/${backlog_id}/${pt_sequence}`,
      project_task
    );
    history.push(`/projectBoard/${backlog_id}`);
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const deleteProjectTask = (
  backlog_id,
  pt_sequence,
  history
) => async dispatch => {
  if (
    window.confirm(
      "Are you sure you want to delete the `" + pt_sequence + "` task?"
    )
  ) {
    await axios.delete(`/api/backlog/${backlog_id}/${pt_sequence}`);
    dispatch({
      type: DELETE_PROJECT_TASK,
      payload: pt_sequence
    });
  }
};
