package com.zanni.rte.grabber;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zanni.rte.framework.grabber.RteGrabberRegionalService;
import com.zanni.rte.framework.grabber.RteGrabberService;
import com.zanni.rte.framework.utils.RegionEnum;

public class Grabber {
	public static String[] ARGS;
	private static ClassPathXmlApplicationContext context;

	public static void main(String[] args) {

		PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
		Properties properties = new Properties();
		properties.setProperty("MONGO_PORT", System.getProperty("MONGO_PORT"));
		properties.setProperty("MONGO_DBNAME",
				System.getProperty("MONGO_DBNAME"));
		properties.setProperty("MONGO_HOST", System.getProperty("MONGO_HOST"));
		properties.setProperty("MONGO_USERNAME",
				System.getProperty("MONGO_USERNAME"));
		properties.setProperty("MONGO_PASS", System.getProperty("MONGO_PASS"));
		configurer.setProperties(properties);

		context = new ClassPathXmlApplicationContext();
		context.addBeanFactoryPostProcessor(configurer);
		context.setConfigLocations(new String[] {
				"classpath*:META-INF/spring/applicationContext.xml",
				"classpath*:META-INF/spring/applicationContext-mongo.xml" });
		context.refresh();

		RteGrabberService grabber = context.getBean(RteGrabberService.class);

		RteGrabberRegionalService regionalGrabber = context
				.getBean(RteGrabberRegionalService.class);

		if (args.length != 2) {
			return;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date startDate = format.parse(args[0]);
			Calendar start = new GregorianCalendar();
			start.setTime(startDate);

			Date endDate = format.parse(args[1]);
			Calendar end = new GregorianCalendar();
			end.setTime(endDate);
			while (start.getTime().before(end.getTime())) {
				grabber.retreiveAllMixenergieOfDate(start.getTime());
				regionalGrabber.retreiveAllMixenergieOfDate(start.getTime());
				start.add(Calendar.DATE, 1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
