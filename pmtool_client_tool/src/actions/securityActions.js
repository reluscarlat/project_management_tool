import axios from "axios";
import { GET_ERRORS, SET_CURENT_USER, CLEAR_ERRORS } from "./types";
import setJWTToken from "../securityUtils/setJWTToken";
import jwt_decode from "jwt-decode";

export const createNewUser = (newUser, history) => async dispatch => {
  try {
    await axios.post("/api/users/register", newUser);
    history.push("/login");
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const login = LoginRequest => async dispatch => {
  try {
    // post => login request
    const res = await axios.post("/api/users/login", LoginRequest);
    // extract token from res
    const { token } = res.data;
    // store the token in the localStorage
    localStorage.setItem("jwtToken", token);
    // set the received token in the headers ***
    setJWTToken(token);
    // decode token on React
    const decoded = jwt_decode(token);
    // dispatch to the securityReducer
    dispatch({
      type: SET_CURENT_USER,
      payload: decoded
    });
    dispatch({
      type: CLEAR_ERRORS,
      payload: {}
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const logout = () => dispatch => {
  localStorage.removeItem("jwtToken"); // remove jwtToken from local storage
  setJWTToken(false); // remove jwtToken from Authentication Header
  dispatch({
    type: SET_CURENT_USER, // set user to empty and valitToken to false
    payload: {}
  });
};
