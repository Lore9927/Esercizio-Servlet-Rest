package it.objectmethod.esercizio.dao;

import java.util.List;

import it.objectmethod.esercizio.model.City;

public interface ICityDao {
	
	public List<City> findCityByNameOrCode(String name, String code);
	
	public List<City> findCityByCode(String code);
	
	public City findCityByID(Long id);
	
	public int addCity(String name, String countryCode, String district, Long population);
	
	public int deleteCity(Long id);
	
	public int updateCity(Long id, String name, String countryCode, String district, Long population);
}
