package com.pkmittal.coronavirustracker.rest;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkmittal.coronavirustracker.model.CoronaData;
import com.pkmittal.coronavirustracker.model.LocationStats;
import com.pkmittal.coronavirustracker.service.CoronaVirusTrackerService;

@Controller
public class CoronaVirusTrackerController {

	@Autowired
	CoronaVirusTrackerService coronaService;

	@GetMapping
	@RequestMapping(value = "/corona")
	public String coronaTracker(Model model) throws IOException, InterruptedException {
		List<LocationStats> allStats = coronaService.getAllStats();
		model.addAttribute("stats", allStats);
		return "corona";
	}
	@GetMapping
	@RequestMapping(value = "/")
	public String home(Model model) throws IOException, InterruptedException, JSONException {
		List<LocationStats> allStats = coronaService.getAllStats();
		List<CoronaData> indiaStates = coronaService.fetchIndiaData();
		model.addAttribute("india_states", indiaStates);
		model.addAttribute("stats", allStats);
		DecimalFormat format = new DecimalFormat("##,##,###");
		model.addAttribute("confirmed", format.format(allStats.stream().mapToInt(p -> p.getConfirmed()).sum()));
		model.addAttribute("active", format.format(allStats.stream().mapToInt(p -> p.getActive()).sum()));
		model.addAttribute("deaths", format.format(allStats.stream().mapToInt(p -> p.getDeaths()).sum()));
		model.addAttribute("recovered", format.format(allStats.stream().mapToInt(p -> p.getRecovered()).sum()));
		return "home";
	}
	
}
