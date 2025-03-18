import React, { useState } from "react";
import "../Compoments/Styles/Login.css";
import BaseUrl from "./URL's/BaseUrl";
import { useAuth } from "./Auth/AuthContext";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, SetPassword] = useState("");
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
      { headers: { "Content-Type": "application/json" } }
    )
      .then((res) => {
        if (res.data.role === "PASSENGER") {
          navigate("passeange");
        } else if (res.data.role === "DRIVER") {
          navigate("driver");
        }
        saveToken(res.data.token);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <div className="main">
      <div className="login-container">
        {/* Login Options */}
        <div className="login-options">
          <button className="login-btn">Login as Driver</button>
          <button className="login-btn">Login as Passenger</button>
        </div>

        <h2>Login</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-elements">
            <label>Username</label>
            <input
              type="text"
              placeholder="Enter your username"
              onChange={(e) => setUsername(e.target.value)}
              required
            />

            <label>Password</label>
            <input
              type="password"
              placeholder="Enter your password"
              onChange={(e) => SetPassword(e.target.value)}
              required
            />

            <button type="submit">Login</button>
          </div>
        </form>

        {/* Register Option */}
        <div className="register">
          <p>Don't have an account?</p>
          <button className="register-btn">Register</button>
        </div>
      </div>
    </div>
  );
};

export default Login;
