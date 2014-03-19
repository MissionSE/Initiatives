package com.missionse.datafusionframeworklibrary.dataassociationlibrary;

import java.util.ArrayList;

/*
 * Class Sort will receive an array of associated candidate data.
 * The list will be sorted in statistical order.
 */
public class Sort {

	public Sort() {
	}

	/*
	 * This method sorts the given sources by their range difference for
	 * smallest to largest using a bubble sort algorithm.
	 */
	public ArrayList<Candidate> sortList(ArrayList<Candidate> candidateList) {
		boolean sorted = false;
		Candidate temp;

		while (!sorted) {
			sorted = true;

			for (int i = 1; i < candidateList.size(); i++) {
				if (candidateList.get(i).getRangeDiff() < candidateList.get(
						i - 1).getRangeDiff()) {
					temp = candidateList.get(i);
					candidateList.set(i - 1, candidateList.get(i));
					candidateList.set(i, temp);

					sorted = false;
				}
			}
		}
		System.out.println("sort candList: "+candidateList);

		return candidateList;

	}

}
