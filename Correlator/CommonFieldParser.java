package datacorrelation;

import java.util.ArrayList;

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
    public Source correlateSources(ArrayList<Source> sources)
    {
	//All correlated sources in the program are created with a identification of 3CS.
	Source correlated = new Source("3CS");
	//Create storage for unused sources during steps of the correlation process.
	ArrayList<Source> unused = new ArrayList<Source>();

	//See methods for what the methods do.
	sortOutUncorrelatableXData(sources, unused);
	sortByErrorX(sources);
	correlateXvariables(correlated, sources);

	//Reset the arrays for correlation of Y variables.
	sources.addAll(unused);
	unused.clear();

	//Rinse for Y
	sortOutUncorrelatableYData(sources, unused);
	sortByErrorY(sources);
	correlateYvariables(correlated, sources);

	//Reset the arrays for correlation of Z variables.
	sources.addAll(unused);
	unused.clear();

	//Repeat for Z
	sortOutUncorrelatableZData(sources, unused);
	sortByErrorZ(sources);
	correlateZvariables(correlated, sources);

	//Reset the source array for the correlation of other non-XYZ variables.
	sources.addAll(unused);

	correlateOthers(correlated, sources);

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
    private void sortOutUncorrelatableXData(ArrayList<Source> sources, ArrayList<Source> unused)
    {
	Source temp;

	for(int i = 0; i < sources.size(); i++)
	{
	    if(sources.get(i).getErrorX() == null)
	    {
		temp = sources.remove(i);
		unused.add(temp);
		i--;
	    }
	}

	if(sources.isEmpty())
	{
	    sources.addAll(unused);

	    for(Source s : sources)
	    {
		s.setErrorX(new Double(1));
	    }
	}
    }

    //See description for sortOutUncorrelatableXData, replacing any instance of 'x' to 'y'.
    private void sortOutUncorrelatableYData(ArrayList<Source> sources, ArrayList<Source> unused)
    {
	Source temp;

	for(int i = 0; i < sources.size(); i++)
	{
	    if(sources.get(i).getErrorY() == null)
	    {
		temp = sources.remove(i);
		unused.add(temp);
		i--;
	    }
	}

	if(sources.isEmpty())
	{
	    sources.addAll(unused);

	    for(Source s : sources)
	    {
		s.setErrorY(new Double(1));
	    }
	}
    }

    //See description for sortOutUncorrelatableXData, replacing any instance of 'x' to 'z'.
    private void sortOutUncorrelatableZData(ArrayList<Source> sources, ArrayList<Source> unused)
    {
	Source temp;

	for(int i = 0; i < sources.size(); i++)
	{
	    if(sources.get(i).getErrorZ() == null)
	    {
		temp = sources.remove(i);
		unused.add(temp);
		i--;
	    }
	}

	if(sources.isEmpty())
	{
	    sources.addAll(unused);

	    for(Source s : sources)
	    {
		s.setErrorZ(new Double(1));
	    }
	}
    }

    /*
     * This method sorts the given sources by their error x's for smallest to largest (for the
     * use of this algoritm, it doesnt matter) using a bubble sort algorithm.
     */
    private void sortByErrorX(ArrayList<Source> sources)
    {
	boolean sorted = false;
	Source temp;

	while(!sorted)
	{
	    sorted = true;

	    for(int i = 1; i < sources.size(); i++)
	    {
		if(sources.get(i).getErrorX() < sources.get(i - 1).getErrorX())
		{
		    temp = sources.get(i);
		    sources.set(i - 1, sources.get(i));
		    sources.set(i, temp);

		    sorted = false;
		}
	    }
	}
    }

    //See description for sortByErrorX, replacing any instance of 'x' to 'y'.
    private void sortByErrorY(ArrayList<Source> sources)
    {
	boolean sorted = false;
	Source temp;

	while(!sorted)
	{
	    sorted = true;

	    for(int i = 1; i < sources.size(); i++)
	    {
		if(sources.get(i).getErrorY() < sources.get(i - 1).getErrorY())
		{
		    temp = sources.get(i);
		    sources.set(i - 1, sources.get(i));
		    sources.set(i, temp);

		    sorted = false;
		}
	    }
	}
    }

    //See description for sortByErrorX, replacing any instance of 'x' to 'z'.
    private void sortByErrorZ(ArrayList<Source> sources)
    {
	boolean sorted = false;
	Source temp;

	while(!sorted)
	{
	    sorted = true;

	    for(int i = 1; i < sources.size(); i++)
	    {
		if(sources.get(i).getErrorZ() < sources.get(i - 1).getErrorZ())
		{
		    temp = sources.get(i);
		    sources.set(i - 1, sources.get(i));
		    sources.set(i, temp);

		    sorted = false;
		}
	    }
	}
    }

    /*
     * The actual correlation of the variables goes as follows:
     */
    public void correlateXvariables(Source correlate, ArrayList<Source> sources)
    {
    	
    	//Value to denote if there were even any valid variables to correlate.
	boolean nothingDoing = true;
	double variable = 0;
	double toDivideBy = 0;

	//This loop correlates the positionX's of the sources.
	for(int i = 0; i < sources.size(); i++)
	{
		//Checks to make sure that the current source even has a positionX.
	    if(sources.get(i).getPositionLatitude() != null)
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
		variable = variable + (sources.get(i).getPositionLatitude() * sources.get(sources.size() - i - 1).getErrorX());
		toDivideBy = toDivideBy + sources.get(sources.size() - i - 1).getErrorX();
		nothingDoing = false;
	    }
	}

	//In the end, if no variables were correlated, it stays null in the correlated source.
	if(!nothingDoing)
	{
	    correlate.setPositionLatitude(new Double(variable / toDivideBy));
	}

	nothingDoing = true;
	variable = 0;
	toDivideBy = 0;

	//Same loop algorithm is used for speedX.
	for(int i = 0; i < sources.size(); i++)
	{
	    if(sources.get(i).getSpeedX() != null)
	    {
		variable = variable + (sources.get(i).getSpeedX() * sources.get(sources.size() - i - 1).getErrorX());
		toDivideBy = toDivideBy + sources.get(sources.size() - i - 1).getErrorX();
		nothingDoing = false;
	    }
	}

	if(!nothingDoing)
	{
	    correlate.setSpeedX(new Double(variable / toDivideBy));
	}
    }

    //See correlateXvariables and replace all instances of 'x' with 'y'
    public void correlateYvariables(Source correlate, ArrayList<Source> sources)
    {
	boolean nothingDoing = true;
	double variable = 0;
	double toDivideBy = 0;

	for(int i = 0; i < sources.size(); i++)
	{
	    if(sources.get(i).getPositionLongitude() != null)
	    {
		variable = variable + (sources.get(i).getPositionLongitude() * sources.get(sources.size() - i - 1).getErrorY());
		toDivideBy = toDivideBy + sources.get(sources.size() - i - 1).getErrorY();
		nothingDoing = false;
	    }
	}

	if(!nothingDoing)
	{
	    correlate.setPositionLongitude(new Double(variable / toDivideBy));
	}

	nothingDoing = true;
	variable = 0;
	toDivideBy = 0;

	for(int i = 0; i < sources.size(); i++)
	{
	    if(sources.get(i).getSpeedY() != null)
	    {
		variable = variable + (sources.get(i).getSpeedY() * sources.get(sources.size() - i - 1).getErrorY());
		toDivideBy = toDivideBy + sources.get(sources.size() - i - 1).getErrorY();
		nothingDoing = false;
	    }
	}

	if(!nothingDoing)
	{
	    correlate.setSpeedY(new Double(variable / toDivideBy));
	}
    }

    //See correlateXvariables and replace all instances of 'x' with 'z'
    public void correlateZvariables(Source correlate, ArrayList<Source> sources)
    {
	boolean nothingDoing = true;
	double variable = 0;
	double toDivideBy = 0;

	for(int i = 0; i < sources.size(); i++)
	{
	    if(sources.get(i).getPositionAltitude() != null)
	    {
		variable = variable + (sources.get(i).getPositionAltitude() * sources.get(sources.size() - i - 1).getErrorZ());
		toDivideBy = toDivideBy + sources.get(sources.size() - i - 1).getErrorZ();
		nothingDoing = false;
	    }
	}

	if(!nothingDoing)
	{
	    correlate.setPositionAltitude(new Double(variable / toDivideBy));
	}

	nothingDoing = true;
	variable = 0;
	toDivideBy = 0;

	for(int i = 0; i < sources.size(); i++)
	{
	    if(sources.get(i).getSpeedZ() != null)
	    {
		variable = variable + (sources.get(i).getSpeedZ() * sources.get(sources.size() - i - 1).getErrorZ());
		toDivideBy = toDivideBy + sources.get(sources.size() - i - 1).getErrorZ();
		nothingDoing = false;
	    }
	}

	if(!nothingDoing)
	{
	    correlate.setSpeedZ(new Double(variable / toDivideBy));
	}
    }

    /*
     * This method correlates the targets threatLevel and fuel using effectively the same loop logic as the
     * correlation of X, Y and Z variables but instead of using the explained algorithm using error values,
     * it simply takes the averages of all sources.
     */
    public void correlateOthers(Source correlate, ArrayList<Source> sources)
    {
	boolean nothingDoing = true;
	double variable = 0;
	int toDivideBy = 0;

	for(int i = 0; i < sources.size(); i++)
	{
	    if(sources.get(i).getFuel() != null)
	    {
		variable = variable + sources.get(i).getFuel();
		toDivideBy++;
		nothingDoing = false;
	    }
	}

	if(!nothingDoing)
	{
	    correlate.setFuel(new Double(variable / toDivideBy));
	}

	nothingDoing = true;
	int variableI = 0;
	toDivideBy = 0;

	for(int i = 0; i < sources.size(); i++)
	{
	    if(sources.get(i).getThreatLevel() != null)
	    {
		variableI = variableI + sources.get(i).getThreatLevel();
		toDivideBy++;
		nothingDoing = false;
	    }
	}

	if(!nothingDoing)
	{
	    correlate.setThreatLevel(new Integer(variableI / toDivideBy));
	}

	nothingDoing = true;
	String correlatedType = "";

	for(int i = 0; i < sources.size(); i++)
	{
	    if(sources.get(i).getTrackType() != null)
	    {
		correlatedType = correlatedType + sources.get(i).getTrackType() + "/";
		nothingDoing = false;
	    }
	}

	if(!nothingDoing)
	{
	    correlate.setTrackType(correlatedType.substring(0, correlatedType.length() - 2));
	}

	nothingDoing = true;
	correlatedType = "";

	for(int i = 0; i < sources.size(); i++)
	{
	    if(sources.get(i).getTrackCategory() != null)
	    {
		correlatedType = correlatedType + sources.get(i).getTrackCategory() + "/";
		nothingDoing = false;
	    }
	}

	if(!nothingDoing)
	{
	    correlate.setTrackCategory(correlatedType.substring(0, correlatedType.length() - 2));
	}

	nothingDoing = true;
	correlatedType = "";

	for(int i = 0; i < sources.size(); i++)
	{
	    if(sources.get(i).getTrackPlatform() != null)
	    {
		correlatedType = correlatedType + sources.get(i).getTrackPlatform() + "/";
		nothingDoing = false;
	    }
	}

	if(!nothingDoing)
	{
	    correlate.setTrackPlatform(correlatedType.substring(0, correlatedType.length() - 2));
	}

    }
}
