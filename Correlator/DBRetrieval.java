/**
 * DBRetrieval Module
 * 	Module designed for the Output Module of the Data Source Correlation Project.
 * This module's purpose is to retrieve older information from the Database Module.
 * It simply retrieves this information and passes it back to the requesting
 * analysis module.
 * 
 * @author Michael Speer
 * @version 2010.11.30
 */

package datacorrelation;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBRetrieval
{
	//the database used for retrieval
	Database database;
	
	/**
	 * constructor to create the DBRetrieval module. it will require
	 * a database to read from.
	 * 
	 * @param db: the database this instance will use
	 */
	public DBRetrieval(Database db)
	{
		//set the database to be used
		database = db;
	}
	
	/**
	 * gathers all of the different uniqueIds to a list
	 * @return Array list containing uniqueId strings
	 * @throws SQLException database error
	 */
	public ArrayList<String> getUniqueIds() throws SQLException
	{
		return database.fetchUniqueId();
	}
	
	/**
	 * retrieves an uncorrelated source form the database
	 * as requested for the specified time
	 * 
	 * @param time: the time (in ms) back which the user would like to go
	 * @param uniqueId: the uniqueId of the desired data source
	 * @return Source which was stored at the given time
	 * @throws SQLException the database threw an error
	 */
	public Source retrieveSourceDBInfoAtTime(String uniqueId, long time) throws SQLException
	{
            System.out.println("!!!!!!!");
            
		Source s = database.queryBuilder(uniqueId, time);

                System.out.println(s.getUniqueId());

                return s;
	}
}