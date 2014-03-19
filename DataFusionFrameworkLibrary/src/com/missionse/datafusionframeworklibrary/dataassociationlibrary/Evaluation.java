package com.missionse.datafusionframeworklibrary.dataassociationlibrary;

import java.util.ArrayList;

import com.missionse.datafusionframeworklibrary.databaselibrary.Source;

/*
 * Class Evaluation will receive a measurement and tests will be performed against existing tracks.
 * A list of associated candidates will be returned.
 */
public class Evaluation {
	private boolean performNonKinematic = false;
	private boolean performKinematic = true;
	private boolean extrapolate = false;
	
	NonKinematicTest nkt;
	KinematicTest kt;
	Extrapolation ex;
	Sort sl;

	public Evaluation() {
		nkt = new NonKinematicTest();
		kt = new KinematicTest();
		ex = new Extrapolation();
		sl = new Sort();
	}

	/**
	 * This method will receive a measurement and perform tests to existing
	 * tracks to determine if data is associated. a list of candidates will be
	 * assembled.
	 */
	public ArrayList<String> evaluateInput(Source toUpdate) {

        System.out.println("evaluateInput toUpdate: "+toUpdate);
		
        boolean valid = true;
		
        // create working array for candidate data
		Candidate cand = new Candidate();
		ArrayList<Candidate> candList = new ArrayList<Candidate>();

		// create return array of candidate is id's
		ArrayList<String> candidates = new ArrayList<String>();


		// loop thru all tracks in db

		if (performNonKinematic)
			valid = nkt.nonKinematicTest();

		if (valid && extrapolate)
			ex.extrapolateTrack();

		if (valid && performKinematic)
			valid = kt.kinematicTest();
		
		System.out.println("evaluateInput valid: " +valid);
		
		// if track passes all tests, add to candidate list
		if (valid) {
			cand.setUniqueId("0001");
			candList.add(cand);
		}
		System.out.println("evaluateInput cand: "+cand.getUniqueId()); 
		// end loop thru all tracks in db

		// if candidates found
		if (!candList.isEmpty()){
			
            // sort candidate list
			ArrayList<Candidate> sorted = sl.sortList(candList);

			// filter uniqueId
			for (Candidate c : sorted) {
				candidates.add(c.getUniqueId());
			}
		}
		return candidates;
	}

}
