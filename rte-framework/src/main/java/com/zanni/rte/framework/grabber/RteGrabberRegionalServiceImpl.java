package com.zanni.rte.framework.grabber;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.zanni.rte.framework.MixEnergyRegional;
import com.zanni.rte.framework.conf.RteWSConfigurationBean;
import com.zanni.rte.framework.service.MixEnergyRegionalService;
import com.zanni.rte.framework.utils.RegionEnum;

@Service
public class RteGrabberRegionalServiceImpl implements RteGrabberRegionalService {

	private static Logger _logger = LoggerFactory
			.getLogger(RteGrabberRegionalService.class);
	@Autowired
	private RteWSConfigurationBean _rteConfigurationBean;

	@Resource
	private MixEnergyRegionalService _mixEnergyService;

	@Override
	public void retreiveLastMixEnergie() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void retreiveAllMixenergieOfDate(Date date, RegionEnum region)
			throws Exception {
		// _logger.info("Start importing RTE Data of " + date);

		// need to add today date with correct format to service url to retreive
		// today's archive
		SimpleDateFormat format = new SimpleDateFormat(
				_rteConfigurationBean.getDateArgFormat());

		String arg = format.format(date);
		String url = _rteConfigurationBean.getServiceUrl() + arg;
		url += "&region=" + region.getCode();

		int updated = 0;
		int created = 0;
		try {
			// download archive from RTE website
			URL website = new URL(url);

			ZipArchiveInputStream zis = new ZipArchiveInputStream(
					website.openStream());

			ZipArchiveEntry zipEntry = null;

			zipEntry = zis.getNextZipEntry();
			ByteArrayOutputStream streamBuilder = new ByteArrayOutputStream();
			while (zipEntry != null) {

				IOUtils.copy(zis, streamBuilder);

				Reader reader = new InputStreamReader(new ByteArrayInputStream(
						streamBuilder.toByteArray()));

				CSVReader<MixEnergyRegional> csvParser = new CSVReaderBuilder<MixEnergyRegional>(
						reader).entryParser(
						new MixenergieCsvParserRegional(region)).build();

				Iterator<MixEnergyRegional> it = csvParser.iterator();
				while (it.hasNext()) {
					MixEnergyRegional record = it.next();

					if (!record.getInit())
						continue;
					// test if record is empty or not
					if (record.getLogDate() != null) {

						MixEnergyRegional existing = _mixEnergyService
								.findByLogDateAndRegion(record.getLogDate(),
										region);
						if (existing == null) {
							// create imported record
							_mixEnergyService.saveMixEnergyRegional(record);
							created++;
						} else {
							// merge imported record with previous saved one
							// existing.merge(record);
							_mixEnergyService.updateMixEnergyRegional(record);
							updated++;

						}
					}
				}
//				zis.close();

				zipEntry = zis.getNextZipEntry();
			}
			int total = created + updated;
			_logger.info(region.getCode() + " - " + date + "cr:" + created
					+ " , up:" + updated + " , to:" + total);

		} catch (Exception e) {
			_logger.error(region.getCode(), e);

		}

	}

	@Override
	public void retreiveAllMixenergieOfDate(Date date) throws Exception {
		for (RegionEnum region : RegionEnum.values()) {
			this.retreiveAllMixenergieOfDate(date, region);
		}

	}
}
