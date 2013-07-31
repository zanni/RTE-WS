package com.rte.converter;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateToDoubleConverter implements Converter<Date, Double>{

	@Override
	public Double convert(Date source) {
		return new Long(source.getTime()).doubleValue();
	}

}
