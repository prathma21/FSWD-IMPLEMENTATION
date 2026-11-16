import useProperties from "../hooks/useProperties";
import PropertyCard from "../components/PropertyCard";
import { useFavorites } from "../context/FavoritesContext";

export default function Favorites() {
  const { properties } = useProperties();
  const { favorites } = useFavorites();

  const saved = properties.filter(p => favorites.includes(p.id));

  return (
    <main className="container">
      <h2>Saved Properties</h2>
      {saved.length === 0 ? (
        <p className="empty">You have not saved any property yet.</p>
      ) : (
        <div className="grid">
          {saved.map(p => <PropertyCard key={p.id} property={p} />)}
        </div>
      )}
    </main>
  );
}
