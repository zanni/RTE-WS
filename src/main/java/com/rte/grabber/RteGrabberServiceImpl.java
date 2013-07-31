package com.rte.grabber;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.rte.business.MixEnergy;
import com.rte.business.service.MixEnergyService;
import com.rte.conf.RteWSConfigurationBean;
import com.rte.utils.ZipUtils;

@Service
public class RteGrabberServiceImpl implements RteGrabberService {

	private static Logger _logger = LoggerFactory
			.getLogger(RteGrabberService.class);
	@Autowired
	private RteWSConfigurationBean _rteConfigurationBean;

	@Resource
	private MixEnergyService _mixEnergyService;

	private File downloadFile(String url, String filename) throws IOException {
		URL website = new URL(url);
		File file = new File(filename);
		FileUtils.copyURLToFile(website, file);
		return file;
	}

	@Override
	public void retreiveLastMixEnergie() {
		retreiveAllMixenergieOfDate(new Date());
	}

	@Override
	public void retreiveAllMixenergieOfDate(Date date)
			 {
		_logger.info("Start importing RTE Data of "+date);
		File folder = new File(_rteConfigurationBean.getTempLocalFolder());
		if (!folder.exists()) {
			folder.mkdir();
		}
		// need to add today date with correct format to service url to retreive
		// today's archive
		SimpleDateFormat format = new SimpleDateFormat(
				_rteConfigurationBean.getDateArgFormat());

		String arg = format.format(date);
		String url = _rteConfigurationBean.getServiceUrl() + arg;

		String filename = _rteConfigurationBean.getTempLocalFolder()
				+ "/imported_rte_" + arg.replace("/", "_");
		String archive = filename + "."
				+ _rteConfigurationBean.getArchiveExtension();
		format = new SimpleDateFormat(
				_rteConfigurationBean.getUnarchivedFileNameDateFormat());
		String unarchived = _rteConfigurationBean.getTempLocalFolder() + "/"
				+ _rteConfigurationBean.getUnarchivedFilename()
				+ format.format(date) + "."
				+ _rteConfigurationBean.getUnarchivedExtension();
		try {
			// download archive from RTE website
			File downloadFile = downloadFile(url, archive);
			// unarchive previous download
			ZipUtils.unpackArchive(downloadFile,
					new File(_rteConfigurationBean.getTempLocalFolder()));
			// parse file
			Reader reader = new FileReader(unarchived);

			CSVReader<MixEnergy> csvParser = new CSVReaderBuilder<MixEnergy>(
					reader).entryParser(new MixenergieCsvParser()).build();

			Iterator<MixEnergy> it = csvParser.iterator();
			while (it.hasNext()) {
				MixEnergy record = it.next();
				// test if record is empty or not
				if (record.getLogDate() != null) {

					MixEnergy existing = _mixEnergyService
							.findByLogDate(record.getLogDate());
					if (existing == null) {
						// create imported record
						_mixEnergyService.saveMixEnergy(record);
					} else {
						// merge imported record with previous saved one
//						existing.merge(record);
						_mixEnergyService.updateMixEnergy(record);

					}
				}
			}

			// delete both archive and unarchived data
			downloadFile.delete();
			File file = new File(unarchived);
			file.delete();

			_logger.info("RTE Data were successfully grabbed");

		} catch (IOException e) {
			_logger.error("RTE Data could not be grabbed");
			e.printStackTrace();
		}
	}
}
