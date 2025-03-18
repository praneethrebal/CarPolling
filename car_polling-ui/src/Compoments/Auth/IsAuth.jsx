import { Navigate } from "react-router-dom";
import { useAuth } from "./AuthContext";

const IsAuth = ({ children }) => {
  const { token } = useAuth();
  if (!token) return <Navigate to="/" replace />;
  return children;
};

export default IsAuth;
