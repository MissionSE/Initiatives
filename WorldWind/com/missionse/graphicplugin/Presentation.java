package com.missionse.graphicplugin;

import com.missionse.worldwind.ApplicationTemplate.AppFrame;

public interface Presentation {
	
	public void initialize(AppFrame parent);
	
	public Presentation getPresentation();

}
