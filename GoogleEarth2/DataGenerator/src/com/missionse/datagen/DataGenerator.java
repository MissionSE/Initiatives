package com.missionse.datagen;

public class DataGenerator {

	// Change this to use dependency injection
	MovingTracksDataGenerator moving_tracks = null;

	public DataGenerator () {

	}


	public void generateData() {
		// Create data generator objects
		// and start threads.
		try {
			moving_tracks = new MovingTracksDataGenerator();
		} catch (Exception e) {
			e.printStackTrace();
		}
		moving_tracks.start();
		System.out.println("MovingTracksDataGenerator started");


	}

}
