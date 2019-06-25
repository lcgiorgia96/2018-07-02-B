package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	Graph<Airport,DefaultWeightedEdge> grafo;
	List<Airport> aer ;
	ExtFlightDelaysDAO dao;
	Map<Integer,Airport> idMap;
	List<Airport> best;
	List<Airport> fin;
	Airport a;
	double totOre;
	public Model () {
		dao = new ExtFlightDelaysDAO();
		fin = new ArrayList<>();
	}
	
	
	public void creaGrafo(int x) {
	
		aer = dao.getAer(x);
		idMap = new HashMap<>();
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, aer);
		for (Airport a: grafo.vertexSet()) {
			idMap.put(a.getId(), a);
		}
		
		System.out.println(grafo.vertexSet().size());
		
		List<Rotta> rotte = dao.getRotte(idMap);
		
		for (Rotta r: rotte) {
			Airport a1 = r.getA1();
			Airport a2 = r.getA2();
			double peso = r.getMedia();
			
			if (grafo.getEdge(a1, a2)==null) {
			Graphs.addEdge(this.grafo, a1, a2, peso);
			} else {
				grafo.setEdgeWeight(grafo.getEdge(a1, a2), (grafo.getEdgeWeight(grafo.getEdge(a1, a2))+peso)/2);
			}
		}
		
		System.out.println(grafo.edgeSet().size());
	}


	public List<Airport> getAer() {
		return aer;
	}


	public List<Airport> getConnessi(Airport a) {
		List<Airport> conn = Graphs.neighborListOf(this.grafo, a);
		List<Rotta> r = new ArrayList<>();
		for (Airport a1: conn) {
		 r.add(new Rotta(a,a1,grafo.getEdgeWeight(grafo.getEdge(a, a1))));
		}
		Collections.sort(r);
		//fin = new ArrayList<>();
		for (Rotta r2: r) {
			fin.add(r2.getA2());
		}
		return fin;
	}


	public List<Airport> getItinerario(Airport a, int ore) {
		best = new LinkedList<>();
		List<Airport> parziale = new LinkedList<>();
		this.a=a;
		totOre=0.0;
		trova(a,parziale,ore);
		
		return best;
	}


	private void trova(Airport a, List<Airport> parziale, int ore) {
		
		List<Airport> connessi = Graphs.neighborListOf(this.grafo, a);
		if (totOre > ore) {
			return;
		}
		
		for (Airport a2 : connessi) {
			if (!parziale.contains(a2) && (totOre+2* grafo.getEdgeWeight(grafo.getEdge(a, a2))< ore)) {
					totOre += 2* grafo.getEdgeWeight(grafo.getEdge(a, a2));
					parziale.add(a2);
					trova (a,parziale,ore);
					parziale.remove(parziale.size()-1);
			}	
		}
		
		
		
		if (tot(parziale) > tot(best)) {
			best = new LinkedList<>(parziale);
		}
	}


	private double tot(List<Airport> parziale) {
		double tot = 0.0;
		
		for (Airport a2: parziale) {
			tot+=2*(grafo.getEdgeWeight(grafo.getEdge(a, a2)));
		}
		return tot;
	}


	public double getTotOre() {
		return totOre;
	}

}
