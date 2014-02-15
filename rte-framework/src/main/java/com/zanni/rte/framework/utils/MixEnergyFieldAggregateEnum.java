package com.zanni.rte.framework.utils;

public enum MixEnergyFieldAggregateEnum implements FieldAggregateEnum{

	CONSOMMATION("consommation", ArrayAggregationEnum.SUM)
	, PREVISIONJ1("previsionj1", ArrayAggregationEnum.SUM)
	, PREVISIONJ("previsionj", ArrayAggregationEnum.SUM)
	, FIOUL("fioul", ArrayAggregationEnum.SUM)
	, CHARBON("charbon", ArrayAggregationEnum.SUM)
	, GAZ("gaz", ArrayAggregationEnum.SUM)
	, NUCLEAIRE("nucleaire", ArrayAggregationEnum.SUM)
	, EOLIEN("eolien", ArrayAggregationEnum.SUM)
	, SOLAIRE("solaire", ArrayAggregationEnum.SUM)
	, HYDRAULIQUE("hydraulique", ArrayAggregationEnum.SUM)
	, POMPAGE("pompage", ArrayAggregationEnum.SUM)
	, ENR_THERMIQUE("enr_thermique", ArrayAggregationEnum.SUM)
	, ECH_PHYSIQUES("ech_physiques", ArrayAggregationEnum.SUM)
	, TAUX_CO2("taux_co2", ArrayAggregationEnum.MEAN)
	, ECH_COMM_ALLEMAGNE("ech_comm_allemagne", ArrayAggregationEnum.SUM)
	, ECH_COMM_ANGLETERRE("ech_comm_angleterre", ArrayAggregationEnum.SUM)
	, ECH_COMM_BELGIQUE("ech_comm_belgique", ArrayAggregationEnum.SUM)
	, ECH_COMM_ESPAGNE("ech_comm_espagne", ArrayAggregationEnum.SUM)
	, ECH_COMM_ITALIE("ech_comm_italie", ArrayAggregationEnum.SUM)
	, ECH_COMM_SUISSE("ech_comm_suisse", ArrayAggregationEnum.SUM)
	, FIOUL_TAC("fioul_tac", ArrayAggregationEnum.SUM)
	, FIOUL_COGEN("fioul_cogen", ArrayAggregationEnum.SUM)
	, FIOUL_AUTRE("fioul_autre", ArrayAggregationEnum.SUM)
	, GAZ_TAC("gaz_tac", ArrayAggregationEnum.SUM)
	, GAZ_COGEN("gaz_cogen", ArrayAggregationEnum.SUM)
	, GAZ_CCG("gaz_ccg", ArrayAggregationEnum.SUM)
	, GAZ_AUTRE("gaz_autre", ArrayAggregationEnum.SUM)
	, HYDRAULIQUE_EAU("hydraulique_eau", ArrayAggregationEnum.SUM)
	, HYDRAULIQUE_LAC("hydraulique_lac", ArrayAggregationEnum.SUM)
	, HYDRAULIQUE_STEP("hydraulique_step", ArrayAggregationEnum.SUM)
	, ENR_THERMIQUE_DECHET("enr_thermique_dechet", ArrayAggregationEnum.SUM)
	, ENR_THERMIQUE_BIOMASSE("enr_thermique_biomasse", ArrayAggregationEnum.SUM)
	, ENR_THERMIQUE_BIOGAZ("enr_thermique_biogaz", ArrayAggregationEnum.SUM);	

	private String abbr;
	private ArrayAggregationEnum aggregate;

	MixEnergyFieldAggregateEnum(String val, ArrayAggregationEnum agg) {
		setAbbr(val);
		setAggregate(agg);
	}

	public static MixEnergyFieldAggregateEnum fromAbbr(String abbr) {
		for (MixEnergyFieldAggregateEnum agg : MixEnergyFieldAggregateEnum.values()) {
			if (abbr.equalsIgnoreCase(agg.getAbbr()))
				return agg;
		}
		return null;
	}
	
	public static String getAll(){
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for(MixEnergyFieldAggregateEnum field : MixEnergyFieldAggregateEnum.values()){
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
