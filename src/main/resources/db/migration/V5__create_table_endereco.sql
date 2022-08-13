CREATE TABLE endereco(
	id serial PRIMARY KEY,
	rua varchar(255),
	numero integer,
	complemento varchar(255),
	bairro varchar(255),
	paciente_id integer,
    CONSTRAINT fk_endereco_paciente FOREIGN KEY(paciente_id) REFERENCES paciente(id)
);