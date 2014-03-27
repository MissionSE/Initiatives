package com.missionse.datafusionframeworklibrary.dataassociationlibrary;

import java.util.ArrayList;

import com.missionse.datafusionframeworklibrary.databaselibrary.CompositeDataAccessor;
import com.missionse.datafusionframeworklibrary.databaselibrary.SourceDataAccessor;
import com.missionse.datafusionframeworklibrary.databaselibrary.SourceDataModel;

/*
 * Class DataAssociation is the main input class for this program. It takes in measurement
 * data in the form of strings through the method associateMeasurement. Tests will
 * be performed against existing tracks. A list of associated candidates will be returned.
 */
public class DataAssociation implements DataAssociationProvider {

	// Reference to the database
	CompositeDataAccessor cdb;

	// Reference to package classes
	Evaluation eval;
	Sort sl;

	// Constructor for DataAssociation
	public DataAssociation(CompositeDataAccessor compositeDataAccess) {

		this.cdb = compositeDataAccess;

		eval = new Evaluation(compositeDataAccess);
		sl = new Sort();
	}

	/**
	 * This method will receive a measurement and determine if it associates to
	 * an existing track state. A list of candidates in statistical order will
	 * be returned.
	 */
	public ArrayList<String> associateMeasurement(SourceDataModel toUpdate) {

		// create return array of candidates id's
		ArrayList<String> candidateIds = new ArrayList<String>();

		// create return array of candidates
		ArrayList<Candidate> candidates = null;

		candidates = eval.evaluateInput(toUpdate);
		System.out.println("associateMeasurement candidates: " + candidates);
		
		// If candidates found
		if (!candidates.isEmpty()) {

			// sort candidate list
			ArrayList<Candidate> sorted = sl.sortList(candidates);

			// filter uniqueId
			for (Candidate c : sorted) {
				candidateIds.add(c.getUniqueId());
			}
		}

		return candidateIds;

	}

}
