package com.missionse.graphicplugin;

public class OverlayEntryController implements java.awt.event.ActionListener
{
	OverlayEntryModel model;
	OverlayEntryView view;
	GraphicFactory factory;
	
	public OverlayEntryController(GraphicFactory factory)
	{
		this.factory = factory;
	}
	
	public void actionPerformed(java.awt.event.ActionEvent e)
	{
		System.out.println("OverlayEntryController::actionPerformed: " + e.getActionCommand());
		if (e.getActionCommand() == "Create")
		{
			//Could use key event listeners to set this later... saving time for now.
			this.model.setLatitude(this.view.getEnteredLatitude());
			this.model.setLongitude(this.view.getEnteredLongitude());
			this.model.setRadius(this.view.getEnteredRadius());
			this.model.sendModelUpdate();
			System.out.println("oec" + this.model.getLatitude() + " " + this.model.getLongitude() + " " + this.model.getRadius());
			factory.createCircle(this.model.getRadius(), this.model.getLatitude(), this.model.getLongitude());
		}
	}

	public void addModel(OverlayEntryModel overlayEntryModel)
	{
    this.model = overlayEntryModel;
	}

	public void addView(OverlayEntryView overlayEntryView)
	{
    this.view = overlayEntryView;
	}

}
