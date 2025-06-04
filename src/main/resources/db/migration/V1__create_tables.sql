CREATE TABLE processo_judicial (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero VARCHAR(25) NOT NULL UNIQUE,
    vara VARCHAR(100) NOT NULL,
    comarca VARCHAR(100) NOT NULL,
    assunto VARCHAR(255),
    status VARCHAR(20) NOT NULL
);

CREATE TABLE audiencia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_hora TIMESTAMP NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    local VARCHAR(255) NOT NULL,
    processo_id BIGINT NOT NULL,
    CONSTRAINT fk_processo FOREIGN KEY (processo_id) REFERENCES processo_judicial(id)
);
