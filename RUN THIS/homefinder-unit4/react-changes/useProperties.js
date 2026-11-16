import { useState, useEffect, useDebugValue } from "react";

// Unit 4 version: data now comes from the Spring Boot API (which reads MySQL).
// Replace react-app/src/hooks/useProperties.js with this file.
// No other React file needs to change.
const API_URL = "http://localhost:8080/api/properties";

export default function useProperties() {
  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch(API_URL)
      .then(res => {
        if (!res.ok) throw new Error("Server error " + res.status);
        return res.json();
      })
      .then(data => {
        setProperties(data);
        setLoading(false);
      })
      .catch(err => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  useDebugValue(loading ? "Loading..." : error ? "Error: " + error : properties.length + " properties");

  return { properties, loading, error };
}
