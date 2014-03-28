package com.missionse.datafusionframeworklibrary.dataassociation;

import java.util.ArrayList;

import com.missionse.datafusionframeworklibrary.database.SourceDataModel;

public interface DataAssociationProvider {

	/**
	 * Compiles a list of tracks to associate based on input measurement data.
	 * @param parsedData
	 * @return
	 */
	public ArrayList<String> associateMeasurement(SourceDataModel toUpdate);
}
