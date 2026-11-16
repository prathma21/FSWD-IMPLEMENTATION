"use strict"; // best practice: catches silly mistakes

// ---------- 1. Variables and Arrays ----------
// const = value never reassigned, let = value can change
const properties = [
  { id: 1, title: "Modern 3BHK Apartment", price: 8500000, location: "Whitefield, Bengaluru", type: "Sale", bedrooms: 3, img: 1039 },
  { id: 2, title: "Cozy Family House", price: 12000000, location: "Banjara Hills, Hyderabad", type: "Sale", bedrooms: 4, img: 1068 },
  { id: 3, title: "Studio near Metro", price: 18000, location: "Andheri, Mumbai", type: "Rent", bedrooms: 1, img: 1062 },
  { id: 4, title: "2BHK Near IT Park", price: 28000, location: "Hinjewadi, Pune", type: "Rent", bedrooms: 2, img: 1076 }
];

let saved = JSON.parse(localStorage.getItem("saved")) || []; // window.localStorage

// ---------- 2. Getting elements from the DOM ----------
const listEl = document.getElementById("list");
const searchEl = document.getElementById("search");
const typeEl = document.getElementById("type");
const maxPriceEl = document.getElementById("maxPrice");
const countEl = document.getElementById("count");
const savedCountEl = document.getElementById("savedCount");

// ---------- 3. Functions and Strings ----------
function formatPrice(price) {
  return "₹ " + price.toLocaleString("en-IN");
}

// ---------- 4. Conditions: decide which properties match ----------
function matches(p) {
  const text = searchEl.value.trim().toLowerCase();      // string methods
  const maxPrice = Number(maxPriceEl.value);

  const textOk = p.title.toLowerCase().includes(text) || p.location.toLowerCase().includes(text);
  const typeOk = typeEl.value === "All" || p.type === typeEl.value;
  const priceOk = maxPrice === 0 || p.price <= maxPrice;  // 0 means "no limit"

  return textOk && typeOk && priceOk;
}

// ---------- 5. Loops + creating DOM elements ----------
function render() {
  const result = properties.filter(matches);            // array method
  listEl.innerHTML = "";                                 // clear old cards

  if (result.length === 0) {
    listEl.textContent = "No properties found.";
  }

  for (const p of result) {                              // for...of loop
    const isSaved = saved.includes(p.id);
    const card = document.createElement("article");
    card.className = "card";
    card.innerHTML = `
      <span class="badge">For ${p.type}</span>
      <img src="https://picsum.photos/id/${p.img}/600/400" alt="${p.title}">
      <div class="card-body">
        <h3>${p.title}</h3>
        <p class="price">${formatPrice(p.price)}</p>
        <p>${p.location} - ${p.bedrooms} Beds</p>
        <button class="btn" data-action="view" data-id="${p.id}">View</button>
        <button class="fav-btn ${isSaved ? "saved" : ""}" data-action="save" data-id="${p.id}">
          ${isSaved ? "Saved" : "Save"}
        </button>
      </div>`;
    listEl.appendChild(card);
  }

  countEl.textContent = result.length + " properties found";
  savedCountEl.textContent = saved.length;
}

// ---------- 6. Responding to Events ----------
searchEl.addEventListener("input", render);   // runs on every key press
typeEl.addEventListener("change", render);
maxPriceEl.addEventListener("input", render);

// Event delegation: ONE listener for all buttons inside the list
listEl.addEventListener("click", function (event) {
  const button = event.target.closest("button");
  if (!button) return;                         // clicked something else

  const id = Number(button.dataset.id);

  if (button.dataset.action === "save") {
    if (saved.includes(id)) {
      saved = saved.filter(s => s !== id);     // remove
    } else {
      saved.push(id);                          // add
    }
    localStorage.setItem("saved", JSON.stringify(saved));
    render();
  }

  if (button.dataset.action === "view") {
    const p = properties.find(item => item.id === id);
    // window methods: alert / confirm
    if (window.confirm("Open details of " + p.title + "?")) {
      alert(p.title + "\n" + formatPrice(p.price) + "\n" + p.location);
    }
  }
});

// ---------- 7. Start ----------
render();
