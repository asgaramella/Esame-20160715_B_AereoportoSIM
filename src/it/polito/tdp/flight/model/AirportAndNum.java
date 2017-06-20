package it.polito.tdp.flight.model;

public class AirportAndNum implements Comparable<AirportAndNum>{
	private Airport airport;
	private int pass;
	
	public AirportAndNum(Airport airport, int pass) {
		super();
		this.airport = airport;
		this.pass = pass;
	}

	public Airport getAirport() {
		return airport;
	}

	public void setAirport(Airport airport) {
		this.airport = airport;
	}

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

	@Override
	public int compareTo(AirportAndNum other) {
		
		return -(this.pass-other.getPass());
	}
	
	
	

}
