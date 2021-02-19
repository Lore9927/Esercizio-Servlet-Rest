package it.objectmethod.esercizio.dao;

import java.util.List;

import it.objectmethod.esercizio.model.Country;

public interface ICountryDao {
	
	public List<Country> getContinent();
	
	public List<Country> findCountryByContinent(String continent);
	
}
