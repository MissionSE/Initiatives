package com.missionse.displayoffset;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;


public class DisplayOffsetController implements java.awt.event.ActionListener
{
	AppFrame parent;
	DisplayOffsetModel model;
	DisplayOffsetView view;

	DisplayOffsetController(AppFrame parent)
	{
		this.parent = parent;
	}

	public void actionPerformed(java.awt.event.ActionEvent e)
	{
		System.out.println("DisplayOffsetController::actionPerformed Got GUI action performed: " + e.getActionCommand());
		if (e.getActionCommand() == "Range In")
		{
			this.model.setRange(this.model.getRange()/2);
		}
		if (e.getActionCommand() == "Range Out")
		{
			this.model.setRange(this.model.getRange()*2);
		}
	}

	public void addModel(DisplayOffsetModel m)
	{
		this.model = m;
	}

	public void addView(DisplayOffsetView v)
	{
		this.view = v;
	}

	public void initModel(int x)
	{
		model.setRange(x);
	}

	public void setRange(double range)
	{
	}
}
