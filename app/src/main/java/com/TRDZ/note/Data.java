package com.TRDZ.note;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Data implements Serializable {

	private static ArrayList<line> elements;

	{//def Initialize
		elements = new ArrayList<>();
		elements.add(new line(" Хардкодовая запись 1",0));
		elements.add(new line(" Хардкодовая запись 2",0));
		elements.add(new line(" Хардкодовая запись 3",0));
		elements.add(new line(" Хардкодовая запись 4",0));
		elements.add(new line(" Хардкодовая запись 5",0));
		elements.get(0).set_Content(" Это пример элемента контейнера\n Здесь текст посвященный записи №1.");
		elements.get(0).set_Type(0);
		elements.get(1).set_Content(" Это пример элемента контейнера\n Здесь текст посвященный записи №2.");
		elements.get(1).set_Type(1);
		elements.get(2).set_Content(" Это пример элемента контейнера\n Здесь текст посвященный записи №3.");
		elements.get(2).set_Type(2);
		elements.get(3).set_Content(" Это пример элемента контейнера\n Здесь текст посвященный записи №4.");
		elements.get(3).set_Type(3);
		elements.get(4).set_Content(" Это пример элемента контейнера\n Здесь текст посвященный записи №5.");
		elements.get(4).set_Type(3);
		}
//region Сохранение и загрузка
	public ArrayList<Date> saveT1() {
		ArrayList<Date> result = new ArrayList<>();
		for (line line: elements ) result.add(line.time);
		return result;
		}
	public ArrayList<Integer> saveT2() {
		ArrayList<Integer> result = new ArrayList<>();
		for (line line: elements ) result.add(line.type);
		return result;
		}
	public ArrayList<Integer> saveT3() {
		ArrayList<Integer> result = new ArrayList<>();
		for (line line: elements ) result.add(line.year);
		return result;
		}
	public ArrayList<Integer> saveT4() {
		ArrayList<Integer> result = new ArrayList<>();
		for (line line: elements ) result.add(line.month);
		return result;
		}
	public ArrayList<Integer> saveT5() {
		ArrayList<Integer> result = new ArrayList<>();
		for (line line: elements ) result.add(line.day);
		return result;
		}
	public ArrayList<String> saveT6() {
		ArrayList<String> result = new ArrayList<>();
		for (line line: elements ) result.add(line.content);
		return result;
		}
	public ArrayList<String> saveT7() {
		ArrayList<String> result = new ArrayList<>();
		for (line line: elements ) result.add(line.name);
		return result;
		}
	public void load(ArrayList<Date> t1,ArrayList<Integer> t2,ArrayList<Integer> t3,ArrayList<Integer> t4,ArrayList<Integer> t5,ArrayList<String> t6,ArrayList<String> t7) {
		elements.clear();
		for (int i = 0; i < t1.size(); i++)
			{
			elements.add(new line(t1.get(i),
					t2.get(i),t3.get(i),t4.get(i),t5.get(i),t6.get(i),t7.get(i)));
			}
		}
//endregion
	public int add(String name) {
		elements.add(new line(name));
		return elements.size()-1;
		}

	public int add(String name,Integer type) {
		elements.add(new line(name,type));
		return elements.size()-1;
		}

	public int add(String name,Integer type, int year, int month, int day) {
		elements.add(new line(name,type,year,month,day));
		return elements.size()-1;
		}

	public void remove(int number) {
		elements.remove(number);
		}

	public Integer Size() {
		return elements.size();
		}

	public Integer get_type(int number) {
		return elements.get(number).get_Type();
		}

	public String get_line(int number) {
		return elements.get(number).get_time() + elements.get(number).get_name();
		}
	public String get_name(int number) {
		return elements.get(number).get_name();
		}
	public String get_cont(int number) {
		return elements.get(number).get_Content();
		}

	public void set_type(int number,int type) {
		elements.get(number).set_Type(type);
		}

	public void set_line(int number, String name) {
		elements.get(number).set_name(name);
		}

	public void set_cont(int number,String cont) {
		elements.get(number).set_Content(cont);
		}

	public void set_time(int number, int year, int month, int day) {
		elements.get(number).set_time(year,month,day);
		}


//region Субклассы
	protected class line {
		Date time;
		Integer type;
		int year;
		int month;
		int day;
		String name;
		String content;

		public line(String name) {
			this(name,0);
			}

		public line(String name,Integer type) {
			this.name = name;
			this.type = type;
			time = new Date();
			year = time.getYear()%100;
			month= time.getMonth();
			day  = time.getDay();
			}

		public line(String name,Integer type, int year, int month, int day) {
			this.name = name;
			this.type = type;
			time = new Date();
			this.year = year%100;
			this.month= month;
			this.day  = day;
			}

		public line(Date t1 ,Integer t2, int t3, int t4, int t5,String t6, String t7) {
			time=t1;
			type=t2;
			year=t3;
			month=t4;
			day=t5;
			content=t6;
			name=t7;
			}

		public void set_time(int year, int month, int day) {
			this.year = year%100;
			this.month= month;
			this.day  = day;}

		public void set_name(String name) {
			this.name=name;
			}

		public void set_Type(Integer type) {
			this.type = type;
			}

		public void set_Content(String text) {
			content=text;
			}

		public String get_Content() {
			return content;
			}

		public Integer get_Type() {
	return type;
	}

		public String get_name() {
			return name;
			}

		public String get_time() {
			StringBuilder result=new StringBuilder(" ");
		//region Наполение текст билдера
			if (year<10) result.append("0");
			result.append(year);
			result.append(".");
			if (month<10) result.append("0");
			result.append(month);
			result.append(".");
			if (day<10) result.append("0");
			result.append(day);
			result.append(" ");
			int temp=(time.getHours());
			if (temp<10) result.append("0");
			result.append(temp);
			result.append(":");
			temp=(time.getMinutes());
			if (temp<10) result.append("0");
			result.append(temp);
			result.append(" ");
		//endregion
			return result.toString();
			}
		}
	//endregion

	}
