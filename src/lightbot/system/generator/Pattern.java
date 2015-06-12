package lightbot.system.generator;

import java.util.ArrayList;

public class Pattern {
	
	private ArrayList<Integer> patternList = new ArrayList<Integer>();
	
	public ArrayList<Integer> pattern1(){
		
		patternList.add(1);
		patternList.add(1);
		patternList.add(0);
		patternList.add(2);
		patternList.add(4);
		
		return patternList;
	}
	
	public ArrayList<Integer> pattern2(){
		
		patternList.add(0);
		patternList.add(2);
		patternList.add(1);
		patternList.add(4);
		patternList.add(1);
		patternList.add(3);
		patternList.add(1);
		
		return patternList;
	}
	
	public ArrayList<Integer> pattern3(){
		
		patternList.add(2);
		patternList.add(0);
		patternList.add(1);
		patternList.add(1);
		patternList.add(3);
		patternList.add(1);
		patternList.add(2);
		patternList.add(4);
		patternList.add(1);
		patternList.add(0);
		
		return patternList;
	}
	
	public ArrayList<Integer> pattern4(){
		
		patternList.add(1);
		patternList.add(4);
		patternList.add(1);
		patternList.add(0);
		
		return patternList;
	}


}
