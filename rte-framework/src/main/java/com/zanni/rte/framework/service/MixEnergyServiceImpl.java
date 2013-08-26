package com.zanni.rte.framework.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zanni.rte.framework.MixEnergy;
import com.zanni.rte.framework.repository.MixEnergyRepository;

public class MixEnergyServiceImpl implements MixEnergyService {
	@Resource
	private MixEnergyRepository repository;

	@Override
	public MixEnergy findByLogDate(Date logDate) {
		return repository.findByLogDate(logDate);
	}

	@Override
	public List<MixEnergy> findByLogDateBetween(Date startDate, Date endDate) {
		return repository.findByLogDateBetween(startDate, endDate);
	}

	@Override
	public List<MixEnergy> findCO2ByLogDateBetween(Date startDate, Date endDate) {
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");
		return repository.findCO2ByLogDateBetween(format.format(startDate),
				format.format(endDate));
	}
}
