package com.zanni.rte.framework.service;

import java.util.Date;
import java.util.List;

import com.zanni.rte.framework.MixEnergy;
import com.zanni.rte.framework.MixEnergyRegional;
import com.zanni.rte.framework.utils.RegionEnum;

public interface MixEnergyRegionalAggregateService {
	public List<MixEnergyRegional> aggregate(String agg, Date start, Date end,
			List<String> field, List<RegionEnum> region);

	public List<MixEnergyRegional> aggregateQuarter(Date startDate, Date endDate,
			List<String> field, List<RegionEnum> region);

	public List<MixEnergyRegional> aggregateHour(Date startDate, Date endDate,
			List<String> field, List<RegionEnum> region);

	public List<MixEnergyRegional> aggregateDay(Date startDate, Date endDate,
			List<String> field, List<RegionEnum> region);

	public List<MixEnergyRegional> aggregateMonth(Date startDate, Date endDate,
			List<String> field, List<RegionEnum> region);

	public List<MixEnergyRegional> aggregateYear(Date startDate, Date endDate,
			List<String> field, List<RegionEnum> region);
}
