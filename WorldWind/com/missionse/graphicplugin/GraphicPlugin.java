package com.missionse.graphicplugin;

import com.missionse.worldwind.ApplicationTemplate;
import com.missionse.worldwind.Plugin;

public class GraphicPlugin implements Plugin
{
	ApplicationTemplate.AppFrame parent;
	GraphicController graphicController;
	GraphicFactory graphicFactory;
	GraphicHookManager graphicHookManager;
	
	OverlayEntryController overlayEntryController;
  OverlayEntryModel overlayEntryModel;
  OverlayEntryView overlayEntryView;
	
	public GraphicPlugin()
	{
	}

	public String getPluginName()
	{
		return "GraphicPlugin";
	}

	public void update()
	{
		this.graphicController.update();
	}

	public void hookUpdate(int hashCode)
	{
		if (hashCode != -1)
		{
			this.graphicHookManager.hook(hashCode);
		}
		else
		{
			this.graphicHookManager.unhook();
		}
	}

	public void initialize(com.missionse.worldwind.ApplicationTemplate.AppFrame parent)
	{
		this.parent = parent;
		this.graphicFactory = new WorldwindGraphicFactory(parent);
		this.graphicController = this.graphicFactory.getGraphicController();
		this.graphicHookManager = this.graphicFactory.getGraphicHookManager();
    createOverlayEntryGUI();
	}

	private void createOverlayEntryGUI()
	{
		this.overlayEntryModel = new OverlayEntryModel();
		this.overlayEntryView = new OverlayEntryView();
		this.overlayEntryModel.addObserver(this.overlayEntryView);
		this.overlayEntryController = new OverlayEntryController(this.graphicFactory);
		this.overlayEntryController.addModel(this.overlayEntryModel);
		this.overlayEntryController.addView(this.overlayEntryView);
		this.overlayEntryView.addController(this.overlayEntryController);		
	}

	/*public void drawShape(ShapeType shape) {

		switch (shape)
		{
		case CIRCLE:
			System.out.printf("drawShape::CIRCLE\n");
			WorldwindCircle circle = new WorldwindCircle();		
			this.graphicFactory.getGraphicDataSource().addShape(circle);
			circle.draw();
			break;
		case SQUARE:
			WorldwindSquare square = new WorldwindSquare();
			this.graphicFactory.getGraphicDataSource().addShape(square);
			square.draw();
			break;
		default:
			break;

		}
	}

	public void deleteShape(int hashCode) {

		if (hashCode != -1)
		{
			this.graphicController.graphicDataSource.getShapeFromHashCode(hashCode);
		}
	}

	public void editShape(int hashCode) {

		if (hashCode != -1)
		{
			 this.graphicController.graphicDataSource.getShapeFromHashCode(hashCode);
		}
		
	}*/

}
