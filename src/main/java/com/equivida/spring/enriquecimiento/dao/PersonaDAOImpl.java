package com.equivida.spring.enriquecimiento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.equivida.spring.enriquecimiento.model.Persona;

@Repository
public class PersonaDAOImpl  implements IPersonaDAO { //extends JdbcDaoSupport

	@Autowired
	private DataSource dataSource;
	
//	@Autowired
//	public PersonaDAOImpl(DataSource dataSource) {
//		setDataSource(dataSource);
//	}

	@Override
	public void create(Persona persona) {
		String sql = "INSERT INTO public.persona (cedula, respuesta_xml) values (?, XML(?))";
		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, persona.getCedula());
			ps.setString(2, persona.getXml());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
//		getJdbcTemplate().update(sql, new Object[] { persona.getCedula(), persona.getXml() });
	}

	@Override
	public void update(Persona persona) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Persona> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Persona find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}
