CREATE TABLE Usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL,
    senha VARCHAR(250) NOT NULL,
    cartao_id BIGINT,
    FOREIGN KEY (cartao_id) REFERENCES Cartao(id)
);
