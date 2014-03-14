package com.missionse.datafusionframeworklibrary.databaselibrary;

public interface DatabaseAccessor {

	public void setupDatabase();
	
	public ArrayList<String> fetchUniqueId();
	
	public void shutdown();
	
	public void updateBuilder(Source source);
	
	public Source queryBuilder(String uniqueId, long desiredTime);
	
	public ArrayList<Source> queryBuilder(String sourceType,
            String uniqueId, long fromTime);
	
	
	
}
