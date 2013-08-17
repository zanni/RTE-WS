// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.rte.business.service;

import com.rte.business.MixEnergy;
import com.rte.business.repository.MixEnergyRepository;
import com.rte.business.service.MixEnergyServiceImpl;
import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

privileged aspect MixEnergyServiceImpl_Roo_Service {
    
    declare @type: MixEnergyServiceImpl: @Service;
    
    declare @type: MixEnergyServiceImpl: @Transactional;
    
    @Autowired
    MixEnergyRepository MixEnergyServiceImpl.mixEnergyRepository;
    
    public long MixEnergyServiceImpl.countAllMixEnergys() {
        return mixEnergyRepository.count();
    }
    
    public void MixEnergyServiceImpl.deleteMixEnergy(MixEnergy mixEnergy) {
        mixEnergyRepository.delete(mixEnergy);
    }
    
    public MixEnergy MixEnergyServiceImpl.findMixEnergy(BigInteger id) {
        return mixEnergyRepository.findOne(id);
    }
    
    public List<MixEnergy> MixEnergyServiceImpl.findAllMixEnergys() {
        return mixEnergyRepository.findAll();
    }
    
    public List<MixEnergy> MixEnergyServiceImpl.findMixEnergyEntries(int firstResult, int maxResults) {
        return mixEnergyRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }
    
    public void MixEnergyServiceImpl.saveMixEnergy(MixEnergy mixEnergy) {
        mixEnergyRepository.save(mixEnergy);
    }
    
    public MixEnergy MixEnergyServiceImpl.updateMixEnergy(MixEnergy mixEnergy) {
        return mixEnergyRepository.save(mixEnergy);
    }
    
}