package com.missionse.datafusionframeworklibrary.datafusionlibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.missionse.datafusionframeworklibrary.databaselibrary.Database;
import com.missionse.datafusionframeworklibrary.databaselibrary.SourceDataAccessor;
import com.missionse.datafusionframeworklibrary.databaselibrary.SourceDataModel;

public class PackSupportingData {

	//Reference to the ObjectRefinementModule, which receives data once it has been through this module.
	ObjectRefinementModule orm;
	SourceDataAccessor db;
    Map<String, ObjectRefinementModule> trackRefinement = null;
	
	public PackSupportingData(SourceDataAccessor db)
	{
		this.db = db; 
		orm = null;
		trackRefinement = new HashMap<String, ObjectRefinementModule>();
	}

	/*
	 * Once this class has done everything it needs to do, this method allows it to send off data to
	 * other sections of the program.
	 */
	public void packSupportingData(SourceDataModel toUpdate, SourceDataModel correlated, 
			ArrayList<SourceDataModel> sources, String trackKey)
	{

		//Checks to see if the ObjectRefinementModule has been hooked up yet.
		if(orm == null)
		{
			/*
			 * When the ObejctRefinementModule is first created, it requires a Source. It, however,
			 * cannot receive a Source until this class has received data for and created one itself.
			 * Because of this, the ObejctRefinementModule must wait to be instantiated until now.
			 */

			orm = new ObjectRefinementModule(toUpdate.clone(), db);
			//trackRefinement.put(trackKey, orm);
			System.out.println("psd: instantiated orm, db: "+ db);
		}
		else
		{
			/*
			 * A new ArrayList is created with the correlated Source in position 0, along with
			 * clones of all other observed sources, all for the purpose of creating an array
			 * of Sources with that same format that can be sent off to the ObjectRefinementModule.
			 */
			ArrayList<SourceDataModel> toSend = new ArrayList<SourceDataModel>();

			toSend.add(correlated);

			for(int i = 0; i < sources.size(); i++)
			{
				toSend.add(sources.get(i).clone());
			}
			System.out.println("psd toSend: "+toSend);
			//orm = trackRefinement.get(trackKey);
			orm.refineObject(toSend.toArray(new SourceDataModel[0]));
		}
	}

}
