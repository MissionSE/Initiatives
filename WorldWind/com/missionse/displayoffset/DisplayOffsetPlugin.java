package com.missionse.displayoffset;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;
import com.missionse.worldwind.ApplicationTemplate;
import com.missionse.worldwind.Plugin;

public class DisplayOffsetPlugin implements Plugin
{
	ApplicationTemplate.AppFrame parent;
	
	DisplayOffsetController controller;
	DisplayOffsetModel model;
	DisplayOffsetView view;
	
	public String getPluginName()
	{
		return "DisplayOffset";
	}

	public void initialize(AppFrame parent)
	{
    this.parent = parent;
    initializeDisplayOffsetGUI();
	}
	
	private void initializeDisplayOffsetGUI()
	{
		this.model = new DisplayOffsetModel();
		this.view = new DisplayOffsetView();
		this.model.addObserver(this.view);
		this.controller = new WorldwindDisplayOffsetController(parent);
		this.controller.addModel(this.model);
		this.controller.addView(this.view);
		this.controller.setRange(this.model.getRange());
		this.controller.initModel(1000000);
		this.view.addController(this.controller);
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
