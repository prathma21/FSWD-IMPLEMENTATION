USE homefinder;

-- ============ SET QUERIES ============
-- Rules: same number of columns, compatible data types.
-- INTERSECT and EXCEPT need MySQL 8.0.31 or newer.

-- UNION: combine two result sets (removes duplicates; UNION ALL keeps them)
SELECT title, 'cheap' AS tag FROM properties WHERE price < 50000
UNION
SELECT title, 'big'   AS tag FROM properties WHERE area > 2000;

-- INTERSECT: rows present in BOTH queries
-- Properties that were saved AND received an inquiry
SELECT property_id FROM favorites
INTERSECT
SELECT property_id FROM inquiries;

-- EXCEPT: rows in the first query but NOT in the second
-- Properties that nobody has saved
SELECT id FROM properties
EXCEPT
SELECT property_id FROM favorites;

-- ============ SUBQUERIES ============

-- 1. Subquery in WHERE (returns one value)
SELECT title, price FROM properties
WHERE price > (SELECT AVG(price) FROM properties);

-- 2. Subquery with IN (returns a list)
SELECT title FROM properties
WHERE id IN (SELECT property_id FROM inquiries);

-- 3. NOT IN: properties with no inquiries
SELECT title FROM properties
WHERE id NOT IN (SELECT property_id FROM inquiries);

-- 4. EXISTS: buyers who have saved at least one property
SELECT name FROM users u
WHERE EXISTS (SELECT 1 FROM favorites f WHERE f.user_id = u.id);

-- 5. Subquery in SELECT: inquiry count beside each property
SELECT p.title,
       (SELECT COUNT(*) FROM inquiries i WHERE i.property_id = p.id) AS inquiries
FROM properties p;

-- 6. Subquery in FROM (derived table): average price per type, then pick the highest
SELECT type, avg_price
FROM (SELECT type, AVG(price) AS avg_price FROM properties GROUP BY type) t
ORDER BY avg_price DESC
LIMIT 1;

-- 7. Highest priced property of each status
SELECT title, status, price FROM properties p
WHERE price = (SELECT MAX(price) FROM properties WHERE status = p.status);
