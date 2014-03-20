package com.missionse.datafusionframeworklibrary.datafusionlibrary;

import java.util.ArrayList;

import com.missionse.datafusionframeworklibrary.databaselibrary.Source;
import com.missionse.datafusionframeworklibrary.databaselibrary.SourceDataModel;

public class CorrelateSources
{
    //The CommonFieldParser that is used to correlate the data from the Sources.
    CommonFieldParser cfp;

    public CorrelateSources()
    {
        cfp = new CommonFieldParser();
    }

	/**
	 * This method creates a new ArrayList, adds to it clones of the sources this class is observing
	 * and send off the list to be correlated by the CommonFieldParser. It will return the correlated
	 * source that is returned by the parser.
	 */
	public SourceDataModel correlateSources(SourceDataModel toUpdate, ArrayList<SourceDataModel> sources)
	{
		if(sources.size() == 1)
		{
		    return sources.get(0).clone();
		}

		ArrayList<SourceDataModel> toCorrelate = new ArrayList<SourceDataModel>();

		for(int i = 0; i < sources.size(); i++)
		{
		    toCorrelate.add(sources.get(i).clone());
		}
		System.out.println("cs: toCorrelate"+toCorrelate);
		return cfp.correlateSources(toCorrelate, "3CS");

	}

}
