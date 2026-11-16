# HomeFinder - Tutorial 1 (HTML + CSS)

Open `index.html` in a browser. No setup needed (internet needed for sample images/video).

## Folder structure
- index.html, listings.html, detail.html, contact.html, login.html
- css/style.css      (all styling, with numbered sections)
- js/hero-canvas.js  (small canvas animation)

## Where each syllabus topic is used
| Topic | File |
|---|---|
| Document structure, semantic tags | all .html files |
| Lists, tables | listings.html, detail.html |
| Internal / external links | nav bar, index.html, detail.html |
| Images, video | cards, detail.html |
| Colors, fonts, backgrounds, borders | style.css sections 1, 4, 6 |
| Margin, padding, box model | style.css (box-sizing, .card-body, .container) |
| Float | .about img in index.html |
| Positioning | sticky header, .badge on cards |
| Flexbox / Grid | .nav, .grid |
| Transform / transition | .btn:hover, .card:hover |
| Animation (CSS + Canvas) | @keyframes fadeIn, hero-canvas.js |

## Validate your code
Paste each page into https://validator.w3.org/ (Unit topic: validating & debugging).

## Ready for the next units
- Unit 2: header -> <Navbar/>, .card -> <PropertyCard/>, forms -> controlled components
- Unit 3: columns: id, title, price, location, bedrooms, bathrooms, area, type, status, image_url
- Unit 4: REST API returning these same fields as JSON
