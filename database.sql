-- Tạo bảng flights
CREATE TABLE IF NOT EXISTS flights (
  flight_id VARCHAR(255) PRIMARY KEY,
  airline VARCHAR(255) NOT NULL,
  departure VARCHAR(255) NOT NULL,
  arrival VARCHAR(255) NOT NULL,
  departure_date TIMESTAMP(0) WITHOUT TIME ZONE,
  arrival_date TIMESTAMP(0) WITHOUT TIME ZONE,
  available_seats INTEGER,
  price INTEGER
);

-- Tạo bảng users
CREATE TABLE IF NOT EXISTS users (
  name VARCHAR(255) NOT NULL,
  nationality VARCHAR(255) NOT NULL,
  passport VARCHAR(255) PRIMARY KEY,
  phone_number VARCHAR(255) NOT NULL,
  CONSTRAINT users_passport_unique UNIQUE (passport)
);

-- Tạo bảng bookings
CREATE TABLE IF NOT EXISTS bookings (
  booking_id SERIAL PRIMARY KEY,
  user_id VARCHAR(255) NOT NULL,
  flight_id VARCHAR(255) NOT NULL,
  price INTEGER,
  status VARCHAR(255) NOT NULL DEFAULT 'Pending',
  method VARCHAR (255)
);

-- Tạo bảng payments
--CREATE TABLE IF NOT EXISTS payments (
  --payment_id SERIAL PRIaARY KEY,
 -- booking_id INTEGER REFERENCES bookings(booking_id)
--);

-- Tạo bảng airport
CREATE TABLE IF NOT EXISTS airport (
  Airport VARCHAR(255) NOT NULL,
  Airport_code VARCHAR(255) NOT NULL,
  City VARCHAR(255) NOT NULL
);

-- Tạo bảng admins

CREATE TABLE IF NOT EXISTS accounts (
  id VARCHAR(255) NOT NULL ,
  password VARCHAR(255) NOT NULL ,
  isadmin INTEGER NOT NULL DEFAULT 0
);

CREATE FUNCTION create_account_after_user_insert()
RETURNS TRIGGER AS $$
BEGIN
  INSERT INTO accounts (id, password)
  VALUES (NEW.passport, 1);
  RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER create_account_trigger
AFTER INSERT ON users
FOR EACH ROW
EXECUTE FUNCTION create_account_after_user_insert();

-- Chèn dữ liệu vào bảng flights
--INSERT INTO flights (airline, departure, arrival, departure_date, arrival_date, available_seats, price) VALUES
--('Airline 1', 'Departure 1', 'Arrival 1', '2023-05-17 10:00:00', '2023-05-17 12:00:00', 100, 500.00),
--('Airline 2', 'Departure 2', 'Arrival 2', '2023-05-18 14:00:00', '2023-05-18 16:00:00', 150, 800.00),
--('Airline 3', 'Departure 3', 'Arrival 3', '2023-05-19 18:00:00', '2023-05-19 20:00:00', 200, 1200.00);

-- Chèn dữ liệu vào bảng users
INSERT INTO users (name, nationality, passport,  phone_number) VALUES
('Trí Trần', 'Australia', 'A1234567',  '123456789'),
('Uyên Nguyễn', 'Vietnam', 'B9876543',  '987654321'),
('Philips Trương', 'Nigeria', 'C4567890',  '111111111');

INSERT INTO accounts (id , password, isadmin) values
('admin','admin',1);

-- Chèn dữ liệu vào bảng bookings
--INSERT INTO bookings (user_id, flight_id, status) VALUES
--(1, 'Flight 1', 'Confirmed'),
--(2, 'Flight 2', 'Pending'),
--(1, 'Flight 3', 'Cancelled');

-- Chèn dữ liệu vào bảng payments
--INSERT INTO payments (booking_id) VALUES
--(1),
--(2),
--(3);

-- Chèn dữ liệu vào bảng airport
INSERT INTO airport (Airport, Airport_code, City) VALUES
('Sân bay Điện Biên Phủ', 'DIN', 'Điện Biên'),
('Sân bay Thọ Xuân', 'THD', 'Thanh Hóa'),
('Sân bay Đồng Hới', 'VDH', 'Quảng Bình'),
('Sân bay Chu Lai', 'VCL', 'Quảng Nam'),
('Sân bay Tuy Hòa', 'TBB', 'Phú Yên'),
('Sân bay Pleiku', 'PXU', 'Gia Lai'),
('Sân bay Buôn Mê Thuột', 'BMV', 'Đắk Lắk'),
('Sân bay Rạch Giá', 'VKG', 'Kiên Giang'),
('Sân bay Cà Mau', 'CAH', 'Cà Mau'),
('Sân bay Côn Đảo', 'VCS', 'Bà Rịa – Vũng Tàu'),
('Sân bay Nội Bài', 'HAN', 'Hà Nội'),
('Sân bay Tân Sơn Nhất', 'SGN', 'Hồ Chí Minh'),
('Sân bay Đà Nẵng', 'DAD', 'Đà Nẵng'),
('Sân bay Vân Đồn', 'VDO', 'Quảng Ninh'),
('Sân bay Cát Bi', 'HPH', 'Hải Phòng'),
('Sân bay Vinh', 'VII', 'Nghệ An'),
('Sân bay Phú Bài', 'HUI', 'Huế'),
('Sân bay Cam Ranh', 'CXR', 'Khánh Hòa'),
('Sân bay Liên Khương', 'DLI', 'Lâm Đồng'),
('Sân bay Phù Cát', 'UIH', 'Bình Định'),
('Sân bay Cần Thơ', 'VCA', 'Cần Thơ'),
('Sân bay Phú Quốc', 'PQC', 'Kiên Giang');

-- Chèn dữ liệu vào bảng admins
--INSERT INTO admins (id, password) VALUES
--('admin1', 'admin');
