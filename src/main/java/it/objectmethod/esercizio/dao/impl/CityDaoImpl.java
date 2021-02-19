package it.objectmethod.esercizio.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.objectmethod.esercizio.dao.ICityDao;
import it.objectmethod.esercizio.model.City;

@Component
public class CityDaoImpl implements ICityDao{

	private Connection getConnection() {
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
	public List<City> findCityByNameOrCode(String name, String code) {
		Connection conn = getConnection();
		City city = null;
		List<City> lista = new ArrayList<City>();
	
		String sql = "SELECT ID, Name, Population FROM city WHERE (Name LIKE ? OR '' = ?) AND (CountryCode = ? OR 'nessunaNazione' = ?)";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+ name + "%");
			stmt.setString(2, name);
			stmt.setString(3, code);
			stmt.setString(4, code);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				city = new City();
				city.setId(rs.getLong("ID"));
				city.setName(rs.getString("Name"));
				city.setPopulation(rs.getLong("Population"));
				lista.add(city);
				
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return lista;
	}

	@Override
	public int addCity(String name, String countryCode, String district, Long population) {
		Connection conn = getConnection();
		int righeInserite = 0;
		String sql = "INSERT INTO city (Name,CountryCode,District,Population) VALUES (?,?,?,?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, countryCode);
			stmt.setString(3, district);
			stmt.setLong(4, population);
			righeInserite = stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return righeInserite;
	}

	@Override
	public int deleteCity(Long id) {
		Connection conn = getConnection();
		int righeCancellate = 0;
		String sql = "DELETE FROM city WHERE ID = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, id);
			righeCancellate = stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return righeCancellate;
	}

	@Override
	public int updateCity(Long id, String name, String countryCode, String district, Long population) {
		Connection conn = getConnection();
		int righeAggiornate = 0;
		String sql = "UPDATE city SET Name = ?, CountryCode = ?, District = ?, Population = ? WHERE ID = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, countryCode);
			stmt.setString(3, district);
			stmt.setLong(4, population);
			stmt.setLong(5, id);
			
			righeAggiornate = stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return righeAggiornate;
	}

	@Override
	public List<City> findCityByCode(String code) {
		Connection conn = getConnection();
		City city = null;
		List<City> lista = new ArrayList<City>();
		
		String sql = "SELECT ID, Name, Population FROM city WHERE CountryCode = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, code);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				city = new City();
				city.setId(rs.getLong("ID"));
				city.setName(rs.getString("Name"));
				city.setPopulation(rs.getLong("Population"));
				lista.add(city);
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
	public City findCityByID(Long id) {
		Connection conn = getConnection();
		City city = null;
		
		String sql = "SELECT Name, CountryCode, District, Population FROM city WHERE ID = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				city = new City();
				city.setId(id);
				city.setName(rs.getString("Name"));
				city.setCountryCode(rs.getString("CountryCode"));
				city.setDistrict(rs.getString("District"));
				city.setPopulation(rs.getLong("Population"));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return city;
	}

}
