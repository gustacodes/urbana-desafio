CREATE TABLE Usuario_Cartao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT,
    cartao_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
    FOREIGN KEY (cartao_id) REFERENCES Cartao(id)
);