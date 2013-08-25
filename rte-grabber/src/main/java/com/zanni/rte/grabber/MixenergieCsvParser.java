package com.zanni.rte.grabber;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.googlecode.jcsv.reader.CSVEntryParser;
import com.zanni.rte.framework.MixEnergy;

/**
 * 
 * MixenergieCsvParser
 * This class is intended to provide a csvparser converting a CSV file lines 
 * into Mixenergie objects
 * 
 * It's implements a class coming from com.googlecode.jcsv dependecy :
 * http://code.google.com/p/jcsv/
 * 
 * @author bzanni
 *
 */
public class MixenergieCsvParser implements CSVEntryParser<MixEnergy> {

	private static String FILE_DATE_FORMAT = "dd/MM/yyyy";
	/**
	 * flag to check if it is the first line of file
	 * => get date of file
	 */
	private boolean isFirstLine = true;
	
	/**
	 * feild intended to share file's date through different records
	 */
	private Date _fileDate;
	/**
	 * Secured String->Double convvertion method
	 * @param value
	 * @return
	 */
	private Double convertDouble(String value){
		try{
			return Double.valueOf(value);
		}
		catch(NumberFormatException e){
			return null;
		}
	}
	
	/**
	 * secured static array access + secured String Double conversion
	 * @param array
	 * @param index
	 * @return
	 */
	private Double getArrayEntry(String[] array, int index){
		try{
			if(array.length > index){
				return convertDouble(array[index]);
			}
		}
		catch(Exception e){
			return null;
		}
		
		return null;
	}
	@Override
	public MixEnergy parseEntry(String... data) {
		SimpleDateFormat format = new SimpleDateFormat(MixenergieCsvParser.FILE_DATE_FORMAT);
		 String[] arg0 = data[0].split("\t");
		 //the first line contain the date (year month day) of the file
		 if(isFirstLine()){
			 String line = arg0[0];
			 try {
				this.setFileDate(format.parse(line.split(" ")[2]));
				this.setFirstLine(false);
				
			} catch (ParseException e) {
				//if the date of record can't be parsed from file, we stop import
				return null;
			}
		 }
		
		//we check if there is more than 15 args because 
		//either record date is a full hour record (00:00, 01:00 ...) and we have access
		//to energy exchange between countries
		//either record date is not a full hour (00:15, 00:30) and we don't
		//so a "good" record is either 15 or 21 args  
		if(arg0.length < 15){
			//we must return an empty record while returning null seem to break parser
			return new MixEnergy();
		}
			
		
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		MixEnergy record = new MixEnergy();
		String heure = arg0[0];
		Calendar cal=Calendar.getInstance();
		//file date is injected to each record 
		cal.setTime(this.getFileDate());
		//month need to be retreated as cal.get(Calendar.MONTH) return 0 -> 11 and we expect 01->12
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1 );
		if(month.length() == 1){
			month = "0"+month;
		}
		String record_log_date = String.valueOf(cal.get(Calendar.YEAR))
								+"-"
								+month
								+"-"
								+String.valueOf(cal.get(Calendar.DAY_OF_MONTH))
								+" "
								+ heure;
		
		try {
			record.setLogDate(format.parse(record_log_date));
		
			record.setConsommation(getArrayEntry(arg0, 1));
			record.setPrevisionj1(getArrayEntry(arg0, 2));
			record.setPrevisionj(getArrayEntry(arg0, 3));
			record.setFioul(getArrayEntry(arg0, 4));
			record.setCharbon(getArrayEntry(arg0, 5));
			record.setGaz(getArrayEntry(arg0, 6));
			record.setNucleaire(getArrayEntry(arg0, 7));
			record.setEolien(getArrayEntry(arg0, 8));
			record.setSolaire(getArrayEntry(arg0, 9));
			record.setHydrolique(getArrayEntry(arg0, 10));
			record.setPompage(getArrayEntry(arg0, 11));
			record.setAutre(getArrayEntry(arg0, 12));
			record.setEch_physiques(getArrayEntry(arg0, 13));
			record.setTaux_co2(getArrayEntry(arg0, 14));
			record.setEch_comm_allemagne(getArrayEntry(arg0, 15));
			record.setEch_comm_angleterre(getArrayEntry(arg0, 16));
			record.setEch_comm_belgique(getArrayEntry(arg0, 17));
			record.setEch_comm_espagne(getArrayEntry(arg0, 18));
			record.setEch_comm_italie(getArrayEntry(arg0, 19));
			record.setEch_comm_suisse(getArrayEntry(arg0, 20));
			return record;
		} catch (Exception e) {
			//we must return an empty record while returning null seem to break parser
			return record;
		}
		
		
		
		
	}
	/**
	 * @return the fileDate
	 */
	public Date getFileDate() {
		return _fileDate;
	}
	/**
	 * @param fileDate the fileDate to set
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
	 * @param isFirstLine the isFirstLine to set
	 */
	public void setFirstLine(boolean isFirstLine) {
		this.isFirstLine = isFirstLine;
	}
	
	

}
