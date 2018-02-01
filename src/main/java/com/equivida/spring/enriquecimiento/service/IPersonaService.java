package com.equivida.spring.enriquecimiento.service;

import java.util.List;

import com.equivida.spring.enriquecimiento.model.Persona;

public interface IPersonaService {

	void create(Persona persona);

	void update(Persona persona);

	List<String> listAll();

	Persona find(Integer id);

	void delete(Integer id);
}
