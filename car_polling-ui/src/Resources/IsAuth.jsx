import React from "react";
import { useAuth } from "./AuthContext";
import { Navigate } from "react-router-dom";

const IsAuth = ({ children }) => {
  const { token } = useAuth();
  if (!token) return <Navigate to="/" replace />;
  return children;
};

export default IsAuth;
