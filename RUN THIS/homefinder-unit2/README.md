# HomeFinder - Unit 2 (JavaScript + React)

Two parts:

## Part A - js-basics/  (plain JavaScript)
Open `js-basics/index.html` in a browser. No install needed.

## Part B - react-app/  (React)
Needs Node.js (v18 or newer).

    cd react-app
    npm install
    npm run dev

Open the link shown in the terminal (usually http://localhost:5173).

## Where each syllabus topic is used

### JavaScript (js-basics/script.js)
| Topic | Where |
|---|---|
| Variables, strings, arrays | top of file, formatPrice(), matches() |
| Conditions and loops | matches(), render() with for...of |
| DOM | getElementById, createElement, appendChild, innerHTML |
| Events | addEventListener (input, change, click), event delegation |
| Window | confirm(), alert(), localStorage |
| Best practices | "use strict", const/let, ===, small functions |
| Third-party library | React Router in Part B (installed with npm) |

### React (react-app/src)
| Topic | File |
|---|---|
| JSX, components, props | components/PropertyCard.jsx |
| Events | PropertyCard.jsx (onClick), Contact.jsx (onChange, onSubmit) |
| Forms (controlled) | pages/Contact.jsx, pages/Login.jsx, pages/Listings.jsx |
| Refs (useRef) | Listings.jsx, Login.jsx (auto focus) |
| Styling | index.css, inline style in PropertyCard.jsx |
| Routing | App.jsx, Navbar.jsx, Detail.jsx (useParams) |
| useState | Listings.jsx, Contact.jsx, Login.jsx |
| useEffect | hooks/useProperties.js, Detail.jsx |
| useContext | context/FavoritesContext.jsx, Navbar.jsx |
| useMemo | Listings.jsx |
| Custom hooks | hooks/useProperties.js, hooks/useLocalStorage.js |
| useDebugValue | both custom hooks (visible in React DevTools) |
| Finding and using custom hooks | useFavorites() in FavoritesContext.jsx |
| Rules of hooks | Hooks are only called at the top of components/hooks, never inside if/loops |

## Getting ready for Units 3 and 4
- data/properties.js has the same fields as the MySQL table you will create in Unit 3.
- hooks/useProperties.js is the ONLY file that needs to change to call Spring Boot (see comment inside it).
- Contact.jsx and Login.jsx already collect the data a REST API will receive.
