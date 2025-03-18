import axios from "axios";

const BaseUrl = axios.create({
  baseURL: "http://localhost:8080",
});

export default BaseUrl;
