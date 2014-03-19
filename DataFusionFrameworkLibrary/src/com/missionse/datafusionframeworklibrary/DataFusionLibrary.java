package com.missionse.datafusionframeworklibrary;

import com.missionse.datafusionframeworklibrary.databaselibrary.DatabaseFactory;
import com.missionse.datafusionframeworklibrary.databaselibrary.DatabaseProvider;
import com.missionse.datafusionframeworklibrary.datafusionlibrary.PacketReceiver;

public class DataFusionLibrary {
	static PacketReceiver packetReceiver = null;	
	static DatabaseProvider database = null;
	
	public static void main(String[] args) {

		database = new DatabaseFactory();
		database.setupDatabase("FusionData");
		packetReceiver = new PacketReceiver();

	}

}
