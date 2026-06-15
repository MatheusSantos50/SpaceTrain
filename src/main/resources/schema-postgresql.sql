CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS imagens (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    usuario_id INTEGER REFERENCES usuarios(id),
    endereco VARCHAR(255) NOT NULL UNIQUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    visualizacoes INTEGER DEFAULT 0,
    curtidas INTEGER DEFAULT 0,
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS categorias (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO categorias (nome) VALUES
('Planeta'),
('Meteoro'),
('Buraco Negro'),
('Asteroide'),
('Galáxia'),
('Satélite Natural'),
('Supernova'),
('Estrela');

CREATE TABLE IF NOT EXISTS imagem_categorias (
    imagem_id INTEGER REFERENCES imagens(id) ON DELETE CASCADE,
    categoria_id INTEGER REFERENCES categorias(id) ON DELETE CASCADE,
    PRIMARY KEY (imagem_id, categoria_id)
);