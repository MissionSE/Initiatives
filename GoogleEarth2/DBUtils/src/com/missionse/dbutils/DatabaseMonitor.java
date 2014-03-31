package com.missionse.dbutils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.missionse.GEUtils.Util;

public class DatabaseMonitor extends Thread {

	private Map<String, List<DatabaseListener>> map = new HashMap<String, List<DatabaseListener>>();
	DatabaseAccess db = new DatabaseAccess();

	public DatabaseMonitor() { }

	public void run () {
		try {
		// TODO: instead of it's own thread this should be run on a timer.
		pollDatabase();
		} finally {
			System.out.println("DatabaseMonitor thread has exited");
		}
	}
	public void addListener(String table, /* need a start time */ DatabaseListener listener) {
		List<DatabaseListener> list = map.get(table);
		// If there isn't an entry in the map for table, add one.
		if (list == null) {
			list = new ArrayList<DatabaseListener>();
			map.put(table, list);
		}

		list.add(listener);
	}

	private long now() {
		return Calendar.getInstance().getTimeInMillis();
	}

	private void pollDatabase() {
		long since = 0;
		long current_time = 0;
		while (true) {
			current_time = now();
			//System.out.println("DBMonitor polling");
			// First pass, loop through the set of keys in the map
			// An improvement would be to put polling for each table on its
			// own thread.

			//System.out.println("Since is " + String.valueOf(since));

			for (String tablename: map.keySet()) {
				//System.out.println("polling on " + tablename);
				boolean b = hasTableBeenUpdated(tablename, since); // TODO: add time parameter
				if (b) {
					// This needs to be done on another thread so that it doesn't slow down the
					// poll thread. Handle the case where the next poll is ready before the previous
					// notification has completed
					notifyListeners(tablename);
					//System.out.println("Notified " + tablename + " listeners");
					// update since time
					// TODO: it would be better if hasTableBeenUpdated() returned the time
					// of the last update and we use that as the next value of since.
					since = current_time;
				}
			}

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean hasTableBeenUpdated (String table, long since) {

		boolean rval = true;
		ResultSet resultSet = db.checkForUpdate(table, since);

		if (Util.isResultSetEmpty(resultSet)) {
			rval = false;
		}

		try {
			while (resultSet.next()) {
				String s1 = resultSet.getString(1);
				String s2 = resultSet.getString(2);
				String s3 = resultSet.getString(3);
				//System.out.println("Result: " + s1 + " " + s2 + " " + s3);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rval;
	}

	// TODO: this should be on it's own thread.
	private void notifyListeners (String table) {
		List<DatabaseListener> list = map.get(table);
		for (DatabaseListener listener: list) {
			listener.onChange(table);
		}
	}

}
