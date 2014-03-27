package com.missionse.datafusionframeworklibrary.dataassociationlibrary;

// source file link.java

public class link {
	public int value; // value of element
	public link next; // reference to next

	// constructor
	public link(int n, link ln) {
		value = n;
		next = ln;
	}

	public static void main(String args[]) {
		// initialize list head
		link head = null;

		// add some entries to list
		for (int i = 1; i <= 10; i++)
			head = new link(i, head);

		// dump out entries
		link p = head;
		while (p != null) {
			System.out.println(p.value);
			p = p.next;
		}
	}
}
