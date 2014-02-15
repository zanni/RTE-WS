package com.zanni.rte.framework.utils;

public enum RegionEnum {
	ALSACE("ALS")
	, AQUITAINE("AQU")
	, AUVERGNE("AUV")
	, BASSE_NORMANDIE("BNO")
	, BOURGOGNE("BOU")
	, BRETAGNE("BRE")
	, CENTRE("CEN")
	, CHAMPAGNE_ARDENNE("CAR")
	, FRANCHE_COMTE("FCO")
	, HAUTE_NORMANDIE("HNO")
	, ILE_DE_FRANCE("IDF")
	, LANGUEDOC_ROUSILLON("LRO")
	, LIMOUSIN("LIM")
	, LORRAINE("LOR")
	, MIDI_PYRENEES("MPY")
	, NORD_PAS_DE_CALAIS("NPC")
	, PAYS_DE_LA_LOIRE("PLO")
	, PICARDIE("PIC")
	, POITOU_CHARENTES("PCH")
	, PACA("PAC")
	, RHONE_ALPES("RAL");
	
	
	private String code;
	
	private RegionEnum(String c){
		setCode(c);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public static RegionEnum fromCode(String str){
		for(RegionEnum r : RegionEnum.values()){
			if(r.getCode().equals(str)){
				return r;
			}
		}
		return null;
	}
}
