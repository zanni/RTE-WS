package com.rte.business.service;

import java.util.Date;
import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import com.rte.business.MixEnergy;

@RooService(domainTypes = { com.rte.business.MixEnergy.class })
public interface MixEnergyService {
	public MixEnergy findByLogDate(Date logDate);
	public List<MixEnergy> findByLogDateBetween(Date startDate, Date endDate);
	public List<MixEnergy> findCO2ByLogDateBetween(Date startDate, Date endDate);
}
