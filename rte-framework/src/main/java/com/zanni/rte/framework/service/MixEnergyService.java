package com.zanni.rte.framework.service;

import java.util.Date;
import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import com.zanni.rte.framework.MixEnergy;

@RooService(domainTypes = { MixEnergy.class })
public interface MixEnergyService {
	public MixEnergy getCurrent();
	public MixEnergy findByLogDate(Date logDate);
	public List<MixEnergy> findByLogDateBetween(Date startDate, Date endDate);
	public List<MixEnergy> findCO2ByLogDateBetween(Date startDate, Date endDate);
}
