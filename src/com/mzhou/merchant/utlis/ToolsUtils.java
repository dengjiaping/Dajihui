package com.mzhou.merchant.utlis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.Time;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * Author: Mzhou Date:2014-4-28ÏÂÎç6:26:21</br> Desc:</br>
 * 
 */
public class ToolsUtils {
	/**
	 * update the textView Value
	 * 
	 * @param tv
	 * @param year
	 * @param month
	 * @param day
	 */
 
	@SuppressLint("SimpleDateFormat")
	public static void updateDateDisplay(TextView tv, Time time) {
		StringBuilder builder = new StringBuilder();
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 String string = simpleDateFormat.format(new Date(time.toMillis(true)));
		 builder.append(string);
		tv.setText(builder+"");
	}
	@SuppressLint("SimpleDateFormat")
	public static void updateDateDisplay(TextView tv, Time time, int color) {
		SpannableStringBuilder builder = new SpannableStringBuilder();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String string = simpleDateFormat.format(new Date(time.toMillis(true)));
		builder.append(string);
		builder.setSpan(new ForegroundColorSpan(color), 0, string.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		tv.setText(builder);
	}


	/**
	 * add the number 0
	 * 
	 * @param c
	 * @return
	 */
	public static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	/**
	 * add the number of the month
	 * 
	 * @param c
	 * @return
	 */
	public static String pad1(int c) {
		if (c >= 10)
			return String.valueOf(c + 1);
		else
			return "0" + String.valueOf(c + 1);
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String TimeStamp2Date(String timestampString) {
		if (timestampString != null && !timestampString.equals("")&& !timestampString.equals("0")) {
			Long timestamp = Long.parseLong(timestampString);
			String date = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
					.format(new java.util.Date(timestamp));

			return date.substring(0, 10);
		}
		return "";

	}
	@SuppressLint("SimpleDateFormat")
	public static String TimeStamp2Date(long timestamp) {
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
				.format(new java.util.Date(timestamp));
		return date.substring(0, 10);

	}
	@SuppressLint("SimpleDateFormat")
	public static long Date2TimeStamp(String time) {
		long timeStart;
		try {
			if (time.indexOf(":") == -1) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				timeStart = sdf.parse(time).getTime();
				return timeStart;
			} else {
				return System.currentTimeMillis();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return System.currentTimeMillis();
	}
}
