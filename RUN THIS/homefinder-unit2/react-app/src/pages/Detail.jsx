import { useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import useProperties from "../hooks/useProperties";
import { formatPrice } from "../data/properties";
import { useFavorites } from "../context/FavoritesContext";

export default function Detail() {
  const { id } = useParams();                  // reads :id from the URL
  const { properties, loading } = useProperties();
  const { favorites, toggleFavorite } = useFavorites();

  const property = properties.find(p => p.id === Number(id));

  // EFFECT: change the browser tab title when the property is known
  useEffect(() => {
    if (property) document.title = property.title + " | HomeFinder";
    return () => { document.title = "HomeFinder"; };
  }, [property]);

  if (loading) return <main className="container"><p>Loading...</p></main>;
  if (!property) return <main className="container"><p>Property not found.</p></main>;

  const isSaved = favorites.includes(property.id);

  return (
    <main className="container">
      <p><Link to="/listings">&larr; Back to listings</Link></p>

      <img className="detail-img" src={property.imageUrl} alt={property.title} />
      <h1>{property.title}</h1>
      <p className="price">{formatPrice(property.price)}</p>
      <p>{property.location}</p>
      <p>{property.description}</p>

      <table>
        <tbody>
          <tr><th>Bedrooms</th><td>{property.bedrooms}</td></tr>
          <tr><th>Bathrooms</th><td>{property.bathrooms}</td></tr>
          <tr><th>Area</th><td>{property.area} sq ft</td></tr>
          <tr><th>Type</th><td>{property.type}</td></tr>
          <tr><th>Status</th><td>{property.status}</td></tr>
        </tbody>
      </table>
      <br />
      <Link className="btn" to="/contact">Contact Agent</Link>
      <button className={isSaved ? "fav-btn saved" : "fav-btn"} onClick={() => toggleFavorite(property.id)}>
        {isSaved ? "Saved" : "Save"}
      </button>
    </main>
  );
}
