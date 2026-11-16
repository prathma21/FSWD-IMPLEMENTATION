import { Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import Home from "./pages/Home";
import Listings from "./pages/Listings";
import Detail from "./pages/Detail";
import Favorites from "./pages/Favorites";
import Contact from "./pages/Contact";
import Login from "./pages/Login";

export default function App() {
  return (
    <>
      <Navbar />
      {/* ROUTING: each path shows a different page component */}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/listings" element={<Listings />} />
        <Route path="/property/:id" element={<Detail />} />
        <Route path="/favorites" element={<Favorites />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/login" element={<Login />} />
        <Route path="*" element={<main className="container"><h2>404 - Page not found</h2></main>} />
      </Routes>
      <Footer />
    </>
  );
}
