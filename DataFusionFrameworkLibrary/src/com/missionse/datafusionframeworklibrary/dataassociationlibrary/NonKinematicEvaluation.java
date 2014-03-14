package com.missionse.datafusionframeworklibrary.dataassociationlibrary;

/*
 * Class NonKinematicAssociation will perform necessary non-kinematic tests for data association.
 * A true/false indicator will be returned.
 */
public class NonKinematicEvaluation {

    public NonKinematicEvaluation()
    {}

	/**
	 * This method will perform necessary non-kinematic tests for data association.
	 */
	public boolean nonKinematicAssociation()
	{		
		liveSimAssociation();
		categoryAssociation();
		return false;

	}
	
	/**
	 * This method will live/sim test for data association .
	 */
	private boolean liveSimAssociation()
	{
		return false;

	}
	
	/**
	 * This method will perform category test for data association .
	 */
	private boolean categoryAssociation()
	{
		return false;

	}
	

}
