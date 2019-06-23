package it.polito.tdp.extflightdelays.model;

public class Rotta implements Comparable<Rotta> {

	private Airport a1;
	private Airport a2;
	private double media;
	public Airport getA1() {
		return a1;
	}
	public void setA1(Airport a1) {
		this.a1 = a1;
	}
	public Airport getA2() {
		return a2;
	}
	public void setA2(Airport a2) {
		this.a2 = a2;
	}
	public double getMedia() {
		return media;
	}
	public void setMedia(double media) {
		this.media = media;
	}
	public Rotta(Airport a1, Airport a2, double media) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.media = media;
	}
	@Override
	public String toString() {
		return a1+" "+a2+" "+media;
	}
	@Override
	public int compareTo(Rotta o) {
	
		return (int)(this.media-o.media);
	} 
	
	
}
