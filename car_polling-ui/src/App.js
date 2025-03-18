import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import Login from "./Compoments/Login";
import { Authpro } from "./Compoments/Auth/AuthContext";
import Passeanger from "./Compoments/Passeanger";
import Driver from "./Compoments/Driver";
import IsAuth from "./Compoments/Auth/IsAuth";

function App() {
  return (
    <div>
      <Authpro>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Login />} />

            <Route
              path="/passeange"
              element={
                <IsAuth>
                  <Passeanger />
                </IsAuth>
              }
            />

            <Route
              path="/driver"
              element={
                <IsAuth>
                  <Driver />
                </IsAuth>
              }
            />
          </Routes>
        </BrowserRouter>
      </Authpro>
    </div>
  );
}

export default App;
