package com.missionse.datafusionframeworklibrary.dataassociationlibrary;

/*
 * Class NonKinematicAssociation will perform necessary non-kinematic tests for data association.
 * A true/false indicator will be returned.
 */
public class NonKinematicTest {

	public NonKinematicTest() {
	}

	/**
	 * This method will perform necessary non-kinematic tests for data
	 * association.
	 * @return 
	 */
	public boolean nonKinematicTest() {

		if (liveSim() && category())
			return true;
		else
			return false;

	}

	/**
	 * This method will live/sim test for data association .
	 */
	private boolean liveSim() {
		return true;

	}

	/**
	 * This method will perform category test for data association .
	 */
	private boolean category() {
		return true;

	}

}
