package com.pkmittal.coronavirustracker.model;

import java.util.List;

public class CoronaData {

	private String state;
	private List<DistrictData> districtData;
	private Integer confirmed;
	
	public void setConfirmed(Integer confirmed) {
		this.confirmed = confirmed;
	}
	
	public Integer getConfirmed() {
		return confirmed;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<DistrictData> getDistrictData() {
		return districtData;
	}
	public void setDistrictData(List<DistrictData> districtData) {
		this.districtData = districtData;
	}
	
	
}
