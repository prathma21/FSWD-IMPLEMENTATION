USE homefinder;

-- users (ids 1-2 agents, 3-5 buyers)
INSERT INTO users (name, email, password, role) VALUES
 ('Ravi Kumar',  'ravi@homefinder.com',  'demo123', 'agent'),
 ('Sneha Rao',   'sneha@homefinder.com', 'demo123', 'agent'),
 ('Amit Shah',   'amit@gmail.com',       'demo123', 'buyer'),
 ('Priya Nair',  'priya@gmail.com',      'demo123', 'buyer'),
 ('Karan Mehta', 'karan@gmail.com',      'demo123', 'buyer');

-- properties (same 6 as the React app)
INSERT INTO properties (title, price, location, bedrooms, bathrooms, area, type, status, image_url, description, agent_id) VALUES
 ('Modern 3BHK Apartment',  8500000,  'Whitefield, Bengaluru',   3, 2, 1450, 'Apartment', 'For Sale', 'https://picsum.photos/id/1039/600/400', 'Bright apartment on the 8th floor with balcony and modular kitchen.', 1),
 ('Cozy Family House',      12000000, 'Banjara Hills, Hyderabad',4, 3, 2200, 'House',     'For Sale', 'https://picsum.photos/id/1068/600/400', 'Independent house with garden and two-car parking.', 1),
 ('Studio near Metro',      18000,    'Andheri, Mumbai',         1, 1, 520,  'Apartment', 'For Rent', 'https://picsum.photos/id/1062/600/400', 'Compact furnished studio, 5 minutes from the metro station.', 2),
 ('Luxury Villa with Pool', 35000000, 'Alibaug, Maharashtra',    5, 4, 4200, 'Villa',     'For Sale', 'https://picsum.photos/id/1029/600/400', 'Weekend villa with private pool and sea-facing terrace.', 2),
 ('2BHK Near IT Park',      28000,    'Hinjewadi, Pune',         2, 2, 980,  'Apartment', 'For Rent', 'https://picsum.photos/id/1076/600/400', 'Semi-furnished flat close to offices and food streets.', 2),
 ('Budget Starter Flat',    4200000,  'Sector 62, Noida',        2, 1, 750,  'Apartment', 'For Sale', 'https://picsum.photos/id/1055/600/400', 'Affordable flat ideal for first-time buyers.', 1);

-- inquiries (property 4 and 6 get none, so LEFT JOIN / EXCEPT demos are interesting)
INSERT INTO inquiries (property_id, name, email, phone, message) VALUES
 (1, 'Amit Shah',   'amit@gmail.com',  '9876500001', 'Is the price negotiable?'),
 (1, 'Priya Nair',  'priya@gmail.com', '9876500002', 'Can I visit this Sunday?'),
 (2, 'Karan Mehta', 'karan@gmail.com', '9876500003', 'Is the garden included in the area?'),
 (3, 'Amit Shah',   'amit@gmail.com',  '9876500001', 'Is the studio pet friendly?'),
 (5, 'Priya Nair',  'priya@gmail.com', '9876500002', 'Is a one-year lease possible?');

-- favorites (property 6 is saved by nobody)
INSERT INTO favorites (user_id, property_id) VALUES
 (3, 1), (3, 2), (3, 4),
 (4, 1), (4, 5),
 (5, 2), (5, 3);
