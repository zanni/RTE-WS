package com.zanni.rte.framework.grabber;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.zanni.rte.framework.MixEnergy;
import com.zanni.rte.framework.conf.RteWSConfigurationBean;
import com.zanni.rte.framework.service.MixEnergyService;

@Service
public class RteGrabberServiceImpl implements RteGrabberService {

	private static Logger _logger = LoggerFactory
			.getLogger(RteGrabberService.class);
	@Autowired
	private RteWSConfigurationBean _rteConfigurationBean;

	@Resource
	private MixEnergyService _mixEnergyService;

	@Override
	public void retreiveLastMixEnergie() {
		retreiveAllMixenergieOfDate(new Date());
	}

	@Override
	public void retreiveAllMixenergieOfDate(Date date) {
		// _logger.info("Start importing RTE Data of " + date);

		// need to add today date with correct format to service url to retreive
		// today's archive
		SimpleDateFormat format = new SimpleDateFormat(
				_rteConfigurationBean.getDateArgFormat());

		String arg = format.format(date);
		String url = _rteConfigurationBean.getServiceUrl() + arg;

		int updated = 0;
		int created = 0;
		try {
			// download archive from RTE website
			URL website = new URL(url);

			ZipInputStream zis = new ZipInputStream(website.openStream());
			ZipEntry zipEntry = null;
			zipEntry = zis.getNextEntry();
			ByteArrayOutputStream streamBuilder = new ByteArrayOutputStream();
			while (zipEntry != null) {

				IOUtils.copy(zis, streamBuilder);

				Reader reader = new InputStreamReader(new ByteArrayInputStream(
						streamBuilder.toByteArray()));

				CSVReader<MixEnergy> csvParser = new CSVReaderBuilder<MixEnergy>(
						reader).entryParser(new MixenergieCsvParser()).build();

				Iterator<MixEnergy> it = csvParser.iterator();
				while (it.hasNext()) {
					MixEnergy record = it.next();

					if (!record.getInit())
						continue;

					// test if record is empty or not
					if (record.getLogDate() != null) {

						MixEnergy existing = _mixEnergyService
								.findByLogDate(record.getLogDate());
						if (existing == null) {
							// create imported record
							_mixEnergyService.saveMixEnergy(record);
							created++;
						} else {
							// merge imported record with previous saved one
							// existing.merge(record);
							_mixEnergyService.deleteMixEnergy(existing);
							_mixEnergyService.saveMixEnergy(record);
							updated++;
						}
					}
				}
				zis.closeEntry();

				zipEntry = zis.getNextEntry();

				int total = created + updated;
				_logger.info("SUCCESS - FRANCE - " + date + "cr:" + created
						+ " , up:" + updated + " , to:" + total);
			}

		} catch (IOException e) {
			_logger.error("RTE Data could not be grabbed");
			e.printStackTrace();
		}
	}
}
