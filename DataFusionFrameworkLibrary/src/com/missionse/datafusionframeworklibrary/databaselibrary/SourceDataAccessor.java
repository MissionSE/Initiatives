package com.missionse.datafusionframeworklibrary.databaselibrary;

import java.sql.SQLException;
import java.util.List;

public interface SourceDataAccessor {
	
	public List<String> fetchSourceDataId() throws SQLException;
		
	public void updateSourceBuilder(SourceDataModel source) throws SQLException;
	
	public SourceDataModel querySourceBuilder(String uniqueId, long desiredTime) throws SQLException;
	
	public List<SourceDataModel> querySourceBuilder(String sourceType,
            String uniqueId, long fromTime) throws SQLException;

	public void shutdown();
		
}
