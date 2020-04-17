package com.pkmittal.coronavirustracker.rest;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkmittal.coronavirustracker.model.CoronaData;
import com.pkmittal.coronavirustracker.model.CoronaIndia;
import com.pkmittal.coronavirustracker.model.DailyData;
import com.pkmittal.coronavirustracker.model.LocationStats;
import com.pkmittal.coronavirustracker.service.CoronaVirusTrackerService;

@Controller
public class CoronaVirusTrackerController {

	@Autowired
	CoronaVirusTrackerService coronaService;

	@GetMapping
	@RequestMapping(value = "/")
	public String home(Model model) throws IOException, InterruptedException, JSONException {
		List<LocationStats> allStats = coronaService.fetchVirusData();
		CoronaIndia coronaIndia = coronaService.fetchIndiaData();
		List<CoronaData> indiaStates = coronaIndia.getStatewise();
		List<DailyData> dailyData = coronaIndia.getCases_time_series();
		model.addAttribute("india_states", indiaStates);
		model.addAttribute("stats", allStats);
		DecimalFormat format = new DecimalFormat("##,##,###");
		SimpleDateFormat dateformat = new SimpleDateFormat("dd MMMM yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, -1);
		String strDate = dateformat.format(c.getTime());
		model.addAttribute("date", "last updated on "+strDate);
		model.addAttribute("confirmed", format.format(allStats.stream().mapToInt(p -> p.getConfirmed()).sum()));
		model.addAttribute("active", format.format(allStats.stream().mapToInt(p -> p.getActive()).sum()));
		model.addAttribute("deaths", format.format(allStats.stream().mapToInt(p -> p.getDeaths()).sum()));
		model.addAttribute("recovered", format.format(allStats.stream().mapToInt(p -> p.getRecovered()).sum()));
		
		model.addAttribute("confirmedIndia", format.format(Integer.valueOf(indiaStates.get(0).getConfirmed())));
		model.addAttribute("activeIndia", format.format(Integer.valueOf(indiaStates.get(0).getActive())));
		model.addAttribute("deathsIndia", format.format(Integer.valueOf(indiaStates.get(0).getDeaths())));
		model.addAttribute("recoveredIndia", format.format(Integer.valueOf(indiaStates.get(0).getRecovered())));
		indiaStates.remove(0);
		model.addAttribute("confirmedIndiaDelta", "+"+String.valueOf(Integer.valueOf(dailyData.get(dailyData.size()-1).getTotalconfirmed()) - Integer.valueOf(dailyData.get(dailyData.size()-2).getTotalconfirmed())));
		model.addAttribute("deathsIndiaDelta", "+"+String.valueOf(Integer.valueOf(dailyData.get(dailyData.size()-1).getTotaldeceased()) - Integer.valueOf(dailyData.get(dailyData.size()-2).getTotaldeceased())));
		model.addAttribute("recoveredIndiaDelta", "+"+String.valueOf(Integer.valueOf(dailyData.get(dailyData.size()-1).getTotalrecovered()) - Integer.valueOf(dailyData.get(dailyData.size()-2).getTotalrecovered())));
		return "home";
	}
	
}
