package com.missionse.datafusionframeworklibrary.datafusionlibrary;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.missionse.datafusionframeworklibrary.databaselibrary.Database;
import com.missionse.datafusionframeworklibrary.databaselibrary.SourceDataModel;

/*
 * Class PacketReciever is the main input class for this program. It takes in packets of data
 * in the form of strings through the method recievePacket, creates/updates a Source object
 * with the given data, calls the CommonFieldParser to correlate it with other Sources and sends
 * off the data to other parts of the program.
 *
 * This class was designed to handle any number of sources and deal with any and all irregular
 * or erroneous input.
 *
 * @author Matt Asfar
 */
public class PacketReceiver
{
    //Reference to the CorrelateSources, which is used to correlate the data from the Sources.
    CorrelateSources cs;
    //Reference to the PackSupportingData, which receives data once it has been through this module.
    PackSupportingData psd;
    //Reference to the Database, which receives data once it has been through this module.
    Database db;
    //The list of currently observed Sources.
    private ArrayList<SourceDataModel> sources;
    //The number of variables that this program uses to represent a Source.
    private int numSourceVariables;

    /*
     * Constructor for PacketReciever
     *
     * 1 - Creates a new instance of Database and CommonFieldParser for use with this module.
     * 2 - Sets the ObjectRefinementModule to null, as it will be created later.
     * 3 - Creates a new ArrayList for storage of Sources.
     */
    public PacketReceiver()
    {
	//1
	try {
		db = new Database();
		db.setupDatabase("SSO");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//2
	    cs = new CorrelateSources();
        psd = new PackSupportingData(db);
	//3
        sources = new ArrayList<SourceDataModel>();

	/*
	 * This line of code sets numSourceVariables to however many class variables make up the
	 * Source class. This used to be a static final variable in class Source, but now is set
	 * dynamically at runtime so that any alterations to class Source will be automatically
	 * reflected instead of counting the variables, reassigning the static field, etc.
	 */
	numSourceVariables = SourceDataModel.class.getDeclaredFields().length;
    }

    //For database use.
    public void end() {
        try {
            db.shutdown();
        } catch (SQLException ex) {
            Logger.getLogger(PacketReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * This is the method where the packet, in the form of a String, is received.
     * The String received holds values given from the source separated by commas.
     *
     * Given Strings must be in the format:
     * "Sensor's unique identification string,
     * Latitude position of the sensor,
     * Longitude position of the sensor,
     * Altitude position of the sensor,
     * Error X value of the sensor,
     * Error Y value of the sensor,
     * Error Z value of the sensor,
     * The update hertz of the sensor,
     * Latitude position of what the sensor is sensing,
     * Longitude position of what the sensor is sensing,
     * Altitude position of what the sensor is sensing,
     * Speed X of what the sensor is sensing,
     * Speed Y of what the sensor is sensing,
     * Speed Z of what the sensor is sensing,
     * The threat level of the what the sensor is sensing,
     * The string track type of what the sensor is sensing: Air, Surface or Subsurface,
     * The string track platform of what the sensor is sensing, depending on the track type,
     * The string track category of what the sensor is sensing, depending on the track type,
     * The level of fuel remaining for what the sensor is sensing"
     */
    public void recievePacket(String data)
    {
        
	//Parse the data into an array of Strings, deliminated by commas.
        String[] parsedData = data.split(",", -1);

        System.out.println("receivePacket data: "+data);

        /*
	 * If the amount of given data does not match up with the amount of data this program uses
	 * to represent sources or if the data does not contain an unique identifier, something has
	 * gone wrong and the data is ignored.
	 */
        System.out.println("receivePacket parsedData.length: "+parsedData.length);
        System.out.println("receivePacket numSourceVariables: "+numSourceVariables); 
        
        if(parsedData.length != numSourceVariables || parsedData[0].compareTo("") == 0)
        {
            return;
        }

	//Storage for, and retrieval of, the Source this data represents.
        SourceDataModel toUpdate = searchExistingSources(parsedData[0]);
        System.out.println("receivePacket sources: "+sources);

	/*
	 * If this program is not yet observing a Source with the given unique identification,
	 * a new one will be created.
	 */
        if(toUpdate == null)
        {
            toUpdate = createNewSource(parsedData[0]);
        }

	/*
	 * Whether the data represents an already existing source or a newly created one,
	 * update the source with.
	 */
        toUpdate.update(parsedData);

	//Correlate all observed data into a correlated source.
        SourceDataModel correlated = cs.correlateSources(toUpdate, sources);

        System.out.println("receivePacket correlated: "+correlated);

        //Send off the newly updated source and the correlated one to the rest of the program.
	    sendUpdates(toUpdate, correlated);
    }

    /*
     * This method searches through the sources that already exist, looking for one
     * with the given unique ID. If no such source is found, it returns null.
     */
    private SourceDataModel searchExistingSources(String id)
    {
        for(SourceDataModel s : sources)
        {
            if(s.getUniqueId().compareTo(id) == 0)
            {
                return s;
            }
        }

        return null;
    }

    /*
     * This method creates, returns and adds to the list of observed sources a new Source
     * with the given identification string. A source created this way has nothing else
     * set yet, effectively making it empty.
     */
    private SourceDataModel createNewSource(String id)
    {
        SourceDataModel newSource = new SourceDataModel(id);
        sources.add(newSource);
        System.out.println("receivePacket:createNewSource sources: "+sources);
        return newSource;
    }

    /*
     * Once this class has done everything it needs to do, this method allows it to send off data to
     * other sections of the program.
     */
    private void sendUpdates(SourceDataModel toUpdate, SourceDataModel correlated)
    {
	//Here the newly updated source and the correlated source are sent to be saved by the Database.
	try
	{
	    System.out.println("receivePacket:sendUpdates toUpdate = "+ toUpdate);
	    db.updateSourceBuilder(toUpdate.clone());
	    System.out.println("receivePacket:sendUpdates correlated = "+ correlated);
	    db.updateSourceBuilder(correlated.clone());
	}
	catch(Exception e)
	{}
    System.out.println("receivePacket:sendUpdates correlated = "+ correlated);
    System.out.println("receivePacket:sendUpdates sources = "+ sources);   
    psd.packSupportingData(toUpdate, correlated, sources);
    }

}
