CREATE TABLE peoples (
  id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  active BOOLEAN DEFAULT 1,
  street VARCHAR(100) NULL,
  number VARCHAR(10) NULL,
  complement VARCHAR(100) NULL,
  district VARCHAR(20) NULL,
  zipcode VARCHAR(8) NULL,
  city VARCHAR(20) NULL,
  state VARCHAR(2) NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;