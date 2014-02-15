package com.zanni.rte.framework.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zanni.rte.framework.MixEnergyRegional;
import com.zanni.rte.framework.repository.MixEnergyRegionalRepository;
import com.zanni.rte.framework.utils.RegionEnum;

public class MixEnergyRegionalServiceImpl implements MixEnergyRegionalService {
	@Resource
	private MixEnergyRegionalRepository repository;

	@Override
	public MixEnergyRegional findByLogDateAndRegion(Date logDate,
			RegionEnum region) {
		return repository.findByLogDateAndRegion(logDate, region);
	}

	@Override
	public List<MixEnergyRegional> findByLogDateBetweenAndRegion(
			Date startDate, Date endDate, RegionEnum region) {
		return repository.findByLogDateBetweenAndRegion(startDate, endDate, region);
	}

}
