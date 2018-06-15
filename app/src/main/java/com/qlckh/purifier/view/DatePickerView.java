package com.qlckh.purifier.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.qlckh.purifier.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Andy
 * @date   2018/6/12 13:00
 * Desc:    日期选择器
 */
public class DatePickerView implements OnDateChangedListener,
        OnTimeChangedListener {
	private DatePicker datePicker;
	private AlertDialog ad;
	private String dateTime;
	private Activity activity;

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);


	/**
	 * 日期时间弹出选择框构造函数
	 *
	 * @param activity
	 *            ：调用的父activity
	 *            初始日期时间值，作为弹出窗口的标题和日期时间初始值
	 */
	public DatePickerView(Activity activity) {
		this.activity = activity;

	}


	public void init(DatePicker datePicker, String initDateTime) {
		Calendar calendar = Calendar.getInstance();
		if (!(null == initDateTime || "".equals(initDateTime))) {
			calendar = this.getCalendarByInintData(initDateTime);
		} else {
			initDateTime = calendar.get(Calendar.YEAR) + "年"+ calendar.get(Calendar.MONTH) + "月"+ calendar.get(Calendar.DAY_OF_MONTH) + "日 ";
		}

		datePicker.init(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), this);
	}

	/**
	 * 弹出日期时间选择框方法
	 *
	 * @param inputDate
	 *            :为需要设置的日期时间文本编辑框
	 */
	public void dateTimePicKDialog(final TextView inputDate,String initDateTime) {
		LinearLayout dateTimeLayout = (LinearLayout) activity
				.getLayoutInflater().inflate(R.layout.date_pick, null);
		datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datePicker);
		init(datePicker,initDateTime);
		ad = new AlertDialog.Builder(activity)
				.setTitle(initDateTime)
				.setView(dateTimeLayout)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						inputDate.setText(dateTime);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				}).show();

		onDateChanged(null, 0, 0, 0);
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		onDateChanged(null, 0, 0, 0);
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
							  int dayOfMonth) {
		// 获得日历实例
		Calendar calendar = Calendar.getInstance();

		calendar.set(datePicker.getYear(), datePicker.getMonth(),
				datePicker.getDayOfMonth());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		dateTime = sdf.format(calendar.getTime());
		ad.setTitle(dateTime);
	}

	/**
	 * 实现将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒,并赋值给calendar
	 *
	 * @param initDateTime
	 *            初始日期时间值 字符串型
	 * @return Calendar
	 */
	private Calendar getCalendarByInintData(String initDateTime) {
		Calendar calendar = Calendar.getInstance();

		// 将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒
		String date = spliteString(initDateTime, "日", "index", "front"); // 日期
		String time = spliteString(initDateTime, "日", "index", "back"); // 时间

		String yearStr = spliteString(date, "年", "index", "front"); // 年份
		String monthAndDay = spliteString(date, "年", "index", "back"); // 月日

		String monthStr = spliteString(monthAndDay, "月", "index", "front"); // 月
		String dayStr = spliteString(monthAndDay, "月", "index", "back"); // 日

		String hourStr = spliteString(time, ":", "index", "front"); // 时
		String minuteStr = spliteString(time, ":", "index", "back"); // 分

		int currentYear = Integer.valueOf(yearStr.trim());
		int currentMonth = Integer.valueOf(monthStr.trim()) - 1;
		int currentDay = Integer.valueOf(dayStr.trim());
		//int currentHour = Integer.valueOf(hourStr.trim()).intValue();
		//int currentMinute = Integer.valueOf(minuteStr.trim()).intValue();

		calendar.set(currentYear, currentMonth, currentDay);
		return calendar;
	}

	/**
	 * 截取子串
	 */
	private static String spliteString(String srcStr, String pattern,
									   String indexOrLast, String frontOrBack) {
		String result = "";
		int loc = -1;
		if ("index".equalsIgnoreCase(indexOrLast)) {
			// 取得字符串第一次出现的位置
			loc = srcStr.indexOf(pattern);
		} else {
			// 最后一个匹配串的位置
			loc = srcStr.lastIndexOf(pattern);
		}
		if ("front".equalsIgnoreCase(frontOrBack)) {
			if (loc != -1) {
				// 截取子串
				result = srcStr.substring(0, loc);
			}
		} else {
			if (loc != -1) {
				// 截取子串
				result = srcStr.substring(loc + 1, srcStr.length());
			}
		}
		return result;
	}

}
