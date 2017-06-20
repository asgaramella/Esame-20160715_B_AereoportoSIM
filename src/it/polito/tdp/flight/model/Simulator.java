package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class Simulator {
	
	private Map<Airport,Integer> passeggeri;
	private int K;
	private PriorityQueue<Event> queue;
	private SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge> graph;
	private Airport Parigi;
	private Airport NewYork;
	private double array[];
	
	public Simulator(int K,SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge> graph,Airport Parigi,Airport NewYork) {
		super();
		this.K=K;
		this.graph=graph;
		this.Parigi=Parigi;
		this.NewYork=NewYork;
		queue=new PriorityQueue<>();
		passeggeri=new HashMap<>();
		array=new double[9];
		
		double ora=7;
		for(int i=0;i<9;i++){
			array[i]=ora;
			
			ora=ora+2;
		}
		
		for(Airport atemp: graph.vertexSet()){
			passeggeri.put(atemp, 0);
		}
		
	}
	
	public void popola(){
		for(int i=1;i<=K/2;i++){
			queue.add(new Event(i,6,Parigi,1));
			passeggeri.put(Parigi, passeggeri.get(Parigi)+1);
		}
		
		for(int i=1;i<=K/2;i++){
			queue.add(new Event(i,6,NewYork,1));
			passeggeri.put(NewYork, passeggeri.get(NewYork)+1);
		}
		
	}
	
	public void run(){
		this.popola();
		
		while(!queue.isEmpty()){
			Event e=queue.poll();
			
			Airport arrivo=e.getAereoporto();
			
			
			int size=Graphs.successorListOf(graph, arrivo).size();
			//se riesce a partire
			if(size!=0 && e.getTratta()!=5){
				Random r=new Random();
				int pos=r.nextInt(size);
			Airport destinazione=Graphs.successorListOf(graph, arrivo).get(pos);
			passeggeri.put(arrivo,passeggeri.get(arrivo)-1);
			passeggeri.put(destinazione,passeggeri.get(destinazione)+1);
			double oraPart= getOraPartenza(e.getTempo());
			Event enew=new Event(e.getPasseggero(),oraPart+graph.getEdgeWeight(graph.getEdge(arrivo, destinazione)),destinazione,e.getTratta()+1);
			queue.add(enew);
			
			}
		}
		
	}

	private double getOraPartenza(double tempo) {
		double ora=-1;
		boolean  trovato=false;
		for(int i=0;i<9;i++){
			if(tempo<=array[i]){
				ora=array[i];
				trovato=true;
				break;
			}
		}
		//per orario maggiore delle 23
		if(trovato==false)
			ora=7;
		
		return ora;
	}
	
	
	public List<AirportAndNum> getPasseggeri(){
		this.run();
		List<AirportAndNum> stats=new ArrayList<>();
		for(Airport atemp:this.passeggeri.keySet()){
			if(passeggeri.get(atemp)!=0){
				stats.add(new AirportAndNum(atemp,passeggeri.get(atemp)));
			}
		
		}
		Collections.sort(stats);
		return stats;
	}

}
