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

import com.zanni.rte.framework.utils.RegionEnum;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.AbstractTransformer;

@RooJavaBean
@RooToString
@RooMongoEntity
@RooJson
public class MixEnergyRegional {

	@Transient
	private Boolean init = false;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Indexed
	private Date logDate;

	private RegionEnum region;

	private Integer consommation;

	private Integer thermique;

	private Integer nucleaire;

	private Integer eolien;

	private Integer solaire;

	private Integer hydraulique;

	private Integer pompage;

	private Integer enr_thermique;

	private Integer ech_physiques;

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

	public static MixEnergyRegional fromJsonToMixEnergyRegional(String json) {
		return new JSONDeserializer<MixEnergyRegional>().use(null,
				MixEnergyRegional.class).deserialize(json);
	}

	public static String toJsonArray(Collection<MixEnergyRegional> collection) {
		return new JSONSerializer()
				.transform(new ExcludeTransformer(), void.class)
				.exclude("*.class").exclude("id").exclude("init").serialize(collection);
	}

	public static Collection<MixEnergyRegional> fromJsonArrayToMixEnergyRegionals(
			String json) {
		return new JSONDeserializer<List<MixEnergyRegional>>()
				.use(null, ArrayList.class)
				.use("values", MixEnergyRegional.class).deserialize(json);
	}

}
