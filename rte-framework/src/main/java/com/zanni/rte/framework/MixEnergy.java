package com.zanni.rte.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.AbstractTransformer;

@RooJavaBean
@RooToString
@RooMongoEntity
@RooJson
public class MixEnergy {
	
	@Transient
	private Boolean init = false;
	
	@DateTimeFormat(iso=ISO.DATE_TIME)
	@Indexed
	private Date logDate;
	
	private Integer consommation;
	
	private Integer previsionj1;

	private Integer previsionj;
	
	private Integer fioul;
	
	private Integer charbon;
	
	private Integer gaz;
	
	private Integer nucleaire;
	
	private Integer eolien;
	
	private Integer solaire;
	
	private Integer hydraulique;

	private Integer pompage;
	
	private Integer enr_thermique;
	
	private Integer ech_physiques;
	
	private Integer taux_co2;
	
	private Integer ech_comm_allemagne;

	private Integer ech_comm_angleterre;

	private Integer ech_comm_belgique;

	private Integer ech_comm_espagne;

	private Integer ech_comm_italie;

	private Integer ech_comm_suisse;
	
	private Integer fioul_tac;
	
	private Integer fioul_cogen;
		
	private Integer fioul_autre;
	
	private Integer gaz_tac;
	
	private Integer gaz_cogen;
	
	private Integer gaz_ccg;
	
	private Integer gaz_autre;
	
	private Integer hydraulique_eau;
	
	private Integer hydraulique_lac;
	
	private Integer hydraulique_step;
	
	private Integer enr_thermique_dechet;
	
	private Integer enr_thermique_biomasse;
	
	private Integer enr_thermique_biogaz;
	

	private static class ExcludeTransformer extends AbstractTransformer {
		@Override
		public Boolean isInline() {
			return true;
		}

		@Override
		public void transform(Object object) {
			// Do nothing, null objects are not serialized.
			return;
		}
	}

	
	
	public String toJson() {
		return new JSONSerializer()
				.transform(new ExcludeTransformer(), void.class)
				.exclude("*.class").exclude("id").exclude("init").serialize(this);
	}

	public static MixEnergy fromJsonToMixEnergy(String json) {
		return new JSONDeserializer<MixEnergy>().use(null, MixEnergy.class)
				.deserialize(json);
	}

	public static String toJsonArray(Collection<MixEnergy> collection) {
		return new JSONSerializer()
				.transform(new ExcludeTransformer(), void.class)
				.exclude("*.class").exclude("id").exclude("init").serialize(collection);
	}

	public static Collection<MixEnergy> fromJsonArrayToMixEnergys(String json) {
		return new JSONDeserializer<List<MixEnergy>>()
				.use(null, ArrayList.class).use("values", MixEnergy.class)
				.deserialize(json);
	}
}
