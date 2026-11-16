import { Link } from "react-router-dom";
import useProperties from "../hooks/useProperties";
import PropertyCard from "../components/PropertyCard";

export default function Home() {
  const { properties, loading } = useProperties();

  return (
    <>
      <div className="hero simple">
        <h1>Find Your Dream Home</h1>
        <p>Browse houses, flats and villas for sale and rent.</p>
        <Link className="btn" to="/listings">Browse Listings</Link>
      </div>

      <main className="container">
        <h2>Featured Properties</h2>
        {loading ? (
          <p>Loading...</p>
        ) : (
          <div className="grid">
            {/* LISTS: map() turns an array into components; key must be unique */}
            {properties.slice(0, 3).map(p => (
              <PropertyCard key={p.id} property={p} />
            ))}
          </div>
        )}
      </main>
    </>
  );
}
