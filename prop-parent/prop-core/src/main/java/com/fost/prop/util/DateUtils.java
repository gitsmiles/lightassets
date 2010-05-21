package com.fost.prop.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

	public static String getToday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		date = cal.getTime();
		String time = df.format(date);
		return time;
	}

	public static String getYesterDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		date = cal.getTime();
		String time = df.format(date);
		return time;
	}

	public static int[] getBetweenDay(Date start, Date end) {

		try {
			Calendar now = Calendar.getInstance();
			now.setTime(end);
			Calendar begin = Calendar.getInstance();
			begin.setTime(start);
			if (now.before(begin)) {
				now.setTime(start);
				begin.setTime(end);
			}
			long gap = now.getTimeInMillis() - begin.getTimeInMillis();
			int d = (int) (gap / (24 * 60 * 60 * 1000));
			int h = (int) ((gap % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000));
			int m = (int) ((gap % (60 * 60 * 1000)) / (60 * 1000));
			int s = (int) ((gap % (60 * 1000)) / 1000);
			return new int[] { d, h, m, s };
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		return new int[] { 0, 0, 0, 0 };
	}

	public static int[] getBetweenDay(Date start, Date end, int days) {

		try {
			Calendar now = Calendar.getInstance();
			now.setTime(end);
			Calendar begin = Calendar.getInstance();
			begin.setTime(start);
			if (now.before(begin)) {
				now.setTime(start);
				begin.setTime(end);
			}
			long gap = now.getTimeInMillis() - begin.getTimeInMillis();
			long limits = days * 24 * 60 * 60 * 1000L;
			if (limits > gap) {
				gap = limits - gap;
				int d = (int) (gap / (24 * 60 * 60 * 1000L));
				int h = (int) ((gap % (24 * 60 * 60 * 1000L)) / (60 * 60 * 1000L));
				int m = (int) ((gap % (60 * 60 * 1000L)) / (60 * 1000L));
				int s = (int) ((gap % (60 * 1000L)) / 1000L);
				return new int[] { d, h, m, s };
			}
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		return new int[] { -1, -1, -1, -1 };
	}

	public static long costTime(Date start, Date end) {
		Calendar now = Calendar.getInstance();
		now.setTime(end);
		Calendar begin = Calendar.getInstance();
		begin.setTime(start);
		return now.getTimeInMillis() - begin.getTimeInMillis();
	}

	public static Date getExpiredDate(Date start, int limitedDay) {
		Calendar begin = Calendar.getInstance();
		begin.setTime(start);
		begin.add(Calendar.DAY_OF_YEAR, limitedDay);
		return begin.getTime();
	}
}
