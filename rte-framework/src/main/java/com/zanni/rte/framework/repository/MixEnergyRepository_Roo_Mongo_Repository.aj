// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.zanni.rte.framework.repository;

import com.zanni.rte.framework.MixEnergy;
import com.zanni.rte.framework.repository.MixEnergyRepository;
import java.math.BigInteger;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

privileged aspect MixEnergyRepository_Roo_Mongo_Repository {
    
    declare parents: MixEnergyRepository extends PagingAndSortingRepository<MixEnergy, BigInteger>;
    
    declare @type: MixEnergyRepository: @Repository;
    
}
