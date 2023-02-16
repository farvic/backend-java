CREATE TABLE IF NOT EXISTS cinema (
    id INT AUTO_INCREMENT PRIMARY KEY,
    total_rows INT NOT NULL,
    total_columns INT NOT NULL
);

CREATE table IF NOT EXISTS orders (
    seat_id INT UNIQUE NOT NULL,
    token VARCHAR(36) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS seats (
    seat_id INT AUTO_INCREMENT PRIMARY KEY,
    seat_row INT NOT NULL,
    seat_column INT NOT NULL,
    price INT NOT NULL,
    available boolean NOT NULL,
    cinema_id INT NOT NULL
);


ALTER TABLE orders
    ADD CONSTRAINT IF NOT EXISTS fk_seat_id FOREIGN KEY (seat_id) REFERENCES seats(seat_id);

ALTER TABLE seats
    ADD CONSTRAINT IF NOT EXISTS fk_cinema_id FOREIGN KEY (cinema_id) REFERENCES cinema(id);