package com.missionse.datafusionframeworklibrary.databaselibrary;

import java.sql.SQLException;
import java.util.List;

public interface CompositeDataAccessor {

	public List<String> fetchCompositeDataId() throws SQLException;
		
	public void updateCompositeBuilder(CompositeDataModel source) throws SQLException;
	
	public CompositeDataModel queryCompositeBuilder(String uniqueId, long desiredTime) throws SQLException;
	
	public List<CompositeDataModel> queryCompositeBuilder(String sourceType,
            String uniqueId, long fromTime) throws SQLException;
}
