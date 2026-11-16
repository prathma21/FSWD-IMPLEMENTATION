import { useState, useMemo, useRef, useEffect } from "react";
import useProperties from "../hooks/useProperties";
import PropertyCard from "../components/PropertyCard";

export default function Listings() {
  const { properties, loading } = useProperties();

  // STATE: values that change and re-draw the screen
  const [search, setSearch] = useState("");
  const [status, setStatus] = useState("All");
  const [maxPrice, setMaxPrice] = useState("");

  // REF: direct access to the search box so we can focus it
  const searchRef = useRef(null);
  useEffect(() => {
    searchRef.current.focus();
  }, []);

  // MEMO: recalculate the filtered list only when one of these values changes
  const filtered = useMemo(() => {
    return properties.filter(p => {
      const text = search.toLowerCase();
      const textOk = p.title.toLowerCase().includes(text) ||
                     p.location.toLowerCase().includes(text);
      const statusOk = status === "All" || p.status === status;
      const priceOk = maxPrice === "" || p.price <= Number(maxPrice);
      return textOk && statusOk && priceOk;
    });
  }, [properties, search, status, maxPrice]);

  return (
    <main className="container">
      <h2>All Properties</h2>

      {/* CONTROLLED inputs: value comes from state, onChange updates state */}
      <div className="filters">
        <input
          ref={searchRef}
          type="text"
          placeholder="Search by title or city"
          value={search}
          onChange={e => setSearch(e.target.value)}
        />
        <select value={status} onChange={e => setStatus(e.target.value)}>
          <option value="All">All</option>
          <option value="For Sale">For Sale</option>
          <option value="For Rent">For Rent</option>
        </select>
        <input
          type="number"
          placeholder="Max price"
          value={maxPrice}
          onChange={e => setMaxPrice(e.target.value)}
        />
      </div>

      {loading && <p>Loading...</p>}
      {!loading && <p>{filtered.length} properties found</p>}

      <div className="grid">
        {filtered.map(p => <PropertyCard key={p.id} property={p} />)}
      </div>
    </main>
  );
}
