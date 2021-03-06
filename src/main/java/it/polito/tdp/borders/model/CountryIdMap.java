package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.Map;

public class CountryIdMap {

	private Map<Integer, Country> idMap;

	public CountryIdMap() {
		idMap = new HashMap<>();
	}

	//IMPLEMENTO I METODI DELLA MAPPA ALL'INTERNO DI UNA CLASSE, COSI' DA POTERLI RICHIAMARE
	public Country getCountry(int countryCode) {
		return idMap.get(countryCode);
	}

	public Country get(Country country) {
		//Se l'oggetto Country corrispondente al codice del parametro è null
		Country old = idMap.get(country.getcCode());
		if (old == null) {
			idMap.put(country.getcCode(), country);
			return country;
		}
		else return old;
	}
	
	public void put(int countryCode, Country country) {
		idMap.put(countryCode, country);
	}
}