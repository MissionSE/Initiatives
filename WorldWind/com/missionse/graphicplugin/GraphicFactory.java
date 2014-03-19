package com.missionse.graphicplugin;


public interface GraphicFactory
{
	public GraphicPresentation getGraphicPresentation();

	public GraphicController getGraphicController();

	public GraphicRepository getGraphicRepository();

	public GraphicHookManager getGraphicHookManager();
	
	public Circle createCircle(double radius, double latitude, double longitude);
}
