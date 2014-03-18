package com.missionse.trackplugin;

import java.util.Vector;

import com.missionse.graphicplugin.LatLongPoint;

public class RandomTrack implements Track
{
	boolean hooked;
	boolean historyDisplay;
	double altitude;
	double latitude;
	double longitude;
	int trackID;
	int directionalIndicator;
	String alignment;
	String reportingSource;
	Vector<LatLongPoint> historyPoints;
	int historyThrottle;

	public RandomTrack()
	{
		this.hooked = false;
		this.historyThrottle = 0;
		this.historyPoints = new Vector<LatLongPoint>();
		generateRandomTrackID();
		generateRandomAltitude();
		generateRandomPosition();
		generateRandomAlignment();
		generateRandomReportingSource();
		generateRandomDirectionalIndicator();
	}

	public RandomTrack(int trackID)
	{
		this.trackID = trackID;
		generateRandomAltitude();
		generateRandomPosition();
		generateRandomAlignment();
		generateRandomReportingSource();
		generateRandomDirectionalIndicator();
	}

	private void generateRandomTrackID()
	{
		this.trackID = (int) (Math.random() * 1000000);
	}

	private void generateRandomPosition()
	{
		this.latitude = -90 + (int) (Math.random() * ((90 + 90) + 1));
		this.longitude = -180 + (int) (Math.random() * ((180 + 180) + 1));
	}

	private void generateRandomAltitude()
	{
		this.altitude = 20000 + (int) (Math.random() * 30000 + 1);
	}

	private void generateRandomReportingSource()
	{
		int tmp = 1 + (int) (Math.random() * ((2 - 1) + 1));
		if (tmp == 2)
		{
			this.reportingSource = "SPY";
		}
		else
		{
			this.reportingSource = "CND";
		}
	}

	private void generateRandomAlignment()
	{
		int tmp = 1 + (int) (Math.random() * ((4 - 1) + 1));
		if (tmp == 4)
		{
			this.alignment = "HOSTILE";
		}
		else if (tmp == 3)
		{
			this.alignment = "NEUTRAL";
		}
		else if (tmp == 2)
		{
			this.alignment = "FRIEND";
		}
		else
		{
			this.alignment = "UNKNOWN";
		}
	}

	private void generateRandomDirectionalIndicator()
	{
		this.directionalIndicator = 1 + (int) (Math.random() * ((4 - 1) + 1));
	}

	public double getLatitude()
	{
		return this.latitude;
	}

	public double getLongitude()
	{
		return this.longitude;
	}

	public double getAltitude()
	{
		return this.altitude;
	}

	public int getTrackID()
	{
		return this.trackID;
	}

	public String getAlignment()
	{
		return this.alignment;
	}

	public String getReportingSource()
	{
		return this.reportingSource;
	}

	public void printData()
	{
		RandomTrack randomTrack = new RandomTrack();
		System.out.println("Test track " + randomTrack.getTrackID() + " is at latitude " + randomTrack.getLatitude()
				+ " and longitude " + randomTrack.getLongitude() + " flying at altitude " + randomTrack.getAltitude()
				+ " being reported from " + randomTrack.getReportingSource() + " and is considered "
				+ randomTrack.getAlignment());
	}

	public void updatePosition()
	{
		if (this.directionalIndicator == 4)
		{
			this.latitude = this.latitude + .015;
			this.longitude = this.longitude + .012;
		}
		else if (this.directionalIndicator == 3)
		{
			this.latitude = this.latitude + .014;
			this.longitude = this.longitude - .011;
		}
		else if (this.directionalIndicator == 2)
		{
			this.latitude = this.latitude - .017;
			this.longitude = this.longitude + .013;
		}
		else
		{
			this.latitude = this.latitude - .014;
			this.longitude = this.longitude - .012;
		}
		updateDirection();
		updateHistory();
	}

	private void updateHistory()
	{
		this.historyThrottle++;
		if (this.historyThrottle == 5)
		{
			this.historyThrottle = 0;
			historyPoints.add(new LatLongPoint(this.latitude, this.longitude));
			if (historyPoints.size() > 30)
			{
				historyPoints.remove(0);
			}
		}
	}

	protected void updateDirection()
	{
		if (this.getLatitude() >= 90.0)
		{
			this.directionalIndicator = 2;
		}
		if (this.getLatitude() <= -90.0)
		{
			this.directionalIndicator = 3;
		}
		if (this.getLongitude() >= 180.0)
		{
			this.directionalIndicator = 1;
		}
		if (this.getLongitude() <= -180.0)
		{
			this.directionalIndicator = 4;
		}
	}

	public void display()
	{
	}

	public void update()
	{
	}

	public void hook()
	{
		this.hooked = true;
	}

	public void unhook()
	{
		this.hooked = false;
	}

	public void toggleHistory()
	{
		this.historyDisplay = !this.historyDisplay;
	}

	public boolean isHistoryDisplayed()
	{
		return this.historyDisplay;
	}
}
