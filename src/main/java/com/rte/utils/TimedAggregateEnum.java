package com.rte.utils;

public enum TimedAggregateEnum {
	QUARTER ("quarter")
	, HOUR ("hour")
	, DAY ("day")
	, WEEK ("week")
	, MONTH ("month")
	, YEAR ("year");
	
	private String _abbr = "";
	
	TimedAggregateEnum(String abbr){
		setAbbr(abbr);
	}
	
	public static TimedAggregateEnum fromAbbr(String abbr){
		for(TimedAggregateEnum agg : TimedAggregateEnum.values()){
			if(abbr.equalsIgnoreCase(agg.getAbbr())) return agg;
		}
		return null;
	}

	/**
	 * @return the _abbr
	 */
	public String getAbbr() {
		return _abbr;
	}

	/**
	 * @param _abbr the _abbr to set
	 */
	public void setAbbr(String _abbr) {
		this._abbr = _abbr;
	}
	
	
}
