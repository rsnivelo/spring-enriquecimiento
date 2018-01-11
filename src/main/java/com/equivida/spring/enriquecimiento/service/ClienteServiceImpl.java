package com.equivida.spring.enriquecimiento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equivida.spring.enriquecimiento.dao.IClienteDAO;
import com.equivida.spring.enriquecimiento.model.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteDAO dao;

	@Override
	public void create(Cliente cliente) {
		dao.create(cliente);
	}

	@Override
	public void update(Cliente cliente) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Cliente> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}
