import React, { useEffect, useState } from "react";
import "../Styles/CheckRides.css";
import BaseUrl from "../Resources/BaseUrl";
import { useAuth } from "../Resources/AuthContext";

const CheckRides = () => {
  const [start, setStart] = useState();
  const [end, setEnd] = useState();
  const { token } = useAuth();
  // const [ridesAvaliable, setRidesAvaliable] = useState(false);
  const [rides, setRides] = useState([]);
  const [bookings, setBookings] = useState([]);

  const handleSubmit = (e) => {
    e.preventDefault();
    BaseUrl.post(
      "/passeanger/search",
      {
        startPoint: start,
        endPoint: end,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
      .then((res) => {
        setRides(res.data);
      })
      .catch((err) => {
        if (err.status === 404) alert("No rides Found");
        else console.log(err);
      });
  };

  const rideBooking = (id) => {
    const conform = window.confirm("Do you Want to Book This ride");
    if (conform) {
      BaseUrl.post(
        `/passeanger/book/${id}`,
        {},
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      )
        .then((res) => {
          alert("Ride is Booked Sucessfully");
          console.log("ride Booked", res.data);
          fetchBookings();
          // setRidesAvaliable(true);
        })
        .catch((err) => {
          setRides([]);
          if (err.response?.status === 404) {
            alert("No rides Avaliable in this route");
          }
        });
    } else {
      alert("Ride is not Booked");
    }
  };

  const fetchBookings = () => {
    BaseUrl.get(
      `passeanger/my-bookings`,

      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
      .then((res) => {
        console.log("data from bookings", res.data);
        setBookings(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  useEffect(() => {
    fetchBookings();
  }, [token]);

  return (
    <div className="search-main">
      <div className="search-container">
        <div className="search-form">
          <span>Search a Ride</span>
          <form onSubmit={handleSubmit}>
            <div className="search-form-group">
              <label>Start Point: </label>
              <input
                type="text"
                placeholder="Enter start location"
                onChange={(e) => {
                  setStart(e.target.value);
                }}
                required
              />
            </div>
            <div className="search-form-group">
              <label>End Point: </label>
              <input
                type="text"
                placeholder="Enter start location"
                onChange={(e) => {
                  setEnd(e.target.value);
                }}
                required
              />
            </div>
            <div className="search-btn">
              <button type="submit">Search</button>
            </div>
          </form>
        </div>
        {rides.length !== 0 && (
          <div className="avaliable-rides">
            <span className="search-show-rides">
              Available Rides will be displayed here
            </span>
            {rides.map((ride) => (
              <div key={ride.id} className="all-rides">
                <p>Driver Name : {ride.driverName}</p>
                <p>Driver PhoneNo : {ride.driverPhoneNo}</p>
                <br></br>
                <p>Start Point : {ride.startPoint}</p>
                <p>End Point : {ride.endPoint}</p>
                <br></br>
                <p>Total amount : {ride.totalAmountt}</p>
                <div className="ride-book-btn">
                  <button
                    className="book-btn"
                    onClick={() => {
                      rideBooking(ride.id);
                    }}
                  >
                    Book
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
      <SearchHistory bookings={bookings} />
    </div>
  );
};

export const SearchHistory = ({ bookings }) => {
  const [history, setHistory] = useState([]);

  const { token } = useAuth();

  useEffect(() => {
    BaseUrl.get(`passeanger/history`, {
      headers: { Authorization: `Bearer ${token}` },
    })
      .then((res) => {
        console.log("API Response:", res.data);
        setHistory(res.data);
      })
      .catch((err) => {
        console.error("API Error:", err);
        setHistory([]); // Ensure history is always an array
      });
  }, [token]);

  return (
    <>
      <div className="search-history">
        <div className="my-bookings">
          <span>My Bookings</span>
          <div className="bookings">
            {bookings.length === 0 && <h4>User Does not Book any Rides</h4>}
            {bookings.length !== 0 &&
              bookings.map((book) => (
                <div key={book.id} className="book-map">
                  <p>
                    From:{book.startPoint} <span></span>
                  </p>
                  <p>
                    To: {book.endPoint}
                    <span></span>
                  </p>
                  <p>
                    Driver Name:{book.driverName} <span></span>
                  </p>
                  <p>
                    Amount:{book.bookingDate} <span></span>
                  </p>
                  <p>
                    Date:{book.bookingTime} <span></span>
                  </p>
                  <p>
                    Time:{book.totalAmount}
                    <span></span>
                  </p>
                </div>
              ))}
          </div>
        </div>
        <div className="my-history">
          <span>my History</span>
          <div className="history">
            {history.length === 0 && <h4>User Does not Book any Rides</h4>}
            {history.length !== 0 &&
              history.map((his, index) => (
                <div key={his.id || index} className="book-map">
                  <p>
                    From:{his.start_point} <span></span>
                  </p>
                  <p>
                    To: {his.end_Point}
                    <span></span>
                  </p>
                  <p>
                    Driver Name:{his.driverName} <span></span>
                  </p>
                  <p>
                    Amount:{his.start_point} <span></span>
                  </p>
                  <p>
                    Date:{his.date} <span></span>
                  </p>
                  <p>
                    Time:{his.time}
                    <span></span>
                  </p>
                </div>
              ))}
          </div>
        </div>
      </div>
    </>
  );
};

export default CheckRides;
