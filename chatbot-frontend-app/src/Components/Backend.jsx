// fetchApiKey.js
import axios from "axios";

export const fetchApiKey = async () => {
  try {
    const response = await axios.get("http://localhost:8181/api/config/apikey");
    console.log(response.data);
    return response.data; // Adjust based on your backend response
  } catch (error) {
    console.error("Error fetching API key:", error);
    return null;
  }
};
