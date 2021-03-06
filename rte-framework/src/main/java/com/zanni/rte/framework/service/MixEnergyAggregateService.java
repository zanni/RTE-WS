package com.zanni.rte.framework.service;

import java.util.Date;
import java.util.List;

import com.zanni.rte.framework.MixEnergy;

public interface MixEnergyAggregateService {
	public List<MixEnergy> aggregate(String agg, Date start, Date end, List<String> field);
	public List<MixEnergy> aggregateQuarter(Date startDate,Date endDate, List<String> field);
	public List<MixEnergy> aggregateHour(Date startDate,Date endDate, List<String> field);
	public List<MixEnergy> aggregateDay(Date startDate,Date endDate, List<String> field);
	public List<MixEnergy> aggregateMonth(Date startDate,Date endDate, List<String> field);
	public List<MixEnergy> aggregateYear(Date startDate,Date endDate, List<String> field);
}
