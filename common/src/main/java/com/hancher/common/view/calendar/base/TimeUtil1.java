package com.hancher.common.view.calendar.base;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil1 {

	private Date todata;
	private Calendar calendar;
	private int n = 0;
	private int y = 0;
	private int show_year = 0;
	private int show_mouth = 0;
	private Dd1 today;

	public Dd1 getToday() {
		return today;
	}

	public TimeUtil1() {
		calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		n = calendar.get(Calendar.YEAR);
		y = calendar.get(Calendar.MONTH);
		today = new Dd1();
		today.d = calendar.get(Calendar.DAY_OF_MONTH);
		today.m = y;
		today.y = n;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public int getN() {
		return n;
	}

	public int getY() {
		return y;
	}

	int test = 0;

	public int getshowyear() {
		return calendar.get(Calendar.YEAR);
	}

	public int getshowmouth() {
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * 
	 * @param n年
	 * @param y月
	 * @return 给出当月的日期
	 */
	public ArrayList<Dd1> getlist(int n, int y) {
		ArrayList<Dd1> list = new ArrayList<Dd1>();
		calendar.set(n, y, 1, 0, 0, 0);
		int da = calendar.get(Calendar.DAY_OF_WEEK);
		for (int i = 0; i < 42; i++) {
			calendar.set(n, y,1-da+i+1);
			Dd1 dd = new Dd1();
			
			if((calendar.get(Calendar.MONTH) -y)%12 == 0){
				dd.isukow = false;
			}else{
				dd.isukow = true;
			}
			
			dd.d = calendar.get(calendar.DAY_OF_MONTH);
			dd.y = calendar.get(Calendar.YEAR);
			dd.m = calendar.get(Calendar.MONTH);
			list.add(dd);
		}
		calendar.set(n, y, 1, 0, 0, 0);
		return list;
	}

	public ArrayList<Dd1> getDdList(int n, int y) {
		ArrayList<Dd1> list = new ArrayList<Dd1>();
		calendar.set(n, y, 1, 0, 0, 0);
		int da = calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a ").format(new Date(calendar.getTimeInMillis())));

		int riqi = 1;
		for (int i = 0; i < 42; i++) {
			Dd1 dd = new Dd1();
			if (i < (da - 1)) {
				dd.isukow = true;
			} else {
				if (i > calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + da - 2) {
					dd.isukow = true;
				} else {
					dd.isukow = false;
					dd.d = riqi;
					dd.y = calendar.get(Calendar.YEAR);
					dd.m = calendar.get(Calendar.MONTH);
				}
				riqi++;

			}
			list.add(dd);
		}

		System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return list;
	}

	public int getint() {
		return test;
	}
}

