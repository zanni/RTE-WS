package com.zanni.rte.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zanni.rte.framework.MixEnergy;
import com.zanni.rte.framework.MixEnergyRegional;
import com.zanni.rte.framework.service.MixEnergyAggregateService;
import com.zanni.rte.framework.service.MixEnergyRegionalAggregateService;
import com.zanni.rte.framework.utils.MixEnergyFieldAggregateEnum;
import com.zanni.rte.framework.utils.RegionEnum;

@RooWebJson(jsonObject = MixEnergy.class)
@Controller
@RequestMapping("/mixenergys")
public class MixEnergyController {
	public final static String FIELDS_DELIMITER = ",";

	@Resource
	private MixEnergyAggregateService aggService;

	@Resource
	private MixEnergyRegionalAggregateService aggRegionalService;

	private List<MixEnergy> aggregate(String agg, String fields,
			String startStr, String endStr) {

		if (fields.equals("*"))
			fields = MixEnergyFieldAggregateEnum.getAll();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		List<String> fields_list = new ArrayList<String>();
		for (String str : fields.split(MixEnergyController.FIELDS_DELIMITER)) {
			fields_list.add(str);
		}
		Date start;
		try {
			start = format.parse(startStr);
			Date end = format.parse(endStr);
			return aggService.aggregate(agg, start, end, fields_list);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<MixEnergyRegional> aggregateRegional(String agg,
			String fields, String startStr, String endStr, String region) {

		if (fields.equals("*"))
			fields = MixEnergyFieldAggregateEnum.getAll();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		List<String> fields_list = new ArrayList<String>();
		for (String str : fields.split(MixEnergyController.FIELDS_DELIMITER)) {
			fields_list.add(str);
		}
		List<RegionEnum> region_list = new ArrayList<RegionEnum>();
		for (String str : region.split(MixEnergyController.FIELDS_DELIMITER)) {
			RegionEnum fromCode = RegionEnum.fromCode(str);
			if(fromCode != null){
				region_list.add(fromCode);
			}
			
		}
		Date start;
		try {
			start = format.parse(startStr);
			Date end = format.parse(endStr);
			return aggRegionalService.aggregate(agg, start, end, fields_list,
					region_list);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/{agg}/{fields}/between/{start}/{end}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> findByLogDateBetween(
			@PathVariable("agg") String agg,
			@PathVariable("fields") String fields,
			@PathVariable("start") String startStr,
			@PathVariable("end") String endStr,
			@RequestParam(value = "region", required = false) String region) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		if (region == null) {
			return new ResponseEntity<String>(MixEnergy.toJsonArray(aggregate(
					agg, fields, startStr, endStr)), headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(
					MixEnergyRegional.toJsonArray(aggregateRegional(agg,
							fields, startStr, endStr, region)), headers,
					HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/{agg}/{fields}/between/jsonp/{start}/{end}", method = RequestMethod.GET)
	public ResponseEntity<String> getLocations(@PathVariable("agg") String agg,
			@PathVariable("fields") String fields,
			@PathVariable("start") String startStr,
			@PathVariable("end") String endStr,
			@RequestParam(value = "region", required = false) String region,
			@RequestParam(value = "callback", required = false) String callback) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin",
				"http://www.domain.com");
		responseHeaders.set("Access-Control-Allow-Methods", "GET");
		responseHeaders.set("Access-Control-Allow-Headers", "");
		responseHeaders.set("Access-Control-Max-Age", "86400");

		String json = new String();
		if(region == null){
			if (callback.trim().length() > 0) {
				json = callback
						+ "("
						+ MixEnergy.toJsonArray(aggregate(agg, fields, startStr,
								endStr)) + ")";
			}
			return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
		}
		else {
			if (callback.trim().length() > 0) {
				json = callback
						+ "("
						+ MixEnergyRegional.toJsonArray(aggregateRegional(agg, fields, startStr,
								endStr, region)) + ")";
			}
			return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
		}
	}

}
