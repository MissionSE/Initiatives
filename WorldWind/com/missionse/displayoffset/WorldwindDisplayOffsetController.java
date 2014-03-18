package com.missionse.displayoffset;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;

import gov.nasa.worldwind.geom.Position;

public class WorldwindDisplayOffsetController extends DisplayOffsetController
{
	WorldwindDisplayOffsetController(AppFrame parent)
	{
		super(parent);
	}

	public void actionPerformed(java.awt.event.ActionEvent e)
	{
	  System.out.println("WorldwindDisplayOffset::actionPerformed received");
		super.actionPerformed(e);
		this.setRange(this.model.getRange());
	}
	
	public void setRange(double range)
	{
		System.out.println("WorldwindDisplayOffsetController::setRange setting range to:" + range);
		Position currentPosition = this.parent.getWwd().getView().getCurrentEyePosition();
		Position newPosition = new Position(currentPosition.getLatitude(), currentPosition.getLongitude(), range);
		this.parent.getWwd().getView().setEyePosition(newPosition);
		this.parent.getWwd().redraw();
	}
}
