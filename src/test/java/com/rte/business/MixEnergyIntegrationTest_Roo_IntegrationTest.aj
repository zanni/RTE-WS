// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.rte.business;

import com.rte.business.MixEnergyDataOnDemand;
import com.rte.business.MixEnergyIntegrationTest;
import java.math.BigInteger;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

privileged aspect MixEnergyIntegrationTest_Roo_IntegrationTest {
    
    declare @type: MixEnergyIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: MixEnergyIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    @Autowired
    MixEnergyDataOnDemand MixEnergyIntegrationTest.dod;
    
    @Test
    public void MixEnergyIntegrationTest.testCountAllMixEnergys() {
        Assert.assertNotNull("Data on demand for 'MixEnergy' failed to initialize correctly", dod.getRandomMixEnergy());
        long count = mixEnergyService.countAllMixEnergys();
        Assert.assertTrue("Counter for 'MixEnergy' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void MixEnergyIntegrationTest.testFindMixEnergy() {
        MixEnergy obj = dod.getRandomMixEnergy();
        Assert.assertNotNull("Data on demand for 'MixEnergy' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MixEnergy' failed to provide an identifier", id);
        obj = mixEnergyService.findMixEnergy(id);
        Assert.assertNotNull("Find method for 'MixEnergy' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'MixEnergy' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void MixEnergyIntegrationTest.testFindMixEnergyEntries() {
        Assert.assertNotNull("Data on demand for 'MixEnergy' failed to initialize correctly", dod.getRandomMixEnergy());
        long count = mixEnergyService.countAllMixEnergys();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<MixEnergy> result = mixEnergyService.findMixEnergyEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'MixEnergy' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'MixEnergy' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void MixEnergyIntegrationTest.testSaveMixEnergy() {
        Assert.assertNotNull("Data on demand for 'MixEnergy' failed to initialize correctly", dod.getRandomMixEnergy());
        MixEnergy obj = dod.getNewTransientMixEnergy(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'MixEnergy' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'MixEnergy' identifier to be null", obj.getId());
        mixEnergyService.saveMixEnergy(obj);
        Assert.assertNotNull("Expected 'MixEnergy' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void MixEnergyIntegrationTest.testDeleteMixEnergy() {
        MixEnergy obj = dod.getRandomMixEnergy();
        Assert.assertNotNull("Data on demand for 'MixEnergy' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MixEnergy' failed to provide an identifier", id);
        obj = mixEnergyService.findMixEnergy(id);
        mixEnergyService.deleteMixEnergy(obj);
        Assert.assertNull("Failed to remove 'MixEnergy' with identifier '" + id + "'", mixEnergyService.findMixEnergy(id));
    }
    
}
