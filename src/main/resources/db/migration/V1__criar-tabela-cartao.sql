CREATE TABLE Cartao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numeroCartao BIGINT,
    nome VARCHAR(250) NOT NULL,
    status BOOLEAN,
    tipoCartao VARCHAR(20) NOT NULL
);