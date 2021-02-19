package it.objectmethod.esercizio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.esercizio.dao.ICityDao;
import it.objectmethod.esercizio.model.City;

@RestController
public class CityController {
	
	@Autowired
	private ICityDao cityDao;
	
	@GetMapping("/{country-code}/city")
	public List<City> cityNameGet(@PathVariable("country-code") String codiceNazione) {
		List<City> city = cityDao.findCityByCode(codiceNazione);

		return city;
	}
	
	@GetMapping("/get-city")
	public List<City> findCityByNameOrCountry(@RequestParam("nomeCitta") String citta, @RequestParam("codiceNazione") String codice) {
		List<City> city = cityDao.findCityByNameOrCode(citta, codice);
		 
		return city;
	}
	
	@GetMapping("/{id}/form-city")
	public ResponseEntity<City> prepareAddOrUpdateCity(@PathVariable("id") Long id) {
		ResponseEntity<City> resp = new ResponseEntity<>(HttpStatus.ACCEPTED);
		if(id != 0)
		{
			City city = cityDao.findCityByID(id);
			resp = new ResponseEntity<>(city,HttpStatus.ACCEPTED);
		}
		
		return resp;
	}
	
	@GetMapping("/add-update-city")
	public ResponseEntity<String> addOrUpdateCity(@RequestBody City c) {
		ResponseEntity<String> resp = new ResponseEntity<>("Errore durante l'operazione", HttpStatus.BAD_REQUEST);
		int righeAggiornate = 0;
		int righeInserite = 0;
		if(c.getId() != 0)
		{
			righeAggiornate = cityDao.updateCity(c.getId(), c.getName(), c.getCountryCode(), c.getDistrict(), c.getPopulation());
			if(righeAggiornate > 0)
			{
				resp = new ResponseEntity<>("Modifica completata!", HttpStatus.ACCEPTED);
			}
		}
		else
		{
			righeInserite = cityDao.addCity(c.getName(), c.getCountryCode(), c.getDistrict(), c.getPopulation());
			if(righeInserite > 0)
			{
				resp = new ResponseEntity<>("Inserimento completato!", HttpStatus.ACCEPTED);
			}
		}
		
		return resp;
	}
}
