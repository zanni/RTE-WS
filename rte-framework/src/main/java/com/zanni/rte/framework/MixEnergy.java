package com.zanni.rte.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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

	private Double autre;

	private Double charbon;

	private Double consommation;

	private Double ech_comm_allemagne;

	private Double ech_comm_angleterre;

	private Double ech_comm_belgique;

	private Double ech_comm_espagne;

	private Double ech_comm_italie;

	private Double ech_comm_suisse;

	private Double ech_physiques;

	private Double eolien;

	private Double fioul;

	private Double gaz;

	private Double hydrolique;

	private Double nucleaire;

	private Double pompage;

	private Double previsionj1;

	private Double previsionj;

	private Double solaire;

	private Double taux_co2;
	
	@DateTimeFormat(iso=ISO.DATE_TIME)
	@Indexed
	private Date logDate;

	public Double getAutre() {
		return autre;
	}

	public void setAutre(Double autre) {
		this.autre = autre;
	}

	public Double getCharbon() {
		return charbon;
	}

	public void setCharbon(Double charbon) {
		this.charbon = charbon;
	}

	public Double getConsommation() {
		return consommation;
	}

	public void setConsommation(Double consommation) {
		this.consommation = consommation;
	}

	public Double getEch_comm_allemagne() {
		return ech_comm_allemagne;
	}

	public void setEch_comm_allemagne(Double ech_comm_allemagne) {
		this.ech_comm_allemagne = ech_comm_allemagne;
	}

	public Double getEch_comm_angleterre() {
		return ech_comm_angleterre;
	}

	public void setEch_comm_angleterre(Double ech_comm_angleterre) {
		this.ech_comm_angleterre = ech_comm_angleterre;
	}

	public Double getEch_comm_belgique() {
		return ech_comm_belgique;
	}

	public void setEch_comm_belgique(Double ech_comm_belgique) {
		this.ech_comm_belgique = ech_comm_belgique;
	}

	public Double getEch_comm_espagne() {
		return ech_comm_espagne;
	}

	public void setEch_comm_espagne(Double ech_comm_espagne) {
		this.ech_comm_espagne = ech_comm_espagne;
	}

	public Double getEch_comm_italie() {
		return ech_comm_italie;
	}

	public void setEch_comm_italie(Double ech_comm_italie) {
		this.ech_comm_italie = ech_comm_italie;
	}

	public Double getEch_comm_suisse() {
		return ech_comm_suisse;
	}

	public void setEch_comm_suisse(Double ech_comm_suisse) {
		this.ech_comm_suisse = ech_comm_suisse;
	}

	public Double getEch_physiques() {
		return ech_physiques;
	}

	public void setEch_physiques(Double ech_physiques) {
		this.ech_physiques = ech_physiques;
	}

	public Double getEolien() {
		return eolien;
	}

	public void setEolien(Double eolien) {
		this.eolien = eolien;
	}

	public Double getFioul() {
		return fioul;
	}

	public void setFioul(Double fioul) {
		this.fioul = fioul;
	}

	public Double getGaz() {
		return gaz;
	}

	public void setGaz(Double gaz) {
		this.gaz = gaz;
	}

	public Double getHydrolique() {
		return hydrolique;
	}

	public void setHydrolique(Double hydrolique) {
		this.hydrolique = hydrolique;
	}

	public Double getNucleaire() {
		return nucleaire;
	}

	public void setNucleaire(Double nucleaire) {
		this.nucleaire = nucleaire;
	}

	public Double getPompage() {
		return pompage;
	}

	public void setPompage(Double pompage) {
		this.pompage = pompage;
	}

	public Double getPrevisionj1() {
		return previsionj1;
	}

	public void setPrevisionj1(Double previsionj1) {
		this.previsionj1 = previsionj1;
	}

	public Double getPrevisionj() {
		return previsionj;
	}

	public void setPrevisionj(Double previsionj) {
		this.previsionj = previsionj;
	}

	public Double getSolaire() {
		return solaire;
	}

	public void setSolaire(Double solaire) {
		this.solaire = solaire;
	}

	public Double getTaux_co2() {
		return taux_co2;
	}

	public void setTaux_co2(Double taux_co2) {
		this.taux_co2 = taux_co2;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

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
				.exclude("*.class").exclude("id").serialize(this);
	}

	public static MixEnergy fromJsonToMixEnergy(String json) {
		return new JSONDeserializer<MixEnergy>().use(null, MixEnergy.class)
				.deserialize(json);
	}

	public static String toJsonArray(Collection<MixEnergy> collection) {
		return new JSONSerializer()
				.transform(new ExcludeTransformer(), void.class)
				.exclude("*.class").exclude("id").serialize(collection);
	}

	public static Collection<MixEnergy> fromJsonArrayToMixEnergys(String json) {
		return new JSONDeserializer<List<MixEnergy>>()
				.use(null, ArrayList.class).use("values", MixEnergy.class)
				.deserialize(json);
	}
}
