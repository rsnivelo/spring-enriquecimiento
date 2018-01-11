package com.equivida.spring.enriquecimiento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.equivida.spring.enriquecimiento.model.Cliente;

@Repository
public class ClienteDAOImpl implements IClienteDAO {

	@Autowired
	private DataSource dataSource;

	@Override
	public void create(Cliente cliente) {
		String sql = "insert into public.cliente (cedula) values (?)";

		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, cliente.getCedula());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
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
