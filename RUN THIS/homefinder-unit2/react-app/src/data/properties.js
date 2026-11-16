// Fake data. Field names match the MySQL table we will create in Unit 3
// and the JSON our Spring Boot API will return in Unit 4.
export const properties = [
  { id: 1, title: "Modern 3BHK Apartment", price: 8500000, location: "Whitefield, Bengaluru", bedrooms: 3, bathrooms: 2, area: 1450, type: "Apartment", status: "For Sale", imageUrl: "https://picsum.photos/id/1039/600/400", description: "Bright apartment on the 8th floor with balcony and modular kitchen." },
  { id: 2, title: "Cozy Family House", price: 12000000, location: "Banjara Hills, Hyderabad", bedrooms: 4, bathrooms: 3, area: 2200, type: "House", status: "For Sale", imageUrl: "https://picsum.photos/id/1068/600/400", description: "Independent house with garden and two-car parking." },
  { id: 3, title: "Studio near Metro", price: 18000, location: "Andheri, Mumbai", bedrooms: 1, bathrooms: 1, area: 520, type: "Apartment", status: "For Rent", imageUrl: "https://picsum.photos/id/1062/600/400", description: "Compact furnished studio, 5 minutes from the metro station." },
  { id: 4, title: "Luxury Villa with Pool", price: 35000000, location: "Alibaug, Maharashtra", bedrooms: 5, bathrooms: 4, area: 4200, type: "Villa", status: "For Sale", imageUrl: "https://picsum.photos/id/1029/600/400", description: "Weekend villa with private pool and sea-facing terrace." },
  { id: 5, title: "2BHK Near IT Park", price: 28000, location: "Hinjewadi, Pune", bedrooms: 2, bathrooms: 2, area: 980, type: "Apartment", status: "For Rent", imageUrl: "https://picsum.photos/id/1076/600/400", description: "Semi-furnished flat close to offices and food streets." },
  { id: 6, title: "Budget Starter Flat", price: 4200000, location: "Sector 62, Noida", bedrooms: 2, bathrooms: 1, area: 750, type: "Apartment", status: "For Sale", imageUrl: "https://picsum.photos/id/1055/600/400", description: "Affordable flat ideal for first-time buyers." }
];

export function formatPrice(price) {
  return "₹ " + price.toLocaleString("en-IN");
}
