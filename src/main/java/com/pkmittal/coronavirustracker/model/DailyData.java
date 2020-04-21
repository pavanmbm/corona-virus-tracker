package com.pkmittal.coronavirustracker.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
	
	public static void main(String[] args) throws ParseException {
		String str = "06 April";
		
		SimpleDateFormat format = new SimpleDateFormat("dd MMM");
		SimpleDateFormat format1 = new SimpleDateFormat("dd MMM");
		System.out.println(format1.format(format.parse(str)));
	}

}
