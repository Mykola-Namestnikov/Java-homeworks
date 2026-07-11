INSERT INTO client (name) VALUES
('Elon Musk'), ('Neil Armstrong'), ('Buzz Aldrin'),('Yuri Gagarin'), ('John Doe'),
('Jane Smith'), ('Peter Parker'), ('Clark Kent'), ('Bruce Wayne'), ('Tony Stark');

INSERT INTO planet (id, name) VALUES
('MARS', 'Mars'),
('VEN', 'Venus'),
('EARTH', 'Earth'),
('JUP', 'Jupiter'),
('SAT', 'Saturn');

INSERT INTO ticket (client_id, from_planet_id, to_planet_id) VALUES
(1, 'EARTH', 'MARS'),
(2, 'EARTH', 'VEN'),
(3, 'MARS', 'EARTH'),
(4, 'EARTH', 'JUP'),
(5, 'VEN', 'SAT'),
(6, 'SAT', 'MARS'),
(7, 'JUP', 'EARTH'),
(8, 'EARTH', 'SAT'),
(9, 'MARS', 'JUP'),
(10, 'VEN', 'EARTH');