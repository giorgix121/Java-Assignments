package PartIII;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class SortFrequency {

	public static void sortByFrequency(ArrayList<Integer> ar) {
		
	}
	
	public static void main(String[] args) {
		ArrayList<Integer> ar = new ArrayList<Integer>();
		for (int i=0;i<100;i++) {
			ar.add((int)(Math.random()*10));			
		}
		System.out.println(ar.toString());
		sortByFrequency(ar);
		System.out.println(ar.toString());
	}
}
