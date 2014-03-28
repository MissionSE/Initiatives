package com.missionse.datafusionframeworklibrary.database;

import java.sql.SQLException;
import java.util.List;

public interface CompositeDataAccessor {

	public List<String> fetchCompositeDataId() throws SQLException;
		
	public void updateCompositeBuilder(SourceDataModel source) throws SQLException;
	
	public SourceDataModel queryCompositeBuilder(String uniqueId, long desiredTime) throws SQLException;
	
	public List<SourceDataModel> queryCompositeBuilder(String sourceType,
            String uniqueId, long fromTime) throws SQLException;

	public void updateCompositeSourceCrossReference(String compositeId, String sourceId) throws SQLException;
	
	public List<String> fetchSourcesForComposite(String compositeId) throws SQLException;
	
	public void shutdown() throws SQLException;
}
