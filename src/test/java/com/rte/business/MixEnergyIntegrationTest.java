package com.rte.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;

import com.rte.business.service.MixEnergyService;
import com.rte.business.service.RteAggregateService;

@RooIntegrationTest(entity = MixEnergy.class, transactional = false, findAll = false)
public class MixEnergyIntegrationTest {

	@Resource
	private MixEnergyService mixEnergyService;

	@Resource
	private RteAggregateService aggregateService;

	@Test
	public void testMarkerMethod() {
		Calendar start = new GregorianCalendar();
		start.set(2013, 0, 0);
		Calendar end = new GregorianCalendar();
		end.set(2013, 11, 0);
		// List<MixEnergy> findAllMixEnergys = mixEnergyService
		// .findCO2ByLogDateBetween(start.getTime(), end.getTime());
		List<String> fields = new ArrayList<String>();
		fields.add("taux_co2");
		fields.add("consommation");
		List<MixEnergy> findAllMixEnergys = aggregateService.aggregate("hour",
				start.getTime(), end.getTime(), fields);

		System.out.print(findAllMixEnergys);
	}
}
