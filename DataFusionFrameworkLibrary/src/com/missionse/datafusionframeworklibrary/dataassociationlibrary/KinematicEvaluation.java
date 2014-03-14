package com.missionse.datafusionframeworklibrary.dataassociationlibrary;

/*
 * Class kinematicAssociation will perform necessary kinematic tests for data association.
 * A true/false indicator will be returned.
 */
public class KinematicEvaluation {

    public KinematicEvaluation()
    {}

	/**
	 * This method will perform necessary kinematic tests for data association.
	 */
	public boolean kinematicAssociation()
	{
		
		XYZposAssociation();
		XYZvelAssociation();
		heightAssociation();
		courseAssociation();
		return false;

	}
	
	/**
	 * This method will perform position test for data association .
	 */
	private boolean XYZposAssociation()
	{
		return false;

	}
	
	/**
	 * This method will perform velocity test for data association .
	 */
	private boolean XYZvelAssociation()
	{
		return false;

	}
	
	/**
	 * This method will perform height test for data association .
	 */
	private boolean heightAssociation()
	{
		return false;

	}
	
	/**
	 * This method will perform course test for data association .
	 */
	private boolean courseAssociation()
	{
		return false;

	}
	
}
