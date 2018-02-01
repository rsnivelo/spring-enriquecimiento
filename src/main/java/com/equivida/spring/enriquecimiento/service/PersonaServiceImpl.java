package com.equivida.spring.enriquecimiento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equivida.spring.enriquecimiento.dao.IPersonaDAO;
import com.equivida.spring.enriquecimiento.model.Persona;

@Service
public class PersonaServiceImpl implements IPersonaService {

	@Autowired
	private IPersonaDAO dao;

	@Override
	public void create(Persona persona) {
		dao.create(persona);
	}

	@Override
	public void update(Persona persona) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> listAll() {
		return dao.listAll();
	}

	@Override
	public Persona find(Integer id) {
		return dao.find(id);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}
