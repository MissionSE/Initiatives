/**
 * 
 */
package com.missionse.GEUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * @author Shimony
 *
 */
public class ImageUtil {
	private static final String track_icon_file = "trackicon.png";

	public enum ImageType {
		TRACKICON(track_icon_file);

		private String filepath;
		private ImageType(String filepath) {
			this.filepath = filepath;
		}
		private String getFilePath() {
			return this.filepath;
		}
	}

	// Read image from disk and return as an array of bytes.
	public static byte[] getImage(ImageType type) {

		byte[] imageData = null;

		// Read the image from disk
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(type.getFilePath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Convert it to an output stream
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(image,  "png", outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return it as a stream of bytes.
		imageData = outputStream.toByteArray();
		
		System.out.println("Got image " + type.getFilePath() + " from disk, returning");

		return imageData;
	}
}
