package dataassociation;

import java.util.ArrayList;

import datacorrelation.Source;

/*
 * Class DataAssociation is the main input class for this program. It takes in measurement
 * data in the form of strings through the method associateMeasurement. Tests will
 * be performed against existing tracks. A list of associated candidates will be returned.
 */
public class DataAssociation {

    public DataAssociation()
    {}

	/**
	 * This method will receive a measurement and determine if it associates to an existing track
	 * state. A list of candidates in statistical order will be returned.
	 */
	public ArrayList<String> associateMeasurement(Source measurement)
	{
		return null;

	}

	/**
	 * This method will sort a received list of associated candidates in the desired statistical order.
	 */
	private ArrayList<String> sortList(ArrayList<String> candidateList)
	{
		return candidateList;
	}

}
