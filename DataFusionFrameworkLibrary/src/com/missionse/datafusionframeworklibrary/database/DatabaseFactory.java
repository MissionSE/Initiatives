package com.missionse.datafusionframeworklibrary.database;

import java.sql.SQLException;

public class DatabaseFactory implements DatabaseProvider {

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
		return database;
	}

	public CompositeDataAccessor getCompositeDataAccessor() {
		return database;
	}

	public void shutdown() {
		try {
			database.shutdown();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
