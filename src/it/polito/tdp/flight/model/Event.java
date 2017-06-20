package it.polito.tdp.flight.model;

public class Event implements Comparable<Event>{
	private int passeggero;
	private double tempo;
	private Airport aereoporto;
	private int tratta;
	
	public Event(int passeggero, double tempo, Airport aereoporto, int tratta) {
		super();
		this.passeggero = passeggero;
		this.tempo = tempo;
		this.aereoporto = aereoporto;
		this.tratta = tratta;
	}

	public int getPasseggero() {
		return passeggero;
	}

	public void setPasseggero(int passeggero) {
		this.passeggero = passeggero;
	}

	public double getTempo() {
		return tempo;
	}

	public void setTempo(double tempo) {
		this.tempo = tempo;
	}

	public Airport getAereoporto() {
		return aereoporto;
	}

	public void setAereoporto(Airport aereoporto) {
		this.aereoporto = aereoporto;
	}

	public int getTratta() {
		return tratta;
	}

	public void setTratta(int tratta) {
		this.tratta = tratta;
	}

	@Override
	public int compareTo(Event other) {
	
		return Double.compare(this.tempo, other.getTempo());
	}
	
	
	
	

}
