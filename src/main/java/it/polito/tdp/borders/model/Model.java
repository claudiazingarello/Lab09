package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private Graph<Country, DefaultEdge> grafo ;
	private List<Country> countryList;
	private BordersDAO dao;
	private CountryIdMap countryIdMap;

	public Model() {
	}

	public void creaGrafo (int year) {
		dao = new BordersDAO();
		countryList = new ArrayList<>();
		this.grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);

		countryIdMap = new CountryIdMap();
		//Creazione dei vertici
		countryList = dao.loadAllCountries(countryIdMap);

		if(countryList.isEmpty()) {
			System.out.println("Non ci sono corrispondenze di stati per l'anno specificato");
		}

		for (Border b : dao.getCountryPairs(countryIdMap, year)) {
			grafo.addVertex(b.getC1());
			grafo.addVertex(b.getC2());

			DefaultEdge edge = grafo.getEdge(b.getC1(), b.getC2());
			if(edge == null) {
				grafo.addEdge(b.getC1(), b.getC2());
			}
		}

		System.out.println(String.format("\nGrafo creato!\nVertici: %d\nArchi: %d",this.grafo.vertexSet().size(), this.grafo.edgeSet().size()));

		//Ordino la lista dei vertici, ora modificata sulla base degli stati presenti nell'anno selezionato
		countryList = new ArrayList<Country>(this.grafo.vertexSet());
		Collections.sort(countryList);
	}

	public List<Country> getCountries(){
		if (grafo == null) {
			return new ArrayList<Country>();
		}
		return countryList;
	}

	/**
	 * Usa ConnectivityInspector 
	 * @return il numero di componenti connessi nel grafo sotto forma di connectedSets().size()
	 */
	public int getNumbersOfConnectedComponents() {
		if(grafo == null)
			throw new RuntimeException("Grafo non esistente");

		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<Country, DefaultEdge>(grafo);
		return ci.connectedSets().size();
	}

	public Map<Country, Integer> getCountryCounts() {
		if(grafo == null) {
			throw new RuntimeException("Grafo non esistente");
		}

		Map<Country, Integer> stats = new HashMap<Country, Integer>();
		for(Country c : grafo.vertexSet()) {
			stats.put(c, grafo.degreeOf(c));
		}
		return stats;
	}

	/*
	 * SECONDO ESERCIZIO
	 */
	public List<Country> getCountryRaggiungibili(Country selected) {
		// Controllo che il grafo contenga lo stato selezionato
		if(!grafo.vertexSet().contains(selected)) {
			throw new RuntimeException("Lo stato selezionato non è presente nel grafo");
		}

		List<Country> countryRaggiungibili = this.mostraTuttiViciniJGRAPHT(selected);
		System.out.println("Stati raggiungibili " + countryRaggiungibili.size());
		return countryRaggiungibili;
	}
	

	//Libreria JGRAPHT
	public List<Country> mostraTuttiViciniJGRAPHT(Country selected){
		/*
		 * Visita in profondità
		 * GraphIterator<Country, DefaultEdge> dfv = new DepthFirstIterator<Country, DefaultEdge>(graph, selected);
		 * while (dfv.hasNext()) {
		 * 	visited.add(dfv.next());
		 * }
		 */

		//visita in ampiezza
		List<Country> visita = new ArrayList<Country>();

		GraphIterator<Country, DefaultEdge> bfv = new BreadthFirstIterator<Country, DefaultEdge>(grafo, selected);

		while(bfv.hasNext()) {
			visita.add( bfv.next() );
		}
		return visita;
	}
}