package com.pkmittal.coronavirustracker.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkmittal.coronavirustracker.model.LocationStats;
import com.pkmittal.coronavirustracker.service.CoronaVirusTrackerService;

@Controller
public class CoronaVirusTrackerController {

	@Autowired
	CoronaVirusTrackerService coronaService;

	@GetMapping
	@RequestMapping(value = "/")
	public String helloWorld(Model model) throws IOException, InterruptedException {
		List<LocationStats> allStats = coronaService.getAllStats();
		model.addAttribute("stats", allStats);
		return "home";
	}
}
