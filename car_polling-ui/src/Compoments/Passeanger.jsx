import React, { useState } from "react";
import { useAuth } from "./Auth/AuthContext";
import BaseUrl from "./URL's/BaseUrl";
import { useNavigate } from "react-router-dom";
import "../Compoments/Styles/Passeanger.css";

const Passeanger = () => {
  const { token } = useAuth();
  const [startPoint, setStartPoint] = useState("");
  const [endPoint, setEndPoint] = useState("");
  const [rides, setRides] = useState([]);
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();

    BaseUrl.post(
      `passeanger/search`,
      { startPoint, endPoint },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
      .then((res) => {
        console.log(res.data);
        setRides(res.data);
      })
      .catch((err) => {
        if (err.response.status === 500) {
          navigate("/");
        }

        console.log(err);
      });
  };

  const book = (id) => {
    BaseUrl.post(
      `passeanger/book/${id}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
      .then((res) => console.log(res.data))
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <div className="container">
      <div className="form-container">
        <h1>Passeanger</h1>

        <form onSubmit={handleSubmit}>
          <label>StartPoint:</label>
          <input
            type="text"
            placeholder="Enter Start Point"
            onChange={(e) => setStartPoint(e.target.value)}
            required
          />
          <br />
          <label>EndPoint:</label>
          <input
            type="text"
            placeholder="Enter Start Point"
            onChange={(e) => setEndPoint(e.target.value)}
            required
          />
          <br />
          <button type="submit">Serach</button>
        </form>
      </div>
      <div className="rides">
        {rides.length > 0 ? (
          rides.map((ride) => (
            <div key={ride.id} className="rides-avaliable">
              <div className="ride-card">
                <div className="ride-info">
                  <strong>Driver Name:</strong> <span>{ride.driverName}</span>
                </div>
                <div className="ride-info">
                  <strong>Driver PhoneNo:</strong>{" "}
                  <span>{ride.driverPhoneNo}</span>
                </div>
                <div className="ride-info">
                  <strong>StartPoint:</strong> <span>{ride.startPoint}</span>
                </div>
                <div className="ride-info">
                  <strong>EndPoint:</strong> <span>{ride.endPoint}</span>
                </div>
                <div className="ride-info">
                  <strong>StartTime:</strong> <span>{ride.startTime}</span>
                </div>
                <div className="ride-info">
                  <strong>Total Amount:</strong>{" "}
                  <span>{ride.totalAmountt}</span>
                </div>
                <button className="book-button" onClick={() => book(ride.id)}>
                  Book
                </button>
              </div>
            </div>
          ))
        ) : (
          <p>No rides found.</p>
        )}
      </div>
    </div>
  );
};

export default Passeanger;
