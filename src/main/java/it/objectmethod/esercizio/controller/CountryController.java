package it.objectmethod.esercizio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.esercizio.dao.ICountryDao;
import it.objectmethod.esercizio.model.Country;

@RestController
public class CountryController {
	
	@Autowired
	private ICountryDao countryDao;
	
	@GetMapping("/continent")
	public List<Country> getContinents() {
		List<Country> conts = countryDao.getContinent();

		return conts;
	}
	
	@GetMapping("/country")
	public List<Country> countryNameGet(@RequestParam("cont") String continente) {
		List<Country> country = countryDao.findCountryByContinent(continente);

		return country;
	}
	
}
