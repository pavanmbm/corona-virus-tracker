package com.pkmittal.coronavirustracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class DistrictData {

	private String district;
	private Integer confirmed;
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public Integer getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(Integer confirmed) {
		this.confirmed = confirmed;
	}
	
	
}
