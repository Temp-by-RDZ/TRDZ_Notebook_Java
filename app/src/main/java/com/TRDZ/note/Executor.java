package com.TRDZ.note;

public interface Executor {
	boolean isLandscape();
	void show_toast(String text, int length);
	void deletion(int id);
	void save();
	void load();
	}
