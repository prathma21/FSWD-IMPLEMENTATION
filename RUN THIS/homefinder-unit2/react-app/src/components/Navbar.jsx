import { Link, NavLink } from "react-router-dom";
import { useFavorites } from "../context/FavoritesContext";

export default function Navbar() {
  const { favorites } = useFavorites();   // useContext in action

  return (
    <header>
      <nav className="nav">
        <Link className="logo" to="/">HomeFinder</Link>
        <ul>
          {/* NavLink adds class="active" to the current page automatically */}
          <li><NavLink to="/" end>Home</NavLink></li>
          <li><NavLink to="/listings">Listings</NavLink></li>
          <li><NavLink to="/favorites">Favorites ({favorites.length})</NavLink></li>
          <li><NavLink to="/contact">Contact</NavLink></li>
          <li><NavLink to="/login">Login</NavLink></li>
        </ul>
      </nav>
    </header>
  );
}
