package com.rte.business.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

import com.rte.business.MixEnergy;

@RooMongoRepository(domainType = MixEnergy.class)
public interface MixEnergyRepository {
	
	@Query
    public List<MixEnergy> findAll();
    
    @Query 
    public MixEnergy findByLogDate(Date logDate);
    
    @Query 
    public List<MixEnergy> findByLogDateBetween(Date startDate, Date endDate);
    
    @Query( value="{'logDate' : { $gte :{$date : ?0 }, $lt :{$date : ?1 } } }", fields = "{'taux_co2' : 1, 'logDate' : 1  }")
    public List<MixEnergy> findCO2ByLogDateBetween(String startDate, String endDate);
    
}
