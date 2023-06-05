CREATE TABLE entries (
  id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  description VARCHAR(50) NOT NULL,
  due_date DATE NOT NULL,
  payday DATE,
  value DECIMAL(10, 2) NOT NULL,
  observation VARCHAR(100),
  type VARCHAR(20) NOT NULL,
  id_category BIGINT(20) NOT NULL,
  id_people BIGINT(20) NOT NULL,
  FOREIGN KEY (id_category) REFERENCES categories(id),
  FOREIGN KEY (id_people) REFERENCES peoples(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;