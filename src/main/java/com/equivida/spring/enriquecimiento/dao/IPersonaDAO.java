package com.equivida.spring.enriquecimiento.dao;

import java.util.List;

import com.equivida.spring.enriquecimiento.model.Persona;

public interface IPersonaDAO {

	void create(Persona persona);

	void update(Persona persona);

	List<String> listAll();

	Persona find(Integer id);

	void delete(Integer id);
}
