package com.missionse.datafusionframeworklibrary.datafusionlibrary;

/*
 * Class Number manages the composite track numbers.
 */
public class Number {
	private int[] aryNums;
	private int max = 20;
	private int next;
	private int last;
	
	public Number(){

		// initialize composite numbers
		next = 1;
		last = max - 1;				

		aryNums = new int[max];
		
		// initialize array numbers
		for (int n = 0; n < max-1; n++) {
			aryNums[n] = n+1;
		}
		
		// initialize end of list
		aryNums[max-1] = -1;				
		
	}

	public int getNum(){

        // get next available number
		int n = next;
 
		// if number is available
        if (n >= 1 && n <= max){
        	
        	// set next available
        	next = aryNums[n];
        	aryNums[n] = 0;
        	
            // if no more numbers available
        	if (-1 == next)
        		
        		// indicate all numbers used
        		last = -1;
        	
        }
        // else return no number available
        else return -1;
      // return new composite track number  
      return n;  
	}

	public void deleteNum(int n){

        if (-1 == last)
        	next = n;
        else
        	aryNums[last] = n;
        
        last = n;
        aryNums[n] = -1;

	}

}
