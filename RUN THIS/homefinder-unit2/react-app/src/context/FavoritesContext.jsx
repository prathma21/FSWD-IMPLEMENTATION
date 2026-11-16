import { createContext, useContext } from "react";
import useLocalStorage from "../hooks/useLocalStorage";

// CONTEXT: share "favorites" with every component without passing props down.
const FavoritesContext = createContext();

export function FavoritesProvider({ children }) {
  const [favorites, setFavorites] = useLocalStorage("favorites", []);

  function toggleFavorite(id) {
    setFavorites(prev =>
      prev.includes(id) ? prev.filter(f => f !== id) : [...prev, id]
    );
  }

  return (
    <FavoritesContext.Provider value={{ favorites, toggleFavorite }}>
      {children}
    </FavoritesContext.Provider>
  );
}

// small custom hook so components write useFavorites() instead of useContext(...)
export function useFavorites() {
  return useContext(FavoritesContext);
}
