/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.missionse.datafusionframeworklibrary.datafusionlibrary;

import com.missionse.datafusionframeworklibrary.databaselibrary.DatabaseFactory;
import com.missionse.datafusionframeworklibrary.databaselibrary.DatabaseProvider;
import com.missionse.datafusionframeworklibrary.databaselibrary.SourceDataModel;

/**
 * 
 * @author Team3
 */
public class FinalDriver {

	static DatabaseProvider database = null;
	static PacketReceiver packetReceiver = null;
	// First source will be sent to packetReceiver
	static SourceDataModel source1;
	// Second source will be sent to packetReceiver
	static SourceDataModel source2;
	// Third source will be sent to packetReceiver
	static SourceDataModel source3;

	public static void main(String[] args) throws InterruptedException {
		database = new DatabaseFactory();
		database.setupDatabase("FusionData");

		packetReceiver = new PacketReceiver(database.getSourceDataAccessor(),
				database.getCompositeDataAccessor());
		FinalDriver fd = new FinalDriver();

	}

	public FinalDriver() throws InterruptedException {

		source1 = new SourceDataModel("1", "S1", "platform", "category", 1, .3,
				0.3, 99, 99, 99, 0.4, .1, .1, 40, 0.0, 41.99417, -119.305344,
				.1, 2500, 0.2);

		source2 = new SourceDataModel("2", "S2", "platform", "category", 1,
				0.3, 0.2, 99, 99, 99, 0.3, .1, .1, 40, 0.0, 41.99422,
				-119.305333, .1, 2500, 0.2);

		source3 = new SourceDataModel("3", "S3", "platform", "category", 1,
				0.3, 0.3, 99, 99, 99, 0.4, .1, .1, 40, 0.0, 51.99417,
				-109.305344, .1, 2500, 0.2);

		// source1.setPositionLatitude(0.0);
		// source1.setPositionLongitude(0.0);

		// 99's == source
		// .1 == errors
		// 40 = update hertz

		// source1 = new DriverSource("1", 41.94417, -119.305344, 2500.00);
		// source2 = new DriverSource("2", 41.94422, -119.305333, 2500.00);
		// source3 = new DriverSource("3", 51.94417, -109.305344, 2500.00);

		/*
		 * packetReceiver.recievePacket(source1.toString());
		 * packetReceiver.recievePacket(source2.toString());
		 * packetReceiver.recievePacket(source3.toString());
		 */int counter = 0;

		while (true) {

			if (counter < 20) {
				source1.setPositionLatitude(source1.getPositionLatitude() + .5);
				source1.setPositionLongitude(source1.getPositionLongitude() + .5);

				source2.setPositionLatitude(source2.getPositionLatitude() + .6);
				source2.setPositionLongitude(source2.getPositionLongitude() + .6);

				source3.setPositionLatitude(source3.getPositionLatitude() + .5);
				source3.setPositionLongitude(source3.getPositionLongitude() + .5);

				packetReceiver.recievePacket(source1.toString());
				packetReceiver.recievePacket(source2.toString());
				packetReceiver.recievePacket(source3.toString());

				counter++;

				Thread.sleep(100);
			}
			/**
			 * else { counter = 0;
			 * 
			 * 
			 * source1 = new Source("1", "platform", "category", 1, 0.0, 0.0,
			 * 99, 99, 99, 0.0, .1, .1, 40, 41.99417, -119.305344, .1, 2500,
			 * 0.0);
			 * 
			 * source2 = new Source("1", "platform", "category", 1, 0.0, 0.0,
			 * 99, 99, 99, 0.0, .1, .1, 40, 41.99422, -119.305333, .1, 2500,
			 * 0.0);
			 * 
			 * 
			 * }
			 */

		}

	}
}
