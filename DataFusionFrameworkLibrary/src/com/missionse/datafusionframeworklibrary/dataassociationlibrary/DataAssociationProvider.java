package com.missionse.datafusionframeworklibrary.dataassociationlibrary;

import java.util.ArrayList;

import com.missionse.datafusionframeworklibrary.databaselibrary.SourceDataModel;

public interface DataAssociationProvider {

	/**
	 * Compiles a list of tracks to associate based on input measurement data.
	 * @param parsedData
	 * @return
	 */
	public ArrayList<String> associateMeasurement(SourceDataModel toUpdate);
}
