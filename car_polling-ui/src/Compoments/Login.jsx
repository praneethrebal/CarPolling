import React, { useState } from "react";
import { useAuth } from "../Resources/AuthContext";
import BaseUrl from "../Resources/BaseUrl";
import "../Styles/Login.css";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const { saveToken } = useAuth();
  const navigate = useNavigate();
  const handleSubmit = (e) => {
    e.preventDefault();
    BaseUrl.post(
      "/login",
      {
        username: username,
        password: password,
      },
      {
        headers: { "Content-Type": "application/json" },
      }
    )
      .then((res) => {
        saveToken(res.data.token);
        if (res.data.role !== "DRIVER") navigate("/serchRide");
        else navigate("/driver");
      })
      .catch((err) => {
        if (err.status === 401) alert("Check username and Password");
        else alert("Check Network Connection");
      });
  };

  return (
    <div className="login-main">
      <div className="login-container_main">
        <div className="login-form">
          <span className="login-heading">Login</span>
          <form onSubmit={handleSubmit}>
            <div className="login-input-field">
              <div className="login-form-group">
                <label>Username:</label>
                <input
                  type="text"
                  placeholder="Enter username"
                  className="username"
                  onChange={(e) => setUsername(e.target.value)}
                  required
                />
              </div>
              <div className="login-form-group">
                <label>Password:</label>
                <input
                  type="password"
                  placeholder="Password"
                  className="password"
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
              </div>
            </div>
            <div className="login-submit">
              <button type="submit">Login</button>
              <span>New User?</span>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Login;
