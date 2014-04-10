package com.missionse.kmlserver;

import java.sql.ResultSet;
import java.util.Arrays;

import com.missionse.GEUtils.KmlUtil;
import com.missionse.GEUtils.Util;
import com.missionse.dbutils.DatabaseAccess;
import com.missionse.dbutils.DatabaseListener;
import com.missionse.dbutils.DatabaseMonitor;

import de.micromata.opengis.kml.v_2_2_0.Kml;

public class KmlServerDatabaseMonitor implements DatabaseListener {
	DatabaseAccess db = new DatabaseAccess();

	private DatabaseMonitor dbm = new DatabaseMonitor();
	
	KmlServerDatabaseMonitor() {
	}
	
	public void start() {
		dbm.addListener("trackposition", this);
		dbm.start();
	}

	@Override
	public void onChange(String table) {
		//System.out.println("KmlServerDatabaseMonitor::onChange called");
		String st = Arrays.toString(Thread.currentThread().getStackTrace());
		//System.out.println(st);
		
		if (table == "trackposition") {
			// TODO: queue a message on another thread so we are not
			// blocking the dbmonitor thread.
			handleTrackPositionUpdate();
		}
	}

	private void handleTrackPositionUpdate() {
		// create kml object and place it in the repository.
		String kmlString = null;
		
		ResultSet resultSet = db.getTrackPosition(-1); // request all tracks
		// TODO: move isResultSetEmpty to a new dbutil class
		if (Util.isResultSetEmpty(resultSet)) {
			System.out.println("handleTracpPositionUpdate: nothing in db");
			return;
		}
		//System.out.println("ServerDatabaseMonotir: updating trackposition");
		Kml kml = KmlUtil.convertTrackPosition(resultSet);
		KmlUtil.toFile(kml, "currenttrackposition.kml"); // debug
		kmlString = KmlUtil.toString(kml);
		
		KmlRepository.add("trackposition", kmlString);
	}
	
}
