package it.objectmethod.esercizio.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.objectmethod.esercizio.dao.ICountryDao;
import it.objectmethod.esercizio.model.Country;

@Component
public class CountryDaoImpl implements ICountryDao {

	private Connection getConnection()
	{
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:33060/world", "omdev", "omdev");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}

	@Override
	public List<Country> getContinent() {
		Connection conn = getConnection();
		Country country = null;
		List<Country> lista = new ArrayList<Country>();
		String sql = "SELECT DISTINCT Continent FROM country";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				country = new Country();
				country.setContinent(rs.getString("Continent"));
				lista.add(country);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<Country> findCountryByContinent(String continent) {
		Connection conn = getConnection();
		Country country = null;
		List<Country> lista = new ArrayList<Country>();
		
		String sql = "SELECT Name, Population, Code FROM country WHERE Continent = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, continent);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				country = new Country();
				country.setName(rs.getString("Name"));
				country.setPopulation(rs.getLong("Population"));
				country.setCode(rs.getString("Code"));
				lista.add(country);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lista;
	}
}
