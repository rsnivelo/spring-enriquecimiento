package com.equivida.spring.enriquecimiento.dao;

import java.util.List;

import com.equivida.spring.enriquecimiento.model.Cliente;

public interface IClienteDAO {

	void create(Cliente cliente);

	void update(Cliente cliente);

	List<Cliente> listAll();

	Cliente find(Integer id);

	void delete(Integer id);
}
