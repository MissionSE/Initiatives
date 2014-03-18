package com.missionse.grids;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;
import com.missionse.worldwind.ApplicationTemplate;
import com.missionse.worldwind.Plugin;

public class GridsPlugin implements Plugin
{
	ApplicationTemplate.AppFrame parent;
	LatLongGridsLayer latLongLayer;
	MGRSGridsLayer mgrsGridsLayer;

	public String getPluginName()
	{
			return "GridsPlugin";
	}

	public void initialize(AppFrame parent)
	{
		this.parent = parent;
		this.latLongLayer = new LatLongGridsLayer(parent);
		this.mgrsGridsLayer = new MGRSGridsLayer(parent);
	}

	public void update()
	{
    return;
	}

	public void hookUpdate(int hashCode)
	{
    return;
	}

}
