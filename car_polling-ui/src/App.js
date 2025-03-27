import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import Login from "./Compoments/Login";
import { AuthPro } from "./Resources/AuthContext";
import CheckRides from "./Compoments/CheckRides";
import Driver from "./Compoments/Driver";
import IsAuth from "./Resources/IsAuth";

function App() {
  return (
    <div>
      <AuthPro>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Login />} />
            <Route
              path="serchRide"
              element={
                <IsAuth>
                  <CheckRides />
                </IsAuth>
              }
            />
            <Route
              path="driver"
              element={
                <IsAuth>
                  <Driver />
                </IsAuth>
              }
            />
          </Routes>
        </BrowserRouter>
      </AuthPro>
    </div>
  );
}

export default App;
