package com.zanni.rte.framework.grabber;

import java.util.Date;

import com.zanni.rte.framework.utils.RegionEnum;

public interface RteGrabberRegionalService {
public void retreiveLastMixEnergie() throws Exception;
	
	public void retreiveAllMixenergieOfDate(Date date) throws Exception;	

	public void retreiveAllMixenergieOfDate(Date date, RegionEnum region) throws Exception;	

}
