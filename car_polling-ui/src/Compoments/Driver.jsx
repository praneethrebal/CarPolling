import React, { useState } from "react";
import "../Styles/Driver.css";
import BaseUrl from "../Resources/BaseUrl";
import { useAuth } from "../Resources/AuthContext";
import { HttpStatusCode } from "axios";

const Driver = () => {
  const { token } = useAuth();
  const [formData, setForData] = useState({
    startPoint: "",
    endPoint: "",
    startTime: "",
    driverName: "",
    driverPhone: "",
    totalAmount: "",
  });
  const [ride, setRide] = useState([]);
  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === "driverPhone") {
      const cleanedValue = value.replace(/[^0-9]/g, "");
      setForData({ ...formData, [name]: cleanedValue });
    } else {
      setForData({ ...formData, [name]: value });
    }
  };
  const findAllRides = () => {
    BaseUrl.get(`/driver/findAllrides`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        console.log(res.data);
        setRide(res.data);
      })
      .catch((err) => {
        if (err.response.status === 404) {
          alert("No Rides Found");
        }
        console.log(err);
      });
  };
  const conform_ride = (id) => {
    BaseUrl.put(
      `driver/comform-ride/${id}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
      .then((res) => {
        console.log(res.data);
        setRide([]);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  const reject_ride = (id) => {
    BaseUrl.put(
      `driver/reject-ride/${id}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
      .then((res) => {
        console.log(res.data);
        setRide(ride.filter((r) => r.id !== id));
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const hanbleSubmit = (e) => {
    e.preventDefault();
    BaseUrl.post(
      `/driver/create`,
      {
        startPoint: formData.startPoint,
        endPoint: formData.endPoint,
        startTime: formData.startTime,
        driverName: formData.driverName,
        driverPhoneNo: formData.driverPhone,
        totalAmountt: formData.totalAmount,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
      .then((res) => {
        console.log(res.data);
      })
      .catch((err) => {
        if (err.status === 409) {
          alert(err.response.data.errorResponse);
        } else console.log(err);
      });
  };
  return (
    <div className="Driver-Main">
      <div className="left">
        <div className="Ride-Post">
          <div className="Details">
            <h1>Post a new Ride</h1>
            <form onSubmit={hanbleSubmit}>
              <div className="driver-formGroup">
                <label>Start Point:</label>
                <input
                  type="text"
                  name="startPoint"
                  required
                  placeholder="Start Point"
                  onChange={handleChange}
                />
              </div>
              <div className="driver-formGroup">
                <label>End Point:</label>
                <input
                  type="text"
                  name="endPoint"
                  onChange={handleChange}
                  required
                  placeholder="End Point"
                />
              </div>
              <div className="driver-formGroup">
                <label>Start Time:</label>
                <input
                  type="datetime-local"
                  name="startTime"
                  onChange={handleChange}
                  required
                  placeholder="End Point"
                />
              </div>
              <div className="driver-formGroup">
                <label>Driver Name:</label>
                <input
                  type="text"
                  name="driverName"
                  onChange={handleChange}
                  required
                  placeholder="Driver Name"
                />
              </div>
              <div className="driver-formGroup">
                <label>Driver PhoneNo:</label>
                <input
                  type="tel"
                  name="driverPhone"
                  onChange={handleChange}
                  required
                  pattern="[0-9]{10}"
                  placeholder="Enter phone number"
                />
                <div className="driver-formGroup">
                  <label>Total Amount:</label>
                  <input
                    type="number"
                    name="totalAmount"
                    onChange={handleChange}
                    required
                    placeholder="Amount"
                  />
                </div>
                <div className="postbtn">
                  <button type="submit">Post Ride</button>
                </div>
              </div>
            </form>
          </div>
        </div>
        <div className="Avaliable-Rides"></div>
      </div>
      <div className="right">
        <div className="findAll">
          <div>
            <button onClick={findAllRides}>click To Refresh</button>
          </div>
          <div className="rides">
            <h3 style={{ marginBottom: "10px", color: "#333" }}>
              Ride Details
            </h3>
            {ride.length !== 0 &&
              ride.map((r) => (
                <div key={r.id} className="ride-card">
                  <p>
                    <strong>Driver Name:</strong> {r.driverName}
                  </p>
                  <p>
                    <strong>Passenger Name:</strong> {r.passeangerName}
                  </p>
                  <p>
                    <strong>Start Point:</strong> {r.startPoint}
                  </p>
                  <p>
                    <strong>End Point:</strong> {r.endPoint}
                  </p>
                  <p>
                    <strong>Booking Date:</strong> {r.bookingDate}
                  </p>
                  <p>
                    <strong>Booking Time:</strong> {r.bookingTime}
                  </p>
                  <p>
                    <strong>Driver Phone:</strong> {r.drivePhone_No}
                  </p>
                  <p>
                    <strong>Passenger Phone:</strong> {r.passeangerPhone_NO}
                  </p>
                  <p>
                    <strong>Ride Status:</strong> {r.rideStatus}
                  </p>
                  <p>
                    <strong>Total Amount:</strong> ₹{r.totalAmount}
                  </p>
                  <div className="ride-actions">
                    <button
                      className="confirm-btn"
                      onClick={() => conform_ride(r.id)}
                    >
                      Confirm
                    </button>
                    <button
                      className="cancel-btn"
                      onClick={() => reject_ride(r.id)}
                    >
                      Cancel
                    </button>
                  </div>
                </div>
              ))}
          </div>
        </div>
        <div className="Accpected-Ride"></div>
      </div>
    </div>
  );
};

export default Driver;
