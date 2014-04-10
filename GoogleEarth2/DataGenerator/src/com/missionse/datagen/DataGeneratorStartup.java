/**
 * 
 */
package com.missionse.datagen;

/**
 * @author Shimony
 *
 */
public class DataGeneratorStartup {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Create and start the generator
		try {
			final DataGenerator dg = new DataGenerator();
			dg.generateData();
			
			//TestThread tt = new TestThread();
			//tt.begin();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
