package com.TRDZ.note.data;

public interface Observer {
	void receive(int index, int type, String content);
	}
