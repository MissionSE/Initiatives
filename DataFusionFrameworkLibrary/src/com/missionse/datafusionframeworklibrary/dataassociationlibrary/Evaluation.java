package com.missionse.datafusionframeworklibrary.dataassociationlibrary;

import java.util.ArrayList;

import com.missionse.datafusionframeworklibrary.databaselibrary.Source;

/*
 * Class Evaluation will receive a measurement and tests will be performed against existing tracks.
 * A list of associated candidates will be returned.
 */
public class Evaluation {

	public Evaluation()
	{}

	/**
	 * This method will receive a measurement and perform tests to existing tracks to determine
	 * if data is associated. a list of candidates will be assembled.
	 */
	public ArrayList<String> evaluateInput(String data)
	{
        return null;

	}

    /*
     * This method sorts the given sources by their range difference for smallest to largest using
     * a bubble sort algorithm.
     */
	private ArrayList<String> sortList(ArrayList<String> candidateList)
	{
		boolean sorted = false;
		String temp;

		while(!sorted)
		{
		    sorted = true;

		    for(int i = 1; i < candidateList.size(); i++)
		    {
			if(candidateList.get(i).rangeDiff() < candidateList.get(i - 1).rangeDiff())
			{
			    temp = candidateList.get(i);
			    candidateList.set(i - 1, candidateList.get(i));
			    candidateList.set(i, temp);

			    sorted = false;
			}
		    }
		}
		
		return candidateList;
		
	}

}
