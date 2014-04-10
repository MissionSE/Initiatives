package com.missionse.dbutils;


//JDBC/MySQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseAccess {
	// Lock Objects
	private static final Object loadLock = new Object();
	private static final Object dbConnectLock = new Object();

	// MySQL stuff
	private static Connection geoConnection = null;
	private static Boolean driver_loaded = false; // loadDriver

	public DatabaseAccess() {
		loadDriver();
		createDbConnections();
	}

	private void loadDriver() {
		synchronized(loadLock) {
			if (driver_loaded == false) {	
				try {
					// load MySQL JDBC driver
					// newInstance() may not be necessary
					Class.forName("com.mysql.jdbc.Driver").newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("MySQL JDBC driver loaded.");
				driver_loaded = true;
			}
		}
	}
	private void createDbConnections() {
		synchronized(dbConnectLock) {
			String host;
			String database;
			String user;
			String password;

			if (geoConnection == null)
			{
				// geo database
				host = "localhost";
				database = "geo";
				user = "mseuser";
				password = "mseuser";

				geoConnection = mysqlConnect(host, database, user, password);
			}

			// additional database connections
		}
	}

	private Connection mysqlConnect(String host, String database, String user, String password){

		Connection conn = null;

		String connectionString = "jdbc:mysql://" + host + "/" + database + "?user=" + user +
				"&password=" + password;

		// How should this work? A list of connections to different databases?

		try {
			conn = DriverManager.getConnection(connectionString);
			System.out.println("Connected to "+ host + ":" + database + " as " + user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}

	public ResultSet getTrackHistory (String trackid) {
		//System.out.println("called getTrackHistory");

		if (geoConnection == null) {
			System.out.println("DataAccess::getTrackHistory no database connection");
			return null;
		}

		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			//statement = geoConnection.createStatement();
			String query = "Select * FROM trackhistory WHERE trackid=? ORDER BY id"; // ORDER BY time
			statement = geoConnection.prepareStatement(query);
			statement.setInt(1, Integer.parseInt(trackid));
			resultSet = statement.executeQuery();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return resultSet;
	}
	public ResultSet getGeoPoints() throws Exception {
		System.out.println("DataAccess:getGeoPoints called.");

		// Make sure we're connected to the database.
		if (geoConnection == null) {
			System.out.println("DatabaseAccess:getGeoPoints: conn object is null");
			return null;
		}
		//String resultString = new String();
		ResultSet resultSet = null;
		Statement statement;
		try {
			statement = geoConnection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM geopoints");
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return resultSet;
	}

	public ResultSet getTrackPosition (int track_id) {
		ResultSet resultSet = null;
		String query = null;

		try {

			if (track_id == -1) {
				query = "SELECT * FROM trackposition";
				Statement statement = geoConnection.createStatement();
				resultSet = statement.executeQuery(query);
			} else {
				query = "SELECT * FROM trackposition WHERE trackid = ?";
				PreparedStatement statement = geoConnection.prepareStatement(query);
				statement.setInt(1, track_id);
				resultSet = statement.executeQuery();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
	public ResultSet getCircles (int type) {
		ResultSet resultSet = null;
		String query = null;
		try {
			if (type == -1) {
				query = "Select * FROM circles";
				Statement statement = geoConnection.createStatement();
				resultSet = statement.executeQuery(query);
			} else {
				query = "SELECT * FROM circles WHERE type = ?";
				PreparedStatement statement = geoConnection.prepareStatement(query);
				statement.setInt(1,  type);
				resultSet = statement.executeQuery();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultSet;
	}
	public void updateTrackPosition(int track_id, double moving_lat, double moving_lng, double moving_alt, int moving_heading) {

		try {
			String query = "INSERT INTO trackposition (trackid, lat, lng, alt, heading) VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE lat=?, lng=?, alt=?, heading=?";
			// For the on duplicate update can I use more ?s
			PreparedStatement statement = geoConnection.prepareStatement(query);
			statement.setInt(1, track_id);
			statement.setDouble(2, moving_lat);
			statement.setDouble(3, moving_lng);
			statement.setDouble(4, moving_alt);
			statement.setDouble(5, moving_heading);
			statement.setDouble(6, moving_lat);
			statement.setDouble(7, moving_lng);
			statement.setDouble(8, moving_alt);
			statement.setDouble(9, moving_heading);
			statement.executeUpdate();
			//System.out.println("trackposition insert returned " + r);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// TODO make a generic statement and prepared statement functions
	
	public void addHistoryPosition(int trackid, double lat, double lng, double alt) {
		try {
			String query = "INSERT INTO trackhistory (id, trackid, time, lat, lng, alt) VALUES (NULL, ?, NULL, ?, ?, ?)";
			PreparedStatement statement = geoConnection.prepareStatement(query);
			statement.setInt(1, trackid);
			statement.setDouble(2, lat);
			statement.setDouble(3, lng);
			statement.setDouble(4, alt);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet checkForUpdate(String table, long since) {
		ResultSet resultSet = null;
		java.sql.Timestamp datetime = new java.sql.Timestamp(since);
		//System.out.println("Checking for updates since " + datetime.toString());
		// TODO
		// in table 'modifications' see if the modification time
		// of the entry for tables is after since.
		try {
			String query = "SELECT * FROM modifications WHERE name=? AND updated_at > ?";
			PreparedStatement statement = geoConnection.prepareStatement(query);
			statement.setString(1, table);
			statement.setTimestamp(2, datetime);
			resultSet = statement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultSet;
	}

	public void clearTrackPositionData() {
		clearTable("trackposition");
	}

	public void clearTrackHistoryData() {
		clearTable("trackhistory"); // TODO: move the tablename somewhere else
	}

	private void clearTable (String tableName) {
		try {
			String query = "DELETE FROM " + tableName;
			Statement statement = geoConnection.createStatement();
			statement.execute(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	public String readDatabase() throws Exception {
		String resultString = new String();
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery("select * from FEEDBACK.COMMENTS");

			while (resultSet.next()) {
				String user = resultSet.getString("myuser");
				String comment = resultSet.getString("comments");

				resultString += user + ":" + comment + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultString;
	}

	public void close() {
		try {
			// close everything
			if (resultSet != null) {
				resultSet.close(); // what does this do?
			}
			if (statement != null) {
				statement.close();
			}
			if (conn != null) {
				conn.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	 */


}
