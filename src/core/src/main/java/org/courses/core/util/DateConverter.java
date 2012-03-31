package org.courses.core.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.swt.widgets.DateTime;



public class DateConverter
{
	public static Date swtDateTimeToDate(DateTime dt)
	{
		Calendar cl = new GregorianCalendar();
		cl.set(dt.getYear(), dt.getMonth(), dt.getDay(), 0, 0);
		return cl.getTime();
	}
}
