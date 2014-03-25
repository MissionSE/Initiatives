package com.missionse.datafusionframeworklibrary.dataassociationlibrary;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.missionse.datafusionframeworklibrary.databaselibrary.CompositeDataAccessor;
import com.missionse.datafusionframeworklibrary.databaselibrary.CompositeDataModel;
import com.missionse.datafusionframeworklibrary.databaselibrary.Source;
import com.missionse.datafusionframeworklibrary.databaselibrary.SourceDataModel;

/*
 * Class Evaluation will receive a measurement and tests will be performed against existing tracks.
 * A list of associated candidates will be returned.
 */
public class Evaluation {
	private boolean performNonKinematic = false;
	private boolean performKinematic = true;
	private boolean extrapolate = false;

	CompositeDataAccessor db;

	NonKinematicTest nkt;
	KinematicTest kt;
	Extrapolation ex;

	public Evaluation(CompositeDataAccessor compositeDataAccess) {

		this.db = compositeDataAccess;
		nkt = new NonKinematicTest();
		kt = new KinematicTest();
		ex = new Extrapolation();
	}

	/**
	 * This method will receive a measurement and perform tests to existing
	 * tracks to determine if data is associated. a list of candidates will be
	 * assembled.
	 */
	public ArrayList<Candidate> evaluateInput(SourceDataModel toUpdate) {

		System.out.println("evaluateInput toUpdate: " + toUpdate);

		boolean valid = true;

		// create working array for candidate data
		Candidate cand = new Candidate();
		ArrayList<Candidate> candList = new ArrayList<Candidate>();

		List<CompositeDataModel> dbList = null;

//		// loop thru all tracks in db
//		try {
//			dbList = db.queryCompositeBuilder();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		System.out.println("evaluateInput dbList: " + dbList);

//		for (CompositeDataModel item : dbList) {

			if (performNonKinematic)
				valid = nkt.nonKinematicTest();

			if (valid && extrapolate)
				ex.extrapolateTrack();

			CompositeDataModel item = null; //temp sso
			if (valid && performKinematic)
				valid = kt.kinematicTest(toUpdate, item);

			System.out.println("evaluateInput valid: " + valid);

			// if track passes all tests, add to candidate list
			if (valid) {
				cand.setUniqueId("3CS");
				candList.add(cand);
			}
			System.out.println("evaluateInput cand: " + cand.getUniqueId());
			// end loop thru all tracks in db

//		}
		return candList;
	}
}
