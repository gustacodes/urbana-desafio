CREATE TABLE cartao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_cartao BIGINT,
    nome VARCHAR(250) NOT NULL,
    status BOOLEAN,
    tipo_cartao VARCHAR(20) NOT NULL,
    usuario_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);