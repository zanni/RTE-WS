// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.rte.business.service;

import com.rte.business.MixEnergy;
import com.rte.business.service.MixEnergyService;
import java.math.BigInteger;
import java.util.List;

privileged aspect MixEnergyService_Roo_Service {
    
    public abstract long MixEnergyService.countAllMixEnergys();    
    public abstract void MixEnergyService.deleteMixEnergy(MixEnergy mixEnergy);    
    public abstract MixEnergy MixEnergyService.findMixEnergy(BigInteger id);    
    public abstract List<MixEnergy> MixEnergyService.findAllMixEnergys();    
    public abstract List<MixEnergy> MixEnergyService.findMixEnergyEntries(int firstResult, int maxResults);    
    public abstract void MixEnergyService.saveMixEnergy(MixEnergy mixEnergy);    
    public abstract MixEnergy MixEnergyService.updateMixEnergy(MixEnergy mixEnergy);    
}
