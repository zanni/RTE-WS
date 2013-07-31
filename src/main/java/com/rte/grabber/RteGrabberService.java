package com.rte.grabber;

import java.util.Date;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

/**
 * Interface around RteGrabberService, intended to retreive Rte archive 
 * from RTE website:
 * 		http://www.rte-france.com/curves/eco2mixD?date=05/11/2012
 * 
 * @author bzanni
 *
 */
@Service
@DependsOn("RteWSConfigurationBean")
public interface RteGrabberService {
		
	public void retreiveLastMixEnergie() throws Exception;
	
	public void retreiveAllMixenergieOfDate(Date date) throws Exception;	
	
}
