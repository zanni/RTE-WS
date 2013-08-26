package com.zanni.rte.grabber.daily;

import java.util.Date;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zanni.rte.framework.grabber.RteGrabberService;

public class DailyGrabber {
	public static String[] ARGS;
	private static ClassPathXmlApplicationContext context; 

	public static void main(String[] args) {
		
		PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
	     Properties properties = new Properties();
	     properties.setProperty("MONGO_PORT", System.getProperty("MONGO_PORT"));
	     properties.setProperty("MONGO_DBNAME", System.getProperty("MONGO_DBNAME"));
	     properties.setProperty("MONGO_HOST", System.getProperty("MONGO_HOST"));
	     properties.setProperty("MONGO_USERNAME", System.getProperty("MONGO_USERNAME"));
	     properties.setProperty("MONGO_PASS", System.getProperty("MONGO_PASS"));
	     configurer.setProperties(properties);
	     
	     context = new ClassPathXmlApplicationContext();
	     context.addBeanFactoryPostProcessor(configurer);
	     context.setConfigLocations(new String[] {
					"classpath*:META-INF/spring/applicationContext.xml",
					"classpath*:META-INF/spring/applicationContext-mongo.xml" });
	     context.refresh();

		RteGrabberService grabber = context.getBean(RteGrabberService.class);

		try {
			grabber.retreiveAllMixenergieOfDate(new Date());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
