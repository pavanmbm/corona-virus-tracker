package com.pkmittal.coronavirustracker.model;

public class CoronaData {

	private String state;
	private String confirmed;
	private String deaths;
	private String recovered;
	private String active;
	
	public void setActive(String active) {
		this.active = active;
	}
	
	public String getActive() {
		return active;
	}
	
	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}
	
	public String getConfirmed() {
		return confirmed;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String getDeaths() {
		return deaths;
	}

	public void setDeaths(String deaths) {
		this.deaths = deaths;
	}

	public String getRecovered() {
		return recovered;
	}

	public void setRecovered(String recovered) {
		this.recovered = recovered;
	}
	
}
