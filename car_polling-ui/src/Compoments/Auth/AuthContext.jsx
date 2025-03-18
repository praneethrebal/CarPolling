import { createContext, useContext, useState } from "react";

const AuthContext = createContext();
export const Authpro = ({ children }) => {
  const [token, setToken] = useState();
  const saveToken = (token) => {
    setToken(token);
    localStorage.setItem("token", token);
  };

  const logOut = () => {
    setToken(null);
    localStorage.removeItem("token");
  };

  return (
    <AuthContext.Provider value={{ token, saveToken, logOut }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
