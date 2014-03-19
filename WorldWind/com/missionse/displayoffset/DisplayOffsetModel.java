package com.missionse.displayoffset;

public class DisplayOffsetModel extends java.util.Observable
{
	private int range = 1000000;

	public DisplayOffsetModel()
	{
	}

	public int getRange()
	{
		return range;
	}

	public void setRange(int range)
	{
		this.range = range;
		setChanged();
		notifyObservers(range);
	}
}
