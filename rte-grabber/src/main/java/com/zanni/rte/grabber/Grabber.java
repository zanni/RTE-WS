package com.zanni.rte.grabber;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Grabber {
	public static String[] ARGS; 

	public static void main(String[] args) {
		System.out.print(args);
		ARGS = args;

		PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
	     Properties properties = new Properties();
	     properties.setProperty("MONGO_PORT", "27017");
	     properties.setProperty("MONGO_DBNAME", "db");
	     properties.setProperty("MONGO_HOST", "localhost");
	     properties.setProperty("MONGO_USERNAME", "");
	     properties.setProperty("MONGO_PASS", "");
	     configurer.setProperties(properties);
	     
	     ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
	     context.addBeanFactoryPostProcessor(configurer);
	     context.setConfigLocations(new String[] {
					"classpath*:META-INF/spring/applicationContext.xml",
					"classpath*:META-INF/spring/applicationContext-mongo.xml" });
	     context.refresh();

		System.out.print(context.getEnvironment().getProperty("MONGO_PORT"));
		RteGrabberService grabber = context.getBean(RteGrabberService.class);

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
				start.add(Calendar.DATE, 1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
