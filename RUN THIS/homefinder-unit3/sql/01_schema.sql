-- =====================================================
-- HomeFinder database  |  Unit 3: SQL
-- Run this file first.
-- =====================================================
DROP DATABASE IF EXISTS homefinder;
CREATE DATABASE homefinder;
USE homefinder;

-- ---------- users: buyers and agents ----------
CREATE TABLE users (
  id        INT AUTO_INCREMENT PRIMARY KEY,
  name      VARCHAR(100) NOT NULL,
  email     VARCHAR(100) NOT NULL UNIQUE,
  password  VARCHAR(100) NOT NULL,          -- plain text only for learning, never do this in a real app
  role      VARCHAR(10)  NOT NULL DEFAULT 'buyer'   -- 'buyer' or 'agent'
);

-- ---------- properties: same fields as properties.js in the React app ----------
CREATE TABLE properties (
  id          INT AUTO_INCREMENT PRIMARY KEY,
  title       VARCHAR(150)  NOT NULL,
  price       DECIMAL(12,2) NOT NULL,
  location    VARCHAR(150)  NOT NULL,
  bedrooms    INT           NOT NULL,
  bathrooms   INT           NOT NULL,
  area        INT           NOT NULL,        -- square feet
  type        VARCHAR(20)   NOT NULL,        -- Apartment / House / Villa
  status      VARCHAR(20)   NOT NULL,        -- 'For Sale' or 'For Rent'
  image_url   VARCHAR(255),
  description TEXT,
  agent_id    INT,                           -- which agent listed it
  FOREIGN KEY (agent_id) REFERENCES users(id)
);

-- ---------- inquiries: messages from the Contact form ----------
CREATE TABLE inquiries (
  id          INT AUTO_INCREMENT PRIMARY KEY,
  property_id INT,
  name        VARCHAR(100) NOT NULL,
  email       VARCHAR(100) NOT NULL,
  phone       VARCHAR(20),
  message     TEXT NOT NULL,
  created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (property_id) REFERENCES properties(id)
);

-- ---------- favorites: the "Save" button in the React app ----------
CREATE TABLE favorites (
  user_id     INT NOT NULL,
  property_id INT NOT NULL,
  PRIMARY KEY (user_id, property_id),        -- same property cannot be saved twice by one user
  FOREIGN KEY (user_id)     REFERENCES users(id),
  FOREIGN KEY (property_id) REFERENCES properties(id)
);
