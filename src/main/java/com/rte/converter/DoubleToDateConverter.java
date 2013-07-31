package com.rte.converter;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;


public class DoubleToDateConverter implements Converter<Double, Date>{

	@Override
	public Date convert(Double source) {
		if(source == null) return null;
		Date date = new Date();
		date.setTime(source.longValue());
		return date;
	}

}
