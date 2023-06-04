CREATE TABLE categories (
  id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categories(name) VALUES('Lazer');
INSERT INTO categories(name) VALUES('Alimentação');
INSERT INTO categories(name) VALUES('Supermercado');
INSERT INTO categories(name) VALUES('Farmácia');
INSERT INTO categories(name) VALUES('Outros');