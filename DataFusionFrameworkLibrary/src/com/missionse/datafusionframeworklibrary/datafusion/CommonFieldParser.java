package com.missionse.datafusionframeworklibrary.datafusion;

import java.util.ArrayList;

import com.missionse.datafusionframeworklibrary.database.SourceDataModel;

/*
 * The CommonFieldParser is used to correlate a list of sources into one source.
 *
 * This class was designed to handle any number of sources greater than 1.
 */
public class CommonFieldParser
{
	public CommonFieldParser()
	{}

	/*
	 * This is the method that recieves and oversees all steps of the correlating algorithm.
	 * At the end, it will return the correlated source.
	 */
	public SourceDataModel correlateSources(ArrayList<SourceDataModel> toCorrelate, String id)
	{
		//All correlated sources in the program are created with a identification of Cxxx.
		SourceDataModel correlated = new SourceDataModel(id);
		//Create storage for unused sources during steps of the correlation process.
		ArrayList<SourceDataModel> unused = new ArrayList<SourceDataModel>();

		//See methods for what the methods do.
		sortOutUncorrelatableXData(toCorrelate, unused);
		sortByErrorX(toCorrelate);
		correlateXvariables(correlated, toCorrelate);

		//Reset the arrays for correlation of Y variables.
		toCorrelate.addAll(unused);
		unused.clear();

		//Rinse for Y
		sortOutUncorrelatableYData(toCorrelate, unused);
		sortByErrorY(toCorrelate);
		correlateYvariables(correlated, toCorrelate);

		//Reset the arrays for correlation of Z variables.
		toCorrelate.addAll(unused);
		unused.clear();

		//Repeat for Z
		sortOutUncorrelatableZData(toCorrelate, unused);
		sortByErrorZ(toCorrelate);
		correlateZvariables(correlated, toCorrelate);

		//Reset the source array for the correlation of other non-XYZ variables.
		toCorrelate.addAll(unused);

		correlateOthers(correlated, toCorrelate);

		return correlated;
	}

	/*
	 * This method sorts out from the array of sources which ones do not have reliable data. i.e.
	 * any sources that do not have their ErrorX set to anything. If you do not know what a source's
	 * error x is, not only do we not know how to weigh it againt other sources, its X data may
	 * be infinately unreliable for all we know. If no source has it's error x, however, it is deemed
	 * that all sources have equally unreliable data and therefore can be correlated with equal weights
	 * as some data is better than no data.
	 */
	private void sortOutUncorrelatableXData(ArrayList<SourceDataModel> toCorrelate, ArrayList<SourceDataModel> unused)
	{
		SourceDataModel temp;

		for(int i = 0; i < toCorrelate.size(); i++)
		{
			if(toCorrelate.get(i).getErrorX() == null)
			{
				temp = toCorrelate.remove(i);
				unused.add(temp);
				i--;
			}
		}

		if(toCorrelate.isEmpty())
		{
			toCorrelate.addAll(unused);

			for(SourceDataModel s : toCorrelate)
			{
				s.setErrorX(new Double(1));
			}
		}
	}

	//See description for sortOutUncorrelatableXData, replacing any instance of 'x' to 'y'.
	private void sortOutUncorrelatableYData(ArrayList<SourceDataModel> toCorrelate, ArrayList<SourceDataModel> unused)
	{
		SourceDataModel temp;

		for(int i = 0; i < toCorrelate.size(); i++)
		{
			if(toCorrelate.get(i).getErrorY() == null)
			{
				temp = toCorrelate.remove(i);
				unused.add(temp);
				i--;
			}
		}

		if(toCorrelate.isEmpty())
		{
			toCorrelate.addAll(unused);

			for(SourceDataModel s : toCorrelate)
			{
				s.setErrorY(new Double(1));
			}
		}
	}

	//See description for sortOutUncorrelatableXData, replacing any instance of 'x' to 'z'.
	private void sortOutUncorrelatableZData(ArrayList<SourceDataModel> toCorrelate, ArrayList<SourceDataModel> unused)
	{
		SourceDataModel temp;

		for(int i = 0; i < toCorrelate.size(); i++)
		{
			if(toCorrelate.get(i).getErrorZ() == null)
			{
				temp = toCorrelate.remove(i);
				unused.add(temp);
				i--;
			}
		}

		if(toCorrelate.isEmpty())
		{
			toCorrelate.addAll(unused);

			for(SourceDataModel s : toCorrelate)
			{
				s.setErrorZ(new Double(1));
			}
		}
	}

	/*
	 * This method sorts the given sources by their error x's for smallest to largest (for the
	 * use of this algoritm, it doesnt matter) using a bubble sort algorithm.
	 */
	private void sortByErrorX(ArrayList<SourceDataModel> toCorrelate)
	{
		boolean sorted = false;
		SourceDataModel temp;

		while(!sorted)
		{
			sorted = true;

			for(int i = 1; i < toCorrelate.size(); i++)
			{
				if(toCorrelate.get(i).getErrorX() < toCorrelate.get(i - 1).getErrorX())
				{
					temp = toCorrelate.get(i);
					toCorrelate.set(i - 1, toCorrelate.get(i));
					toCorrelate.set(i, temp);

					sorted = false;
				}
			}
		}
	}

	//See description for sortByErrorX, replacing any instance of 'x' to 'y'.
	private void sortByErrorY(ArrayList<SourceDataModel> toCorrelate)
	{
		boolean sorted = false;
		SourceDataModel temp;

		while(!sorted)
		{
			sorted = true;

			for(int i = 1; i < toCorrelate.size(); i++)
			{
				if(toCorrelate.get(i).getErrorY() < toCorrelate.get(i - 1).getErrorY())
				{
					temp = toCorrelate.get(i);
					toCorrelate.set(i - 1, toCorrelate.get(i));
					toCorrelate.set(i, temp);

					sorted = false;
				}
			}
		}
	}

	//See description for sortByErrorX, replacing any instance of 'x' to 'z'.
	private void sortByErrorZ(ArrayList<SourceDataModel> toCorrelate)
	{
		boolean sorted = false;
		SourceDataModel temp;

		while(!sorted)
		{
			sorted = true;

			for(int i = 1; i < toCorrelate.size(); i++)
			{
				if(toCorrelate.get(i).getErrorZ() < toCorrelate.get(i - 1).getErrorZ())
				{
					temp = toCorrelate.get(i);
					toCorrelate.set(i - 1, toCorrelate.get(i));
					toCorrelate.set(i, temp);

					sorted = false;
				}
			}
		}
	}

	/*
	 * The actual correlation of the variables goes as follows:
	 */
	public void correlateXvariables(SourceDataModel correlated, ArrayList<SourceDataModel> toCorrelate)
	{

		//Value to denote if there were even any valid variables to correlate.
		boolean nothingDoing = true;
		double variable = 0;
		double toDivideBy = 0;

		//This loop correlates the positionX's of the sources.
		for(int i = 0; i < toCorrelate.size(); i++)
		{
			//Checks to make sure that the current source even has a positionX.
			if(toCorrelate.get(i).getPositionX() != null)
			{
				/*
				 * Add to the current variable value of positionX the positionX of the current source multiplied
				 * by the errorX of the source opposite it in the list, then add that errorX to toDivideBy for
				 * averaging later. I.e. the first source in the list will have its poitionX multiplied by the
				 * errorX of the last source in the list, the second source in the list will have its positionX
				 * multiplied by the errorX of the second-to-last source in the list, etc. This will effectively
				 * give a source with less error chances more leverage.
				 *
				 * For example:
				 *
				 * If you have sources
				 * 1. positionX = 7.3
				 *    errorX = 0.8
				 * 2. positionX = 6.1
				 *    errorX = 0.3
				 *
				 * Since source 2 has a lesser error value, its position value should be more important. The
				 * calculations will be:
				 *
				 * variable = (positionX of source 1 * errorX of source 2) + (positionX of source 2 * errorX of source 1)
				 * variable = (7.3 * 0.3) + (6.1 * 0.8) = 2.19 + 4.88 = 7.07
				 * toDivideBy = errorX of source 1 + errorX of source 2
				 * toDivideBy = 0.8 + 0.3 = 1.1
				 * correlated positionX = variable / toDivideBy
				 * correlated positionX = 7.07 / 1.1 = 6.427
				 *
				 * Note how the correlated positionX is closer to the positionX of source 2 than source 1, as it
				 * should be because it has the lesser chance of error.
				 */
				variable = variable + (toCorrelate.get(i).getPositionX() * toCorrelate.get(toCorrelate.size() - i - 1).getErrorX());
				toDivideBy = toDivideBy + toCorrelate.get(toCorrelate.size() - i - 1).getErrorX();
				nothingDoing = false;
			}
		}

		//In the end, if no variables were correlated, it stays null in the correlated source.
		if(!nothingDoing)
		{
			correlated.setPositionX(new Double(variable / toDivideBy));
		}

		nothingDoing = true;
		variable = 0;
		toDivideBy = 0;

		//Same loop algorithm is used for speedX.
		for(int i = 0; i < toCorrelate.size(); i++)
		{
			if(toCorrelate.get(i).getSpeedX() != null)
			{
				variable = variable + (toCorrelate.get(i).getSpeedX() * toCorrelate.get(toCorrelate.size() - i - 1).getErrorX());
				toDivideBy = toDivideBy + toCorrelate.get(toCorrelate.size() - i - 1).getErrorX();
				nothingDoing = false;
			}
		}

		if(!nothingDoing)
		{
			correlated.setSpeedX(new Double(variable / toDivideBy));
		}
	}

	//See correlateXvariables and replace all instances of 'x' with 'y'
	public void correlateYvariables(SourceDataModel correlated, ArrayList<SourceDataModel> toCorrelate)
	{
		boolean nothingDoing = true;
		double variable = 0;
		double toDivideBy = 0;

		for(int i = 0; i < toCorrelate.size(); i++)
		{
			if(toCorrelate.get(i).getPositionY() != null)
			{
				variable = variable + (toCorrelate.get(i).getPositionY() * toCorrelate.get(toCorrelate.size() - i - 1).getErrorY());
				toDivideBy = toDivideBy + toCorrelate.get(toCorrelate.size() - i - 1).getErrorY();
				nothingDoing = false;
			}
		}

		if(!nothingDoing)
		{
			correlated.setPositionY(new Double(variable / toDivideBy));
		}

		nothingDoing = true;
		variable = 0;
		toDivideBy = 0;

		for(int i = 0; i < toCorrelate.size(); i++)
		{
			if(toCorrelate.get(i).getSpeedY() != null)
			{
				variable = variable + (toCorrelate.get(i).getSpeedY() * toCorrelate.get(toCorrelate.size() - i - 1).getErrorY());
				toDivideBy = toDivideBy + toCorrelate.get(toCorrelate.size() - i - 1).getErrorY();
				nothingDoing = false;
			}
		}

		if(!nothingDoing)
		{
			correlated.setSpeedY(new Double(variable / toDivideBy));
		}
	}

	//See correlateXvariables and replace all instances of 'x' with 'z'
	public void correlateZvariables(SourceDataModel correlated, ArrayList<SourceDataModel> toCorrelate)
	{
		boolean nothingDoing = true;
		double variable = 0;
		double toDivideBy = 0;

		for(int i = 0; i < toCorrelate.size(); i++)
		{
			if(toCorrelate.get(i).getPositionZ() != null)
			{
				variable = variable + (toCorrelate.get(i).getPositionZ() * toCorrelate.get(toCorrelate.size() - i - 1).getErrorZ());
				toDivideBy = toDivideBy + toCorrelate.get(toCorrelate.size() - i - 1).getErrorZ();
				nothingDoing = false;
			}
		}

		if(!nothingDoing)
		{
			correlated.setPositionZ(new Double(variable / toDivideBy));
		}

		nothingDoing = true;
		variable = 0;
		toDivideBy = 0;

		for(int i = 0; i < toCorrelate.size(); i++)
		{
			if(toCorrelate.get(i).getSpeedZ() != null)
			{
				variable = variable + (toCorrelate.get(i).getSpeedZ() * toCorrelate.get(toCorrelate.size() - i - 1).getErrorZ());
				toDivideBy = toDivideBy + toCorrelate.get(toCorrelate.size() - i - 1).getErrorZ();
				nothingDoing = false;
			}
		}

		if(!nothingDoing)
		{
			correlated.setSpeedZ(new Double(variable / toDivideBy));
		}
	}

	/*
	 * This method correlates the targets threatLevel using effectively the same loop logic as the
	 * correlation of X, Y and Z variables but instead of using the explained algorithm using error values,
	 * it simply takes the averages of all sources.
	 */
	public void correlateOthers(SourceDataModel correlated, ArrayList<SourceDataModel> toCorrelate)
	{
		boolean nothingDoing = true;
		double variable = 0;
		int toDivideBy = 0;

		for(int i = 0; i < toCorrelate.size(); i++)
		{
			if(toCorrelate.get(i).getThreatLevel() != null)
			{
				variable = variable + toCorrelate.get(i).getAltitude();
				toDivideBy++;
				nothingDoing = false;
			}
		}

		if(!nothingDoing)
		{
			correlated.setAltitude(new Double(variable / toDivideBy));
		}

		nothingDoing = true;
		int variableI = 0;
		toDivideBy = 0;

		for(int i = 0; i < toCorrelate.size(); i++)
		{
			if(toCorrelate.get(i).getThreatLevel() != null)
			{
				variableI = variableI + toCorrelate.get(i).getThreatLevel();
				toDivideBy++;
				nothingDoing = false;
			}
		}

		if(!nothingDoing)
		{
			correlated.setThreatLevel(new Integer(variableI / toDivideBy));
		}

		nothingDoing = true;
		String correlatedType = "";

		for(int i = 0; i < toCorrelate.size(); i++)
		{
			if(toCorrelate.get(i).getTrackType() != null)
			{
				correlatedType = correlatedType + toCorrelate.get(i).getTrackType() + "/";
				nothingDoing = false;
			}
		}

		if(!nothingDoing)
		{
			correlated.setTrackType(correlatedType.substring(0, correlatedType.length() - 2));
		}

		nothingDoing = true;
		correlatedType = "";

		for(int i = 0; i < toCorrelate.size(); i++)
		{
			if(toCorrelate.get(i).getTrackCategory() != null)
			{
				correlatedType = toCorrelate.get(i).getTrackCategory();
				nothingDoing = false;
			}
		}

		if(!nothingDoing)
		{
			correlated.setTrackCategory(correlatedType.substring(0, correlatedType.length() - 0));
		}

		nothingDoing = true;
		correlatedType = "";

		for(int i = 0; i < toCorrelate.size(); i++)
		{
			if(toCorrelate.get(i).getTrackPlatform() != null)
			{
				correlatedType = /*correlatedType + */toCorrelate.get(i).getTrackPlatform()/* + "/"*/;
				nothingDoing = false;
			}
		}

		if(!nothingDoing)
		{
			correlated.setTrackPlatform(correlatedType.substring(0, correlatedType.length() /*- 2*/));
		}

	}
}
