package com.rte.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component("RteWSConfigurationBean")
public class RteWSConfigurationBean {
	private @Value("#{rteConfig['url_service']}")
	String _serviceUrl;
	private @Value("#{rteConfig['temp_local_folder']}")
	String _tempLocalFolder;
	private @Value("#{rteConfig['date_arg_format']}")
	String _dateArgFormat;
	private @Value("#{rteConfig['archive_extension']}")
	String _archiveExtension;
	private @Value("#{rteConfig['unarchived_extension']}")
	String _unarchivedExtension;
	private @Value("#{rteConfig['unarchived_filename']}")
	String _unarchivedFilename;
	private @Value("#{rteConfig['unarchived_filename_date_format']}")
	String _unarchivedFileNameDateFormat;
	private @Value("#{rteConfig['start_line']}")
	int _startLine;
	private @Value("#{rteConfig['end_line']}")
	int _endLine;

	public RteWSConfigurationBean() {
	}

	/**
	 * @return the serviceUrl
	 */
	public String getServiceUrl() {
		return _serviceUrl;
	}

	/**
	 * @param serviceUrl
	 *            the serviceUrl to set
	 */
	public void setServiceUrl(String serviceUrl) {
		_serviceUrl = serviceUrl;
	}

	/**
	 * @return the tempLocalFolder
	 */
	public String getTempLocalFolder() {
		return _tempLocalFolder;
	}

	/**
	 * @param tempLocalFolder
	 *            the tempLocalFolder to set
	 */
	public void setTempLocalFolder(String tempLocalFolder) {
		_tempLocalFolder = tempLocalFolder;
	}

	/**
	 * @return the dateArgFormat
	 */
	public String getDateArgFormat() {
		return _dateArgFormat;
	}

	/**
	 * @param dateArgFormat
	 *            the dateArgFormat to set
	 */
	public void setDateArgFormat(String dateArgFormat) {
		_dateArgFormat = dateArgFormat;
	}

	/**
	 * @return the archiveExtension
	 */
	public String getArchiveExtension() {
		return _archiveExtension;
	}

	/**
	 * @param archiveExtension
	 *            the archiveExtension to set
	 */
	public void setArchiveExtension(String archiveExtension) {
		_archiveExtension = archiveExtension;
	}

	/**
	 * @return the unarchivedExtension
	 */
	public String getUnarchivedExtension() {
		return _unarchivedExtension;
	}

	/**
	 * @param unarchivedExtension
	 *            the unarchivedExtension to set
	 */
	public void setUnarchivedExtension(String unarchivedExtension) {
		_unarchivedExtension = unarchivedExtension;
	}

	/**
	 * @return the unarchivedFilename
	 */
	public String getUnarchivedFilename() {
		return _unarchivedFilename;
	}

	/**
	 * @param unarchivedFilename
	 *            the unarchivedFilename to set
	 */
	public void setUnarchivedFilename(String unarchivedFilename) {
		_unarchivedFilename = unarchivedFilename;
	}

	/**
	 * @return the unarchivedFileNameDateFormat
	 */
	public String getUnarchivedFileNameDateFormat() {
		return _unarchivedFileNameDateFormat;
	}

	/**
	 * @param unarchivedFileNameDateFormat
	 *            the unarchivedFileNameDateFormat to set
	 */
	public void setUnarchivedFileNameDateFormat(
			String unarchivedFileNameDateFormat) {
		_unarchivedFileNameDateFormat = unarchivedFileNameDateFormat;
	}

	/**
	 * @return the startLine
	 */
	public int getStartLine() {
		return _startLine;
	}

	/**
	 * @param startLine
	 *            the startLine to set
	 */
	public void setStartLine(int startLine) {
		_startLine = startLine;
	}

	/**
	 * @return the endLine
	 */
	public int getEndLine() {
		return _endLine;
	}

	/**
	 * @param endLine
	 *            the endLine to set
	 */
	public void setEndLine(int endLine) {
		_endLine = endLine;
	}
}
