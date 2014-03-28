package com.missionse.datafusionframeworklibrary.dataassociation;

public class Candidate {
	private String uniqueId;
	private Double rangeDiff;

	public Candidate() {
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Double getRangeDiff() {
		return rangeDiff;
	}

	public void setRangeDiff(Double rangeDiff) {
		this.rangeDiff = rangeDiff;
	}

}
