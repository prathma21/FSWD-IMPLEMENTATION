import { Link } from "react-router-dom";
import { formatPrice } from "../data/properties";
import { useFavorites } from "../context/FavoritesContext";

// COMPONENT with PROPS: receives one property and draws one card.
export default function PropertyCard({ property }) {
  const { favorites, toggleFavorite } = useFavorites();
  const isSaved = favorites.includes(property.id);

  // inline style (one of the ways to style in React)
  const badgeStyle = {
    background: property.status === "For Rent" ? "#1e6091" : "#e07a1f"
  };

  return (
    <article className="card">
      <span className="badge" style={badgeStyle}>{property.status}</span>
      <img src={property.imageUrl} alt={property.title} />
      <div className="card-body">
        <h3>{property.title}</h3>
        <p className="price">{formatPrice(property.price)}</p>
        <p>{property.location}</p>
        <ul className="meta">
          <li>{property.bedrooms} Beds</li>
          <li>{property.bathrooms} Baths</li>
          <li>{property.area} sq ft</li>
        </ul>
        <Link className="btn" to={`/property/${property.id}`}>View Details</Link>
        {/* EVENT: onClick */}
        <button
          className={isSaved ? "fav-btn saved" : "fav-btn"}
          onClick={() => toggleFavorite(property.id)}
        >
          {isSaved ? "Saved" : "Save"}
        </button>
      </div>
    </article>
  );
}
