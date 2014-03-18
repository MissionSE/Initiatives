package com.missionse.graphicplugin;


public class GraphicController
{
	GraphicRepository graphicRepository;
	GraphicPresentation graphicPresentation;

	public GraphicController(GraphicRepository dataSource, GraphicPresentation graphicPresentation)
	{
		this.graphicRepository = dataSource;
		this.graphicPresentation = graphicPresentation;
	}

	public GraphicRepository getgraphicDataSource()
	{
		return graphicRepository;
	}

	public GraphicPresentation getgraphicPresentation()
	{
		return graphicPresentation;
	}

	public void update()
	{
	}
}
