package com.TRDZ.note.data;

 //Реализация ((MainActivity) requireActivity()).get_Publisher().send_info( index, data.get_type(index), "Симл текст");

import java.util.ArrayList;
import java.util.List;

public class Publisher {
	private List<Observer> observers;

	public Publisher() {
		observers = new ArrayList<>();
		}

	public void sub(Observer observer) {observers.remove(observer);}
	public void usb(Observer observer) {observers.add(observer);}

	public void send_info(int index, int type, String content) {
		for(Observer observer:observers) observer.receive(index,type,content);
		}
	}
