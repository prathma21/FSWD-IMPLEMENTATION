import { useState, useEffect, useDebugValue } from "react";
import { properties as localData } from "../data/properties";

// CUSTOM HOOK: gives any component the list of properties + a loading flag.
// Right now it uses local data. In Unit 4 only the inside of useEffect changes:
//
//   fetch("http://localhost:8080/api/properties")
//     .then(res => res.json())
//     .then(data => { setProperties(data); setLoading(false); });
//
// No component needs to change.
export default function useProperties() {
  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {   // pretend the server takes 0.5 sec
      setProperties(localData);
      setLoading(false);
    }, 500);
    return () => clearTimeout(timer);  // cleanup when component disappears
  }, []);                              // [] = run only once

  useDebugValue(loading ? "Loading..." : properties.length + " properties");

  return { properties, loading };
}
