package com.mse.closecontrol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mse.closecontrol.TrackData;

public class SqlReader {
    protected String dbString = "jdbc:sqlite:/tmp/tdfTracks.db";
    protected String driver = "org.sqlite.JDBC";

    public SqlReader(String sqlDB) {

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

    	}
    	catch(SQLException e) {
    	    System.err.println(e.getMessage());
    	}
    	finally {
    	    close(conn);
    	}
    }
    public SqlReader() {
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

    private StringBuffer buildTrackSqlBuffer() {
	    StringBuffer sb = new StringBuffer();
		sb.append("SELECT id, tn, category, ident, platform, latitude, longitude, speed, altitude, course FROM track_hooks ");
		return sb;
	}	 
    public boolean executeTrackRead(TrackData priHook, TrackData secHook) {
    	
    	Connection conn = null;
    	try {
    		TrackData hook = null;
    		conn = connect();
    	    StringBuffer sb = buildTrackSqlBuffer();
    	    Statement statement = conn.createStatement();
    	    statement.setMaxRows(2);
    	    ResultSet rs = statement.executeQuery(sb.toString());
    	    while(rs.next()) {
    	    	if (rs.getInt("id") == 1 ) {
    	    		priHook.setHookType(TrackData.PRI_HOOK);
    	    		hook = priHook;
    	    	}
    	    	else if (rs.getInt("id") == 2) {
    	    		secHook.setHookType(TrackData.SEC_HOOK);
    	    		hook = secHook;
    	    	}
    	    	hook.setTrackNumber(rs.getString("tn"));
    	    	hook.setCategory(rs.getString("category"));
    	    	hook.setIdentity(rs.getString("ident"));
    	    	hook.setPlatform(rs.getString("platform"));
    	    	hook.setLatitude(rs.getString("latitude"));
    	    	hook.setLongitude(rs.getString("longitude"));
    	    	hook.setSpeed(rs.getString("speed"));
    	    	hook.setAltitude(rs.getString("altitude"));
    	    	hook.setCourse(rs.getString("course"));
    	    }
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

}
