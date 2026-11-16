USE homefinder;

-- ============ MODIFYING DATA ============

-- INSERT one row
INSERT INTO properties (title, price, location, bedrooms, bathrooms, area, type, status, image_url, description, agent_id)
VALUES ('Garden Duplex', 9500000, 'Kothrud, Pune', 3, 3, 1800, 'House', 'For Sale',
        'https://picsum.photos/id/1040/600/400', 'Duplex with private garden.', 2);

-- INSERT many rows
INSERT INTO users (name, email, password, role) VALUES
 ('Test One', 'test1@gmail.com', 'demo123', 'buyer'),
 ('Test Two', 'test2@gmail.com', 'demo123', 'buyer');

-- UPDATE (always use WHERE, otherwise ALL rows change!)
UPDATE properties SET price = 9000000 WHERE title = 'Garden Duplex';
UPDATE properties SET price = price * 1.05 WHERE status = 'For Rent';   -- 5% rent increase

-- DELETE (always use WHERE!)
DELETE FROM users WHERE email IN ('test1@gmail.com', 'test2@gmail.com');
DELETE FROM properties WHERE title = 'Garden Duplex';

-- Check
SELECT id, title, price FROM properties;

-- ============ CREATING, ALTERING, DELETING TABLES ============

-- CREATE a table
CREATE TABLE reviews (
  id          INT AUTO_INCREMENT PRIMARY KEY,
  property_id INT NOT NULL,
  rating      INT NOT NULL,
  comment     VARCHAR(255),
  FOREIGN KEY (property_id) REFERENCES properties(id)
);

-- ALTER: add, change and remove a column
ALTER TABLE reviews ADD COLUMN reviewer_name VARCHAR(100);
ALTER TABLE reviews MODIFY COLUMN comment VARCHAR(500);
ALTER TABLE reviews DROP COLUMN reviewer_name;

-- Use it
INSERT INTO reviews (property_id, rating, comment) VALUES (1, 5, 'Great location');
SELECT * FROM reviews;

-- DELETE all rows but keep the table
DELETE FROM reviews;

-- DROP: remove the table completely
DROP TABLE reviews;

-- ALTER on a real table: add a column we may need later
-- ALTER TABLE properties ADD COLUMN featured BOOLEAN DEFAULT FALSE;
