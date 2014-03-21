package com.missionse.datafusionframeworklibrary.dataassociationlibrary;

import java.util.ArrayList;

import com.missionse.datafusionframeworklibrary.databaselibrary.CompositeDataAccessor;
import com.missionse.datafusionframeworklibrary.databaselibrary.Source;
import com.missionse.datafusionframeworklibrary.databaselibrary.SourceDataAccessor;
import com.missionse.datafusionframeworklibrary.databaselibrary.SourceDataModel;

/*
 * Class DataAssociation is the main input class for this program. It takes in measurement
 * data in the form of strings through the method associateMeasurement. Tests will
 * be performed against existing tracks. A list of associated candidates will be returned.
 */
public class DataAssociation {

	// Reference to package classes
	Evaluation eval;

	// The list of currently observed Sources.
	private ArrayList<SourceDataModel> sources;

	// Constructor for DataAssociation
	public DataAssociation(SourceDataAccessor sourceDataAccess,
			CompositeDataAccessor compositeDataAccess) {

		eval = new Evaluation();
		sources = new ArrayList<SourceDataModel>();
	}

	/**
	 * This method will receive a measurement and determine if it associates to
	 * an existing track state. A list of candidates in statistical order will
	 * be returned.
	 */
	public ArrayList<String> associateMeasurement(String[] parsedData) {

		ArrayList<String> candidates = null;

		System.out.println("associateMeasurement parsedData[0]: "
				+ parsedData[0]);

		// Storage for, and retrieval of, the Source this data represents.
		SourceDataModel toUpdate = searchExistingSources(parsedData[0]);
		System.out.println("associateMeasurement toUpdate: " + toUpdate);

		/*
		 * If this program is not yet observing a Source with the given unique
		 * identification, a new one will be created.
		 */
		if (toUpdate == null) {
			toUpdate = createNewSource(parsedData[0]);
		}

		/*
		 * Whether the data represents an already existing source or a newly
		 * created one, update the source with.
		 */
		toUpdate.update(parsedData);

		candidates = eval.evaluateInput(toUpdate);

		return candidates;

	}

	/*
	 * This method searches through the sources that already exist, looking for
	 * one with the given unique ID. If no such source is found, it returns
	 * null.
	 */
	private SourceDataModel searchExistingSources(String id) {
		for (SourceDataModel s : sources) {
			if (s.getUniqueId().compareTo(id) == 0) {
				return s;
			}
		}

		return null;
	}

	/*
	 * This method creates, returns and adds to the list of observed sources a
	 * new Source with the given identification string. A source created this
	 * way has nothing else set yet, effectively making it empty.
	 */
	private SourceDataModel createNewSource(String id) {
		SourceDataModel newSource = new SourceDataModel(id);
		System.out.println("receivePacket:createNewSource newSource: "
				+ newSource);
		return newSource;
	}

}
