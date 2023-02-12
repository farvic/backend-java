CREATE TABLE cinema (
    id INT AUTO_INCREMENT PRIMARY KEY,
    total_rows INT NOT NULL,
    total_columns INT NOT NULL
);

CREATE table orders (
    seat_id int UNIQUE NOT NULL,
    token VARCHAR(36) PRIMARY KEY
);

CREATE TABLE seats (
    seat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    seat_row INT NOT NULL,
    seat_column INT NOT NULL,
    price INT NOT NULL,
    available boolean NOT NULL,
    cinema_id int NOT NULL -- room_id INT NOT NULL -- FOREIGN KEY (room_id) REFERENCES Room(id)
);


ALTER TABLE orders
    ADD CONSTRAINT fk_seat_id FOREIGN KEY (seat_id) REFERENCES seats(seat_id);

ALTER TABLE seats
    ADD CONSTRAINT fk_cinema_id FOREIGN KEY (cinema_id) REFERENCES cinema(id);