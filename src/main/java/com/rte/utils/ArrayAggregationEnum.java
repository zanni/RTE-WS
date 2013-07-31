package com.rte.utils;

public enum ArrayAggregationEnum {
	SUM ("sum")
	, MEAN ("mean");
	
	private String abbr = "";
	
	ArrayAggregationEnum(String val){
		setAbbr(val);
	}
	
	public static ArrayAggregationEnum fromAbbr(String abbr){
		for(ArrayAggregationEnum agg : ArrayAggregationEnum.values()){
			if(abbr.equalsIgnoreCase(agg.getAbbr())) return agg;
		}
		return null;
	}

	/**
	 * @return the abbr
	 */
	public String getAbbr() {
		return abbr;
	}

	/**
	 * @param abbr the abbr to set
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
}
