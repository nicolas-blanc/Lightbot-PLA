package lightbot.system.generator;

import java.util.ArrayList;
import java.util.Random;

public class Pattern {
	
	private ArrayList<ArrayList<Integer>> patternList = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> patternList1 = new ArrayList<Integer>();
	private ArrayList<Integer> patternList2 = new ArrayList<Integer>();
	private ArrayList<Integer> patternList3 = new ArrayList<Integer>();
	private ArrayList<Integer> patternList4 = new ArrayList<Integer>();
	
	private Random rand;
	
	public Pattern(){
		
		rand = new Random();
		patternArray();
	}

	private ArrayList<ArrayList<Integer>> patternArray(){
		
		patternList.add(patternList1);
		patternList.add(patternList2);
		patternList.add(patternList3);
		patternList.add(patternList4);
		
		return patternList;
	}
	
	public ArrayList<Integer> givePattern(){
		
		return patternList.get(rand.nextInt(4));
	}
	
	private ArrayList<Integer> pattern1(){
		
		
		patternList1.add(1);
		patternList1.add(1);
		patternList1.add(0);
		patternList1.add(2);
		patternList1.add(4);
		
		return patternList1;
	}
	
	private ArrayList<Integer> pattern2(){
		
		patternList2.add(0);
		patternList2.add(2);
		patternList2.add(1);
		patternList2.add(4);
		patternList2.add(1);
		patternList2.add(3);
		patternList2.add(1);
		
		return patternList2;
	}
	
	private ArrayList<Integer> pattern3(){
		
		patternList3.add(2);
		patternList3.add(0);
		patternList3.add(1);
		patternList3.add(1);
		patternList3.add(3);
		patternList3.add(1);
		patternList3.add(2);
		patternList3.add(4);
		patternList3.add(1);
		patternList3.add(0);
		
		return patternList3;
	}
	
	private ArrayList<Integer> pattern4(){
		
		patternList4.add(1);
		patternList4.add(4);
		patternList4.add(1);
		patternList4.add(0);
		
		return patternList4;
	}


}
