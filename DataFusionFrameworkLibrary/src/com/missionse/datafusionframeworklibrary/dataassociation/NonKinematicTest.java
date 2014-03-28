package com.missionse.datafusionframeworklibrary.dataassociation;

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
	 * 
	 * @param candidateCategory
	 * @param toUpdateCategory
	 * 
	 * @return
	 */
	public boolean nonKinematicTest(String toUpdateCategory,
			String candidateCategory) {

		if (liveSim() && category(toUpdateCategory, candidateCategory))
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
	 * 
	 * @param candidateCategory
	 * @param toUpdateCategory
	 */
	private boolean category(String toUpdateCategory, String candidateCategory) {
	if (toUpdateCategory.equals(candidateCategory))
			return true;
		else
			return false;

	}

}
