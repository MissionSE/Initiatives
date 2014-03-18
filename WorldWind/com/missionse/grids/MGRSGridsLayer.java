package com.missionse.grids;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;

import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.Earth.MGRSGraticuleLayer;

public class MGRSGridsLayer
{
		AppFrame frame;
		MGRSGraticuleLayer graticuleLayer;

		public MGRSGridsLayer(AppFrame frame)
		{
			this.frame = frame;
			try
			{
				createMGRSGridsLayer();
			}
			catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/*
	        String labelText = null;
	        if (UTMBaseGraticuleLayer.GRATICULE_UTM.equals(graticuleType))
	            labelText = "Global UTM";
	        else if (MGRSGraticuleLayer.GRATICULE_UTM_GRID.equals(graticuleType))
	            labelText = "UTM Grid";
	        else if (MGRSGraticuleLayer.GRATICULE_100000M.equals(graticuleType))
	            labelText = "100km";
	        else if (MGRSGraticuleLayer.GRATICULE_10000M.equals(graticuleType))
	            labelText = "10km";
	        else if (MGRSGraticuleLayer.GRATICULE_1000M.equals(graticuleType))
	            labelText = "1km";
	        else if (MGRSGraticuleLayer.GRATICULE_100M.equals(graticuleType))
	            labelText = "100m";
	        else if (MGRSGraticuleLayer.GRATICULE_10M.equals(graticuleType))
	            labelText = "10m";
	        else if (MGRSGraticuleLayer.GRATICULE_1M.equals(graticuleType))
	            labelText = "1m";
		 */

		private void createMGRSGridsLayer() throws IllegalAccessException
		{
			try
			{
				graticuleLayer = MGRSGraticuleLayer.class.newInstance();
			}
			catch (Exception e)
			{
				System.out.println("Can't get a graticule layer " + e);
			}
			if (graticuleLayer != null)
			{
				graticuleLayer.setEnabled(true);
				insertLayer(graticuleLayer);
			}
		}
		
		private void insertLayer(Layer layer)
		{
				frame.insertBeforePlacenames(frame.getWwd(), layer);
				frame.getLayerPanel().update(frame.getWwd());
		}
}
