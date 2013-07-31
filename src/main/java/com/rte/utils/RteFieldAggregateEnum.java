package com.rte.utils;

public enum RteFieldAggregateEnum {
	AUTRE("autre", ArrayAggregationEnum.SUM)
	, CHARBON("charbon", ArrayAggregationEnum.SUM)
	, CONSOMMATION("consommation", ArrayAggregationEnum.SUM)
	, ECH_COMM_ALLEMAGNE("ech_comm_allemagne", ArrayAggregationEnum.SUM)
	, ECH_COMM_ANGLETERRE("ech_comm_angleterre", ArrayAggregationEnum.SUM)
	, ECH_COMM_BELGIQUE("ech_comm_belgique", ArrayAggregationEnum.SUM)
	, ECH_COMM_ESPAGNE("ech_comm_espagne", ArrayAggregationEnum.SUM)
	, ECH_COMM_ITALIE("ech_comm_italie", ArrayAggregationEnum.SUM)
	, ECH_COMM_SUISSE("ech_comm_suisse", ArrayAggregationEnum.SUM)
	, ECH_PHYSIQUES("ech_physiques", ArrayAggregationEnum.SUM)
	, EOLIEN("eolien", ArrayAggregationEnum.SUM)
	, FIOUL("fioul", ArrayAggregationEnum.SUM)
	, GAZ("gaz", ArrayAggregationEnum.SUM)
	, HYDROLIQUE("hydrolique", ArrayAggregationEnum.SUM)
	, NUCLEAIRE("nucleaire", ArrayAggregationEnum.SUM)
	, POMPAGE("pompage", ArrayAggregationEnum.SUM)
	, PREVISIONJ1("previsionj1", ArrayAggregationEnum.SUM)
	, PREVISIONJ("previsionj", ArrayAggregationEnum.SUM)
	, SOLAIRE("solaire", ArrayAggregationEnum.SUM)
	, TAUX_CO2("taux_co2", ArrayAggregationEnum.MEAN);

	private String abbr;
	private ArrayAggregationEnum aggregate;

	RteFieldAggregateEnum(String val, ArrayAggregationEnum agg) {
		setAbbr(val);
		setAggregate(agg);
	}

	public static RteFieldAggregateEnum fromAbbr(String abbr) {
		for (RteFieldAggregateEnum agg : RteFieldAggregateEnum.values()) {
			if (abbr.equalsIgnoreCase(agg.getAbbr()))
				return agg;
		}
		return null;
	}
	
	public static String getAll(){
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for(RteFieldAggregateEnum field : RteFieldAggregateEnum.values()){
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
