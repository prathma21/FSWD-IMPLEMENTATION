import { useState, useEffect, useDebugValue } from "react";

// CUSTOM HOOK: works like useState but also saves the value in localStorage.
// Rule: a custom hook name must start with "use".
export default function useLocalStorage(key, initialValue) {
  const [value, setValue] = useState(() => {
    const saved = localStorage.getItem(key);
    return saved ? JSON.parse(saved) : initialValue;
  });

  useEffect(() => {
    localStorage.setItem(key, JSON.stringify(value));
  }, [key, value]);

  // useDebugValue: shows a label in React DevTools next to this hook
  useDebugValue(`${key}: ${JSON.stringify(value)}`);

  return [value, setValue];
}
