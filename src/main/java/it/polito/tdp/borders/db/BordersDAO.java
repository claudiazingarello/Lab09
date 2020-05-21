package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CountryIdMap;

public class BordersDAO {

	public List<Country> loadAllCountries(CountryIdMap countryIdMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> listCountries = new ArrayList<Country>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c = new Country(rs.getString("StateAbb"), rs.getInt("ccode"), rs.getString("StateNme"));
				listCountries.add(countryIdMap.get(c));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		return listCountries;
	}

	/*public void loadAllCountries(CountryIdMap countryIdMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";


		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c = new Country(rs.getString("StateAbb"), rs.getInt("ccode"), rs.getString("StateNme"));
				if(countryIdMap.getCountry(rs.getInt("ccode")) == null) {
					countryIdMap.put(c.getcCode(), c);
				}
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	 */

	public List<Border> getCountryPairs(CountryIdMap countryIdMap, int anno) {
		String sql = "SELECT c.state1no, c.state2no " + 
				"FROM contiguity AS c " + 
				"WHERE c.year <= ? AND c.conttype =1";
		List<Border> listBorders = new ArrayList<Border>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int cc1 = rs.getInt("c.state1no");
				int cc2 = rs.getInt("c.state2no");

				//L'idMap garantisce l'unicità degli oggetti Country
				Country c1 = countryIdMap.getCountry(cc1);
				Country c2 = countryIdMap.getCountry(cc2);

				if(c1 != null && c2 != null) {
					Border b = new Border(c1, c2);
					listBorders.add(b);
				}
				else {
					System.out.println("Error skipping " + String.valueOf(cc1) + " - " + String.valueOf(cc2));
				}
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

		return listBorders;
	}


}
