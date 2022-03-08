CREATE TABLE agenda(
	id serial PRIMARY KEY,
	descricao varchar(255),
	data_hora timestamp,
	data_criacao timestamp,
	paciente_id integer,
	CONSTRAINT fk_agenda_paciente FOREIGN KEY(paciente_id) REFERENCES paciente(id)
);