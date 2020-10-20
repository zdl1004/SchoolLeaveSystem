package pers.zdl1004.SchoolLeaveSystem.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateUtil {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat getDateFormatter() {
		return format;
	}
}
