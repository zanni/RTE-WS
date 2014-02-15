package com.zanni.rte.framework.utils;

public enum MixEnergyRegionalFieldAggregateEnum  implements FieldAggregateEnum{

	CONSOMMATION("consommation", ArrayAggregationEnum.SUM)
	, PREVISIONJ1("previsionj1", ArrayAggregationEnum.SUM)
	, PREVISIONJ("previsionj", ArrayAggregationEnum.SUM)
	, THERMIQUE("thermique", ArrayAggregationEnum.SUM)
	, NUCLEAIRE("nucleaire", ArrayAggregationEnum.SUM)
	, EOLIEN("eolien", ArrayAggregationEnum.SUM)
	, SOLAIRE("solaire", ArrayAggregationEnum.SUM)
	, HYDRAULIQUE("hydraulique", ArrayAggregationEnum.SUM)
	, POMPAGE("pompage", ArrayAggregationEnum.SUM)
	, ENR_THERMIQUE("enr_thermique", ArrayAggregationEnum.SUM)
	, ECH_PHYSIQUES("ech_physiques", ArrayAggregationEnum.SUM);
	

	private String abbr;
	private ArrayAggregationEnum aggregate;

	MixEnergyRegionalFieldAggregateEnum(String val, ArrayAggregationEnum agg) {
		setAbbr(val);
		setAggregate(agg);
	}

	public static MixEnergyRegionalFieldAggregateEnum fromAbbr(String abbr) {
		for (MixEnergyRegionalFieldAggregateEnum agg : MixEnergyRegionalFieldAggregateEnum.values()) {
			if (abbr.equalsIgnoreCase(agg.getAbbr()))
				return agg;
		}
		return null;
	}
	
	public static String getAll(){
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for(MixEnergyRegionalFieldAggregateEnum field : MixEnergyRegionalFieldAggregateEnum.values()){
			if(first) first = false;
			else builder.append(",");
			builder.append(field.getAbbr());
		}
		return builder.toString();
	}

	/**
	 * @return the abbr
	 */
	public String getAbbr() {
		return abbr;
	}

	/**
	 * @param abbr
	 *            the abbr to set
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	/**
	 * @return the aggregate
	 */
	public ArrayAggregationEnum getAggregate() {
		return aggregate;
	}

	/**
	 * @param aggregate the aggregate to set
	 */
	public void setAggregate(ArrayAggregationEnum aggregate) {
		this.aggregate = aggregate;
	}

}
