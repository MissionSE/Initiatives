package com.missionse.datafusionframeworklibrary.dataassociation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.missionse.datafusionframeworklibrary.database.CompositeDataAccessor;
import com.missionse.datafusionframeworklibrary.database.SourceDataModel;

/*
 * Class Evaluation will receive a measurement and tests will be performed against existing tracks.
 * A list of associated candidates will be returned.
 */
public class Evaluation {
	private boolean performNonKinematic = true;
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

		// create working array for candidate data
		ArrayList<Candidate> candList = new ArrayList<Candidate>();

		List<SourceDataModel> dbList = null;

		// get list of all composite tracks
		try {
			dbList = db.queryCompositeBuilder(null, null, 0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("evaluateInput dbList: " + dbList);

		// loop through list
		for (SourceDataModel item : dbList) {
			System.out.println("evaluateInput item: " + item);

			boolean valid = true;

			if (performNonKinematic)
				valid = nkt.nonKinematicTest(toUpdate.getTrackCategory(), item.getTrackCategory());

			if (valid && extrapolate)
				ex.extrapolateTrack();

			if (valid && performKinematic)
				valid = kt.kinematicTest(toUpdate, item);

			System.out.println("evaluateInput valid: " + valid);

			// if track passes all tests, add to candidate list
			if (valid) {
				Candidate cand = new Candidate();
				cand.setUniqueId(item.getUniqueId());
				cand.setRangeDiff(0.0); // temp sso
				candList.add(cand);
			}
			// end loop thru all tracks in db

		}

		return candList;
	}
}
