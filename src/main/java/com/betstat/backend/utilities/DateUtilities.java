package com.betstat.backend.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DateUtilities {

	final static Logger logger = LogManager.getLogger(DateUtilities.class);

	public static Date getDate_EU_TimeFromString(String dateTimeString) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = null;
		try {
			date = simpleDateFormat.parse(dateTimeString);
		} catch (ParseException parseException) {
			logger.error(parseException);
		}
		return date;
	}

	public static Date getDate_USA_TimeFromString(String dateTimeString) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/mm/dd HH:mm");
		Date date = null;
		try {
			date = simpleDateFormat.parse(dateTimeString);
		} catch (ParseException parseException) {
			logger.error(parseException);
		}
		return date;
	}

	public static Date convertDataEUtoUSA(Date dataEU) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		try {
			String date = simpleDateFormat.format(dataEU);
			return simpleDateFormat.parse(date);
		} catch (ParseException parseException) {
			logger.error(parseException);
			return null;
		}
	}

	public static Date convertDataUSAtoEU(Date dataUSA) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			String date = simpleDateFormat.format(dataUSA);
			return simpleDateFormat.parse(date);
		} catch (ParseException parseException) {
			logger.error(parseException);
			return null;
		}
	}

	public static Date elaborateDate(String dateG) {
		// rimuove gli spazi bianchi
		String dateNoSpace = dateG.replaceAll("\\s+", "");
		// al posto della virgola mette uno spazio
		String dateNoComma = dateNoSpace.replaceAll(",", " ");
		return getDate_USA_TimeFromString(dateNoComma);
	}

}
