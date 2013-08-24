package com.rte.grabber;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Grabber {
	private static ApplicationContext context;

	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext(new String[] {
				"META-INF/spring/applicationContext.xml",
				"META-INF/spring/applicationContext-mongo.xml" });

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
