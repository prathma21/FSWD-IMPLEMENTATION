import { useState } from "react";

export default function Contact() {
  // one state object for the whole form
  const [form, setForm] = useState({ name: "", email: "", phone: "", message: "" });
  const [sent, setSent] = useState(false);

  // one handler for all inputs: uses the input's name attribute
  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  function handleSubmit(e) {
    e.preventDefault();             // stop the page from reloading
    console.log("Form data:", form); // Unit 4: send this to Spring Boot
    setSent(true);
    setForm({ name: "", email: "", phone: "", message: "" });
  }

  return (
    <main className="container">
      <h2>Contact Us</h2>
      <form onSubmit={handleSubmit}>
        <label htmlFor="name">Name</label>
        <input id="name" name="name" value={form.name} onChange={handleChange} required />

        <label htmlFor="email">Email</label>
        <input id="email" name="email" type="email" value={form.email} onChange={handleChange} required />

        <label htmlFor="phone">Phone</label>
        <input id="phone" name="phone" type="tel" value={form.phone} onChange={handleChange} />

        <label htmlFor="message">Message</label>
        <textarea id="message" name="message" rows="4" value={form.message} onChange={handleChange} required />

        <button className="btn" type="submit">Send Message</button>
        {sent && <p className="message">Thank you! We will contact you soon.</p>}
      </form>
    </main>
  );
}
