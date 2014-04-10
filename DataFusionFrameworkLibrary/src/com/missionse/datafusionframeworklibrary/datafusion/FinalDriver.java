/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.missionse.datafusionframeworklibrary.datafusion;

import com.missionse.datafusionframeworklibrary.database.DatabaseFactory;
import com.missionse.datafusionframeworklibrary.database.DatabaseProvider;
import com.missionse.datafusionframeworklibrary.database.SourceDataModel;

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
	// Third source will be sent to packetReceiver
	static SourceDataModel source4;

	public static void main(String[] args) throws InterruptedException {
		database = new DatabaseFactory();
		database.setupDatabase("FusionDataRecord");

		packetReceiver = new PacketReceiver(database.getSourceDataAccessor(),
				database.getCompositeDataAccessor());
		FinalDriver fd = new FinalDriver();

	}

	public FinalDriver() throws InterruptedException {

		source1 = new SourceDataModel("1", "S1", "platform", "category", 1, .3,
				0.3, 99, .1, .1, 40, 2500, 41.99417, -119.305344, .1, 2500, 0.2, System.currentTimeMillis());

		source2 = new SourceDataModel("2", "S2", "platform", "category", 1,
				0.3, 0.2, 99, .1, .1, 40, 2500, 41.99422, -119.305333, .1,
				2500, 0.2, System.currentTimeMillis());

		source3 = new SourceDataModel("3", "S3", "platform", "category", 1,
				0.3, 0.3, 99, .1, .1, 40, 2500, 51.99417, -109.305344, .1,
				2500, 0.2, System.currentTimeMillis());

		source4 = new SourceDataModel("4", "S4", "platform", "category", 1,
				0.3, 0.2, 99, .1, .1, 40, 2500, 41.99422, -119.305333, .1,
				2500, 0.2, System.currentTimeMillis());

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

			if (counter < 50) {
				source1.setPositionX(source1.getPositionX() + .01);
				source1.setPositionY(source1.getPositionY() + .01);
				source1.setTime((double) System.currentTimeMillis());

				source2.setPositionX(source2.getPositionX() + .01);
				source2.setPositionY(source2.getPositionY() + .01);
				source2.setTime((double) System.currentTimeMillis());

				source3.setPositionX(source3.getPositionX() + .01);
				source3.setPositionY(source3.getPositionY() + .01);
				source3.setTime((double) System.currentTimeMillis());

				source4.setPositionX(source4.getPositionX() + .01);
				source4.setPositionY(source4.getPositionY() + .01);
				source4.setTime((double) System.currentTimeMillis());

				packetReceiver.recievePacket(source1.toString());
				packetReceiver.recievePacket(source2.toString());
				packetReceiver.recievePacket(source3.toString());
				packetReceiver.recievePacket(source4.toString());

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
