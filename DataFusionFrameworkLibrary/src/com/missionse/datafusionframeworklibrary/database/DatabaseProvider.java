package com.missionse.datafusionframeworklibrary.database;


public interface DatabaseProvider {

	/**
	 * creates the tables for the DataFusionFrameworkLibrary.
	 */
	public void setupDatabase(String dbname);

	public void shutdown();

	/**
	 * Retrieves the handle to the SourceDataAccessor.
	 */
	public SourceDataAccessor getSourceDataAccessor();
	
	/**
	 * Retrieves the handle to the CompositeDataAccessor.
	 */
	public CompositeDataAccessor getCompositeDataAccessor();
	
}
