package com.missionse.datafusionframeworklibrary.databaselibrary;

import java.sql.SQLException;

public class DatabaseFactory implements DatabaseProvider {

	private SourceDataAccessor sourceDataHandle = null;
	private CompositeDataAccessor compositeDataHandle = null;
	private Database database = null;

	public DatabaseFactory() {
		database = new Database();	
	}

	public void setupDatabase(String dbname) {
		try {
			database.setupDatabase(dbname);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SourceDataAccessor getSourceDataAccessor() {
		return sourceDataHandle;
	}

	public CompositeDataAccessor getCompositeDataAccessor() {
		return compositeDataHandle;
	}

	public void shutdown() {
		try {
			database.shutdown();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
