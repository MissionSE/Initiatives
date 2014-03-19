package com.mse.trackhooknotifier;

import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlWriter {
    
    protected String dbString = "jdbc:sqlite:/tmp/tdfTracks.db";
    protected String driver = "org.sqlite.JDBC";
    static final String EMPTY_TRACK = "'', '', '', '', '', '', '', '', ''";

    public SqlWriter(String sqlDB) {

	dbString = sqlDB;
	try {
	    Class.forName(driver);
	}
	catch(ClassNotFoundException e) {
	    System.err.println("Unable to find SQLite driver");
	    System.exit(1);
	}

	Connection conn = null;
	try {
	    conn = connect();
	    Statement statement = conn.createStatement();
	    statement.setQueryTimeout(10);
	    statement.executeUpdate("drop table if exists track_hooks");
	    statement.executeUpdate("create table track_hooks( "
				    + "id integer,"
				    + "tn text,"
				    + "category text,"
				    + "ident text,"
				    + "platform text,"
				    + "latitude text,"
				    + "longitude text,"
				    + "speed text,"
				    + "altitude text,"
				    + "course);");
	    statement.executeUpdate("insert into track_hooks "
				    +"values(1, " + EMPTY_TRACK + ")");
	    statement.executeUpdate("insert into track_hooks "
				    +"values(2, " + EMPTY_TRACK + ")");

	}
	catch(SQLException e) {
	    System.err.println(e.getMessage());
	}
	finally {
	    close(conn);
	}
    }
    
    public SqlWriter() {
	this("jdbc:sqlite:/tmp/tdfTracks.db");
    }

    private Connection connect() {
	try {
	    Properties prop = new Properties();
	    prop.setProperty("journal_mode", "WAL");
	    Connection conn = DriverManager.getConnection(dbString, prop);
	    return conn;
	}
	catch(SQLException e) {
	    System.err.println(e.getMessage());
	    return null;
	}
    }

    private boolean close(Connection conn) {
	try {
	    if (conn != null) {
		conn.close();
	    }
	    return true;
	}
	catch(SQLException e) {
	    System.err.println(e.getMessage());
	    return false;
	}

    }
    public StringBuffer buildTrackSqlBuffer(TrackData td) {

	StringBuffer sb = new StringBuffer();
	sb.append("UPDATE track_hooks ");
	sb.append("SET tn = '" + td.getTrackNumber() + "',"
		  +" category = '" + td.getCategory() + "',"
		  +" ident = '" + td.getIdentity() + "',"
		  +" platform = '" + td.getPlatform() + "',"
		  +" latitude = '" + td.getLatitude() + "',"
		  +" longitude= '" + td.getLongitude() + "',"
		  +" speed = '" + td.getSpeed() + "',"
		  +" altitude = '" + td.getAltitude() + "',"
		  +" course = '" + td.getCourse() + "' ");

	if (TrackData.PRI_HOOK == td.getHookType() ) {
	    sb.append("WHERE id = 1;");
	}
	else if (TrackData.SEC_HOOK == td.getHookType() ) {
	    sb.append("WHERE id = 2;");
	}
	return sb;
    } 


    public boolean executeTrackWrite(TrackData td) {
	
	Connection conn = null;
	try {
	    conn = connect();
	    StringBuffer sb = buildTrackSqlBuffer(td);
	    System.err.println(sb.toString());
	    Statement statement = conn.createStatement();
	    statement.executeUpdate(sb.toString());
	    return true;
	}
	catch(SQLException e) {
	    return false;
	}
	finally {
	    //System.err.println("SqlWriter::executeTrackWrite - closing connection");
	    close(conn);
	}
    }
}
