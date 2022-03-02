package com.TRDZ.note;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Navigation
	{
	private final FragmentManager manager;

	public Navigation(FragmentManager manager)
		{
		this.manager = manager;
		}

	public boolean is_corrupted() {return manager.isDestroyed();}

	public void add(int container, Fragment fragment, boolean remember) {
		if (manager.isDestroyed()) return;
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(container, fragment);
		if (remember) transaction.addToBackStack("");
		transaction.commit();
		}

	public void replace(int container, Fragment fragment, boolean remember) {
		if (manager.isDestroyed()) return;
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(container, fragment);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		if (remember) transaction.addToBackStack("");
		transaction.commit();
		}
	}
