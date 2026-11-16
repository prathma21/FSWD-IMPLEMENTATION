import { useState, useRef, useEffect } from "react";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("buyer");
  const [message, setMessage] = useState({ text: "", isError: false });

  const emailRef = useRef(null);
  useEffect(() => { emailRef.current.focus(); }, []);   // focus on page load

  function handleSubmit(e) {
    e.preventDefault();
    if (password.length < 6) {
      setMessage({ text: "Password must be at least 6 characters.", isError: true });
      return;
    }
    // Real login (with the database) comes after Spring Boot is ready.
    setMessage({ text: `Welcome ${email} (${role})! (demo only)`, isError: false });
  }

  return (
    <main className="container">
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <label htmlFor="email">Email</label>
        <input id="email" ref={emailRef} type="email" value={email}
               onChange={e => setEmail(e.target.value)} required />

        <label htmlFor="password">Password</label>
        <input id="password" type="password" value={password}
               onChange={e => setPassword(e.target.value)} required />

        <label htmlFor="role">I am a</label>
        <select id="role" value={role} onChange={e => setRole(e.target.value)}>
          <option value="buyer">Buyer / Renter</option>
          <option value="seller">Seller / Agent</option>
        </select>

        <button className="btn" type="submit">Login</button>

        {message.text && (
          <p className={message.isError ? "message error" : "message"}>{message.text}</p>
        )}
      </form>
    </main>
  );
}
