package com.zanni.rte.framework.service;

import java.util.Date;
import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import com.zanni.rte.framework.MixEnergyRegional;
import com.zanni.rte.framework.utils.RegionEnum;

@RooService(domainTypes = { MixEnergyRegional.class })
public interface MixEnergyRegionalService {
	public MixEnergyRegional findByLogDateAndRegion(Date logDate,
			RegionEnum region);

	public List<MixEnergyRegional> findByLogDateBetweenAndRegion(
			Date startDate, Date endDate, RegionEnum region);
}
