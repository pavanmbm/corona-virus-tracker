package com.pkmittal.coronavirustracker.model;

public class DailyData {
	
	private String totalconfirmed;
	private String date;
	private String totaldeceased;
	private String totalrecovered;
	public String getTotalconfirmed() {
		return totalconfirmed;
	}
	public void setTotalconfirmed(String totalconfirmed) {
		this.totalconfirmed = totalconfirmed;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTotaldeceased() {
		return totaldeceased;
	}
	public void setTotaldeceased(String totaldeceased) {
		this.totaldeceased = totaldeceased;
	}
	public String getTotalrecovered() {
		return totalrecovered;
	}
	public void setTotalrecovered(String totalrecovered) {
		this.totalrecovered = totalrecovered;
	}
	
	

}
