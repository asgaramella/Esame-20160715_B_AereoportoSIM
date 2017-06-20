package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	
	private FlightDAO dao;
	private SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge> graph;
	private List<Airport> airports;
	private Map<Integer,Airport> mapAirports;
	private List<Route> rotte;
	private ConnectivityInspector<Airport,DefaultWeightedEdge> ci;
	private Simulator sim;
	
	
	public Model() {
		super();
		dao=new FlightDAO();
		
	}
	
	public List<Airport> getAllAirports(){
		if(airports==null){
			airports=dao.getAllAirports();
			this.mapAirports=new HashMap<Integer,Airport>();
			for(Airport atemp:airports)
				mapAirports.put(atemp.getAirportId(), atemp);
		}
		
		return airports;
	}
	
	private List<Route> getAllRoutes(){
		if(rotte==null)
			rotte=dao.getAllRoutes();
		
		return rotte;
	}
	
	
	
	public boolean creaGrafo(int minDistance){
		graph= new SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		List<Airport> vertici=new ArrayList<>();
		vertici.addAll(this.getAllAirports());
		vertici.removeAll(dao.getAllAirportsNoRoutes(this.mapAirports));
		
		Graphs.addAllVertices(graph, vertici);
		
		
		for(Route rtemp: this.getAllRoutes()){
			Airport partenza= this.mapAirports.get(rtemp.getSourceAirportId());
			Airport arrivo=this.mapAirports.get(rtemp.getDestinationAirportId());
			
			if(partenza !=null && arrivo !=null){
			
			double distance=LatLngTool.distance(new LatLng(partenza.getLatitude(),partenza.getLongitude()),new LatLng(arrivo.getLatitude(),arrivo.getLongitude()) , LengthUnit.KILOMETER);
			if(distance> minDistance){
				//controlla cosa fa in caso di arco già esistente
				Graphs.addEdgeWithVertices(graph,partenza ,arrivo, distance/900);
				
			}
		}
		}
		
		ci=new ConnectivityInspector<>(graph);
		
		return ci.isGraphConnected();
		
		
	}
	
	public Airport getVicinissimo(int losAngeles){
		Airport los=this.mapAirports.get(losAngeles);
		Airport vicino=null;
		List<Airport> nonRaggiungibili=new ArrayList<>();
		nonRaggiungibili.addAll(graph.vertexSet());
		nonRaggiungibili.removeAll(ci.connectedSetOf(los));
		
		double minDistance=Integer.MAX_VALUE;
		for(Airport atemp: nonRaggiungibili){
			if(!atemp.equals(los)){
			double d=LatLngTool.distance(new LatLng(atemp.getLatitude(),atemp.getLongitude()),new LatLng(los.getLatitude(),los.getLongitude()) , LengthUnit.KILOMETER);
			if(d<minDistance){
				vicino=atemp;
				minDistance=d;
			}
			}
		}
		
		return vicino;
		
	}
	
	public List<AirportAndNum> doSimula(int K){
		Airport parigi=this.mapAirports.get(1382);
		Airport newyork=this.mapAirports.get(3697);
		sim=new Simulator(K,graph,parigi,newyork);
		
		
		return sim.getPasseggeri();
		
		
		
	}
	

}
