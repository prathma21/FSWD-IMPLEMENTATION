USE homefinder;

-- ============ JOINS ============

-- 1. INNER JOIN: property + the agent who listed it
SELECT p.title, p.price, u.name AS agent
FROM properties p
INNER JOIN users u ON p.agent_id = u.id;

-- 2. INNER JOIN: inquiries with property title
SELECT i.name AS inquirer, i.message, p.title AS property
FROM inquiries i
JOIN properties p ON i.property_id = p.id;

-- 3. LEFT JOIN: ALL properties, even those with no inquiries (count = 0)
SELECT p.title, COUNT(i.id) AS inquiry_count
FROM properties p
LEFT JOIN inquiries i ON i.property_id = p.id
GROUP BY p.id, p.title;

-- 4. Joining three tables: who saved which property
SELECT u.name AS buyer, p.title, p.price
FROM favorites f
JOIN users u      ON f.user_id = u.id
JOIN properties p ON f.property_id = p.id
ORDER BY u.name;

-- 5. Join + GROUP BY: how many listings does each agent have?
SELECT u.name AS agent, COUNT(p.id) AS listings, SUM(p.price) AS total_value
FROM users u
JOIN properties p ON p.agent_id = u.id
GROUP BY u.id, u.name;

-- 6. Most saved property
SELECT p.title, COUNT(*) AS times_saved
FROM favorites f
JOIN properties p ON f.property_id = p.id
GROUP BY p.id, p.title
ORDER BY times_saved DESC;

-- 7. RIGHT JOIN (same as LEFT JOIN with tables swapped)
SELECT p.title, i.message
FROM inquiries i
RIGHT JOIN properties p ON i.property_id = p.id;

-- 8. SELF JOIN idea: pairs of properties in the same type
SELECT a.title AS property_1, b.title AS property_2, a.type
FROM properties a
JOIN properties b ON a.type = b.type AND a.id < b.id;
