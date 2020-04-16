package com.pkmittal.coronavirustracker.service;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pkmittal.coronavirustracker.model.CoronaData;
import com.pkmittal.coronavirustracker.model.LocationStats;

@Service
public class CoronaVirusTrackerService {

	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/<date>.csv";
	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	private List<LocationStats> allStats = new ArrayList<>();

	public List<LocationStats> getAllStats() {
		return allStats;
	}

	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchVirusData() throws IOException, InterruptedException {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, -1);
		String strDate = format.format(c.getTime());
		System.out.println(strDate);
		HttpGet request = new HttpGet(VIRUS_DATA_URL.replace("<date>", strDate));

		try (CloseableHttpResponse response = httpClient.execute(request)) {

			HttpEntity entity = response.getEntity();

			// return it as a String
			String result = EntityUtils.toString(entity);
			StringReader csvBodyReader = new StringReader(result);
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
			Map<String, LocationStats> cormap = new HashMap<>();
			for (CSVRecord record : records) {
				LocationStats locationStat = cormap.get(record.get("Country_Region"));
				if (locationStat == null) {
					locationStat = new LocationStats();
					locationStat.setCountry(record.get("Country_Region"));
					locationStat.setConfirmed(Integer.parseInt(record.get("Confirmed")));
					locationStat.setDeaths(Integer.parseInt(record.get("Deaths")));
					locationStat.setRecovered(Integer.parseInt(record.get("Recovered")));
					locationStat.setActive(Integer.parseInt(record.get("Active")));
				}
				else{
					locationStat.setConfirmed(Integer.parseInt(record.get("Confirmed"))+locationStat.getConfirmed());
					locationStat.setDeaths(Integer.parseInt(record.get("Deaths"))+locationStat.getDeaths());
					locationStat.setRecovered(Integer.parseInt(record.get("Recovered"))+locationStat.getRecovered());
					locationStat.setActive(Integer.parseInt(record.get("Active"))+locationStat.getActive());
				}
				cormap.put(record.get("Country_Region"), locationStat);
			}
			allStats = new ArrayList<>(cormap.values());
			allStats = allStats.stream()
					.sorted(Comparator.comparingInt(LocationStats::getConfirmed).reversed())
					.collect(Collectors.toList());
		}

	}
	
	public List<CoronaData> fetchIndiaData() throws IOException, InterruptedException, JSONException {
		HttpGet request = new HttpGet("https://api.covid19india.org/v2/state_district_wise.json");
		List<CoronaData> list = new ArrayList<>();
		try (CloseableHttpResponse response = httpClient.execute(request)) {

			HttpEntity entity = response.getEntity();

			// return it as a String
			String result = EntityUtils.toString(entity);
			JSONArray array = new JSONArray(result);
			Gson gson = new GsonBuilder().create();
			for(int i = 0; i < array.length(); i++){
				JSONObject objectInArray = array.getJSONObject(i);
				CoronaData data = gson.fromJson(objectInArray.toString(), CoronaData.class);
				data.setConfirmed(data.getDistrictData().stream().mapToInt(p -> p.getConfirmed()).sum());
				list.add(data);
			}
			list = list.stream()
					.sorted(Comparator.comparingInt(CoronaData::getConfirmed).reversed())
					.collect(Collectors.toList());
		}
		return list;

	}

}