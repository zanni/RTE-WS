package com.zanni.rte.framework.grabber;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.googlecode.jcsv.reader.CSVEntryParser;
import com.zanni.rte.framework.MixEnergy;
import com.zanni.rte.framework.MixEnergyRegional;

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
	private Integer convert(String value){
		try{
			return Integer.valueOf(value);
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
	private Integer getArrayEntry(String[] array, int index){
		try{
			if(array.length > index){
				return convert(array[index]);
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
		 String[] arg0 = data[0].split("\\t");
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
			
		 boolean has_data = false;
			for(String str : arg0){
				Integer convert = convert(str);
				if(convert != null) has_data=true;
			}
			if(!has_data){
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
			int i = 1;
			
			record.setConsommation(getArrayEntry(arg0, i++));
			record.setPrevisionj1(getArrayEntry(arg0, i++));
			record.setPrevisionj(getArrayEntry(arg0, i++));
			record.setFioul(getArrayEntry(arg0, i++));
			record.setCharbon(getArrayEntry(arg0, i++));
			record.setGaz(getArrayEntry(arg0, i++));
			record.setNucleaire(getArrayEntry(arg0, i++));
			record.setEolien(getArrayEntry(arg0, i++));
			record.setSolaire(getArrayEntry(arg0, i++));
			record.setHydraulique(getArrayEntry(arg0, i++));
			record.setPompage(getArrayEntry(arg0, i++));
			record.setEnr_thermique(getArrayEntry(arg0, i++));
			record.setEch_physiques(getArrayEntry(arg0, i++));
			record.setTaux_co2(getArrayEntry(arg0, i++));
			record.setEch_comm_allemagne(getArrayEntry(arg0, i++));
			record.setEch_comm_angleterre(getArrayEntry(arg0, i++));
			record.setEch_comm_belgique(getArrayEntry(arg0, i++));
			record.setEch_comm_espagne(getArrayEntry(arg0, i++));
			record.setEch_comm_italie(getArrayEntry(arg0, i++));
			record.setEch_comm_suisse(getArrayEntry(arg0, i++));
			record.setFioul_tac(getArrayEntry(arg0, i++));
			record.setFioul_cogen(getArrayEntry(arg0, i++));
			record.setFioul_autre(getArrayEntry(arg0, i++));
			record.setGaz_tac(getArrayEntry(arg0, i++));
			record.setGaz_cogen(getArrayEntry(arg0, i++));
			record.setGaz_ccg(getArrayEntry(arg0, i++));
			record.setGaz_autre(getArrayEntry(arg0, i++));
			record.setHydraulique_eau(getArrayEntry(arg0, i++));
			record.setHydraulique_lac(getArrayEntry(arg0, i++));
			record.setHydraulique_step(getArrayEntry(arg0, i++));
			record.setEnr_thermique_dechet(getArrayEntry(arg0, i++));
			record.setEnr_thermique_biomasse(getArrayEntry(arg0, i++));
			record.setEnr_thermique_biogaz(getArrayEntry(arg0, i++));
			record.setInit(true);
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
