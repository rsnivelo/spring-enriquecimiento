package com.equivida.spring.enriquecimiento.service;

import java.util.List;

import com.equivida.spring.enriquecimiento.model.Cliente;

public interface IClienteService {

	void create(Cliente persona);

	void update(Cliente persona);

	List<Cliente> listAll();

	Cliente find(Integer id);

	void delete(Integer id);
}
