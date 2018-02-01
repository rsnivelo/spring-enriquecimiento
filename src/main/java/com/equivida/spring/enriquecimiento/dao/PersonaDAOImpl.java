package com.equivida.spring.enriquecimiento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.equivida.spring.enriquecimiento.model.Persona;

@Repository
public class PersonaDAOImpl implements IPersonaDAO { // extends JdbcDaoSupport

	@Autowired
	private DataSource dataSource;

	// @Autowired
	// public PersonaDAOImpl(DataSource dataSource) {
	// setDataSource(dataSource);
	// }

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
		// getJdbcTemplate().update(sql, new Object[] { persona.getCedula(),
		// persona.getXml() });
	}

	@Override
	public void update(Persona persona) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> listAll() {
		String sql = "SELECT respuesta_xml, id AS XMLDATA FROM persona where lote is null"; // lote is null"; //id = 29554
		List<String> resultList = new ArrayList<>();
		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				JSONObject json = XML.toJSONObject(rs.getString(1));
//				System.out.println(json + " >>>> " + rs.getLong(2) + "    \n+++++++++++++++++++");
//				String jsonPrettyPrintString = json.toString(4);
//	            System.out.println(jsonPrettyPrintString);
				
				if (json.getJSONObject("registros").getJSONObject("civil").get("cedula").toString().length() == 0) {
					continue;
				}
				
				String segundoApellido = json.getJSONObject("registros").getJSONObject("civil").has("segundoapellido") ? json.getJSONObject("registros").getJSONObject("civil").get("segundoapellido").toString() : "";
				String result = json.getJSONObject("registros").getJSONObject("civil").get("cedula") + "\t" +
						json.getJSONObject("registros").getJSONObject("civil").getString("primernombre") + " "
						+ json.getJSONObject("registros").getJSONObject("civil").get("segundonombre") + " "
						+ json.getJSONObject("registros").getJSONObject("civil").getString("primerapellido") + " "
						+ segundoApellido + "\t"
						+ json.getJSONObject("registros").getJSONObject("civil").getInt("dianacimiento") + "/"
						+ json.getJSONObject("registros").getJSONObject("civil").getInt("mesnacimiento") + "/"
						+ json.getJSONObject("registros").getJSONObject("civil").getInt("anionacimiento");
				
				resultList.add(result);
			}

			ps.close();
		} catch (Exception e) {
			System.out.println("11111" +  e.getMessage());
		}
		return resultList;
	}

	@Override
	public Persona find(Integer id) {
		// String sql = "SELECT unnest(xpath('.//civil/cedula',
		// respuesta_xml::xml))::text AS XMLDATA FROM persona where id = ?;";
		String sql = "SELECT respuesta_xml AS XMLDATA FROM persona where id = ?";
		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				JSONObject json = XML.toJSONObject(rs.getString(1));
				String jsonPrettyPrintString = json.toString(4);
				System.out.println(jsonPrettyPrintString);
			}

			ps.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}
