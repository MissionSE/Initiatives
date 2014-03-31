package com.missionse.datagen;

import com.missionse.dbutils.DatabaseListener;
import com.missionse.dbutils.DatabaseMonitor;

public class TestThread implements DatabaseListener {
	DatabaseMonitor dbm = new DatabaseMonitor();
	
	public TestThread () {
	}

	@Override
	public void onChange(String table) {
		System.out.println("Notified of change for " + table + ".");
	}

	public void begin() {
		dbm.addListener("trackposition", this);
		dbm.start();
		System.out.println("DBMonitor thread started");
	}
}
