package com.zanni.rte.framework.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

import com.zanni.rte.framework.MixEnergyRegional;
import com.zanni.rte.framework.utils.RegionEnum;

@RooMongoRepository(domainType = MixEnergyRegional.class)
public interface MixEnergyRegionalRepository {

	@Query
	public List<MixEnergyRegional> findAll();

	@Query
	public MixEnergyRegional findByLogDateAndRegion(Date logDate,
			RegionEnum region);

	@Query
	public List<MixEnergyRegional> findByLogDateBetweenAndRegion(
			Date startDate, Date endDate, RegionEnum region);
}
