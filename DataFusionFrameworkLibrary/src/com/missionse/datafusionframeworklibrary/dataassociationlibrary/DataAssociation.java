package com.missionse.datafusionframeworklibrary.dataassociationlibrary;

import java.util.ArrayList;

/*
 * Class DataAssociation is the main input class for this program. It takes in measurement
 * data in the form of strings through the method associateMeasurement. Tests will
 * be performed against existing tracks. A list of associated candidates will be returned.
 */
public class DataAssociation {
	
	Evaluation eval;

    public DataAssociation()
    {
    	eval = new Evaluation();
    }

	/**
	 * This method will receive a measurement and determine if it associates to an existing track
	 * state. A list of candidates in statistical order will be returned.
	 */
	public ArrayList<String> associateMeasurement(String data)
	{

		ArrayList<String> candidates = null;
		
		//Parse the data into an array of Strings, delimited by commas.
        String[] parsedData = data.split(",", -1);

        System.out.println("associateMeasurement data: "+data);
        
        candidates = eval.evaluateInput(parsedData);
        
        return candidates;

	}

}
