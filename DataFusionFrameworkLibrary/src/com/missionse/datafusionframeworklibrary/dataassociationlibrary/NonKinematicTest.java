package com.missionse.datafusionframeworklibrary.dataassociationlibrary;

/*
 * Class NonKinematicAssociation will perform necessary non-kinematic tests for data association.
 * A true/false indicator will be returned.
 */
public class NonKinematicTest {

    public NonKinematicTest()
    {}

	/**
	 * This method will perform necessary non-kinematic tests for data association.
	 */
	public boolean nonKinematicTest()
	{		
		liveSim();
		category();
		return false;

	}
	
	/**
	 * This method will live/sim test for data association .
	 */
	private boolean liveSim()
	{
		return false;

	}
	
	/**
	 * This method will perform category test for data association .
	 */
	private boolean category()
	{
		return false;

	}
	

}
