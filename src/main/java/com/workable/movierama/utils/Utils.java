package com.workable.movierama.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class Utils {
	
	public final static int getYear(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date parse;
		try {
			parse = sdf.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(parse);
			return c.get(Calendar.YEAR);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}

}
