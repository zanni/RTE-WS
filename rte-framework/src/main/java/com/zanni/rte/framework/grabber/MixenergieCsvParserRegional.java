package com.zanni.rte.framework.grabber;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.googlecode.jcsv.reader.CSVEntryParser;
import com.zanni.rte.framework.MixEnergyRegional;
import com.zanni.rte.framework.utils.RegionEnum;

public class MixenergieCsvParserRegional implements
		CSVEntryParser<MixEnergyRegional> {

	private static String FILE_DATE_FORMAT = "dd/MM/yyyy";
	/**
	 * flag to check if it is the first line of file => get date of file
	 */
	private boolean isFirstLine = true;

	private RegionEnum region;

	/**
	 * feild intended to share file's date through different records
	 */
	private Date _fileDate;

	public MixenergieCsvParserRegional(RegionEnum r) {
		region = r;
	}

	/**
	 * Secured String->Double convvertion method
	 * 
	 * @param value
	 * @return
	 */
	private Integer convert(String value) {
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * secured static array access + secured String Double conversion
	 * 
	 * @param array
	 * @param index
	 * @return
	 */
	private Integer getArrayEntry(String[] array, int index) {
		try {
			if (array.length > index) {
				Integer convert = convert(array[index]);
				return convert;
			}
		} catch (Exception e) {
			return null;
		}

		return null;
	}

	@Override
	public MixEnergyRegional parseEntry(String... data) {
		SimpleDateFormat format = new SimpleDateFormat(
				MixenergieCsvParserRegional.FILE_DATE_FORMAT);
		String[] arg0 = data[0].split("\\t");
		
		// the first line contain the date (year month day) of the file
		if (isFirstLine()) {
			String line = arg0[0];
			try {
				this.setFileDate(format.parse(line.split(" ")[2]));
				this.setFirstLine(false);

			} catch (ParseException e) {
				// if the date of record can't be parsed from file, we stop
				// import
				return null;
			}
		}
		
		boolean has_data = false;
		for(String str : arg0){
			Integer convert = convert(str);
			if(convert != null) has_data=true;
		}
		if(!has_data){
			return new MixEnergyRegional();
		}

		format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		MixEnergyRegional record = new MixEnergyRegional();
		String heure = arg0[0];
		Calendar cal = Calendar.getInstance();
		// file date is injected to each record
		cal.setTime(this.getFileDate());
		// month need to be retreated as cal.get(Calendar.MONTH) return 0 -> 11
		// and we expect 01->12
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		if (month.length() == 1) {
			month = "0" + month;
			
		}
		String record_log_date = String.valueOf(cal.get(Calendar.YEAR)) + "-"
				+ month + "-" + String.valueOf(cal.get(Calendar.DAY_OF_MONTH))
				+ " " + heure;

		try {
			record.setLogDate(format.parse(record_log_date));
			record.setRegion(region);
			int i = 1;
			record.setConsommation(getArrayEntry(arg0, i++));
			record.setThermique(getArrayEntry(arg0, i++));
			record.setNucleaire(getArrayEntry(arg0, i++));
			record.setEolien(getArrayEntry(arg0, i++));
			record.setSolaire(getArrayEntry(arg0, i++));
			record.setHydraulique(getArrayEntry(arg0, i++));
			record.setPompage(getArrayEntry(arg0, i++));
			record.setEnr_thermique(getArrayEntry(arg0, i++));
			record.setEch_physiques(getArrayEntry(arg0, i++));
			record.setInit(true);
			return record;
		} catch (Exception e) {
			// we must return an empty record while returning null seem to break
			// parser
			return new MixEnergyRegional();
		}
	}

	/**
	 * @return the fileDate
	 */
	public Date getFileDate() {
		return _fileDate;
	}

	/**
	 * @param fileDate
	 *            the fileDate to set
	 */
	public void setFileDate(Date fileDate) {
		_fileDate = fileDate;
	}

	/**
	 * @return the isFirstLine
	 */
	public boolean isFirstLine() {
		return isFirstLine;
	}

	/**
	 * @param isFirstLine
	 *            the isFirstLine to set
	 */
	public void setFirstLine(boolean isFirstLine) {
		this.isFirstLine = isFirstLine;
	}

}
