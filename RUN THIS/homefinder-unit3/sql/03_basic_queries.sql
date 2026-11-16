USE homefinder;

-- ============ BASICS: SELECT, WHERE, ORDER BY, LIMIT ============

-- 1. All rows, all columns
SELECT * FROM properties;

-- 2. Only some columns
SELECT title, price, location FROM properties;

-- 3. Filter (WHERE) - same as the React "For Sale" filter
SELECT title, price FROM properties WHERE status = 'For Sale';

-- 4. Several conditions (AND / OR)
SELECT title, price FROM properties
WHERE status = 'For Sale' AND bedrooms >= 3;

-- 5. Range and list
SELECT title, price FROM properties WHERE price BETWEEN 4000000 AND 10000000;
SELECT title, type FROM properties WHERE type IN ('House', 'Villa');

-- 6. Text search (same as the search box)
SELECT title, location FROM properties
WHERE title LIKE '%flat%' OR location LIKE '%Pune%';

-- 7. Sorting and limiting
SELECT title, price FROM properties ORDER BY price DESC;
SELECT title, price FROM properties ORDER BY price ASC LIMIT 3;

-- 8. Aliases and calculated column
SELECT title, ROUND(price / area, 0) AS price_per_sqft FROM properties WHERE status = 'For Sale';

-- 9. Aggregate functions
SELECT COUNT(*)  AS total_properties,
       MIN(price) AS cheapest,
       MAX(price) AS costliest,
       AVG(area)  AS average_area
FROM properties;

-- 10. GROUP BY and HAVING
SELECT status, COUNT(*) AS total FROM properties GROUP BY status;
SELECT type, COUNT(*) AS total, AVG(price) AS avg_price
FROM properties GROUP BY type HAVING COUNT(*) > 1;

-- 11. Remove duplicates
SELECT DISTINCT type FROM properties;
