package lightbot.system.generator;

import java.util.Random;

public class Pattern {
	
	private int[] pattern;
	
	private int variation;
	private int numberInstruction;
	
	private int iterate;
	
	final private int numberPattern = 6;
	
	/**
	 * Create a new Pattern with a random pattern for the random generation
	 */
	public Pattern(int maxInstructions) {
		iterate = 0;
		initPattern();
		
		if (numberInstruction == -1) {
			variation = 0;
		} else {
			variation = maxInstructions / (numberInstruction * 2);
		}
		
//		printPattern();
	}

	/**
	 * Reset the iteration in the pattern
	 */
	public void resetIteration() {
		iterate = 0;
	}
	
	/**
	 * Return the next action of the pattern
	 * @return a action with with the code of the generation, -1 if the pattern is finish
	 */
	public int nextAction() {
		try {
			return pattern[iterate++];		
		} catch (ArrayIndexOutOfBoundsException e) {
			return -1;
		}
	}

	/**
	 * Return the number maximum of appearance in the generation 
	 * @return the variation
	 */
	public int getVariation() {
		return variation;
	}

	/**
	 * Return the number of instruction in the pattern
	 * @return the numberInstruction
	 */
	public int getNumberInstruction() {
		return numberInstruction;
	}
	
	/**
	 * Return the end of the pattern
	 * @return true is the pattern is finish, else false
	 */
	public boolean endOfPattern() {
		return iterate >= numberInstruction;
	}
	
	/**
	 * Initialize the pattern of the object
	 */
	private void initPattern() {
		Random rand = new Random();
		int numPattern = rand.nextInt(numberPattern);
		
		switch (numPattern) {
		case 0:
			int temp[] = {1,1,0,2,4};
			pattern = new int[5];
			pattern = temp;
			
			numberInstruction = 5;
			break;
		case 1:
			int temp1[] = {1,1,0};
			pattern = new int[3];
			pattern = temp1;
			
			numberInstruction = 3;
			break;
		case 2:
			int temp2[] = {1,2,1,0};
			pattern = new int[4];
			pattern = temp2;
			
			numberInstruction = 4;
			break;
		case 3:
			int temp3[] = {0,2,1,4,1,3,1};
			pattern = new int[7];
			pattern = temp3;
			
			numberInstruction = 7;
			break;
		case 4:
			int temp4[] = {1,4,1,0};
			pattern = new int[4];
			pattern = temp4;
			
			numberInstruction = 4;
			break;
		case 5:
			int temp5[] = {2,1,0,3};
			pattern = new int[4];
			pattern = temp5;
			
			numberInstruction = 4;
			break;
		default:
			numberInstruction = -1;
			break;
		}
	}
	
	/**
	 * Print in the console the pattern
	 */
	@SuppressWarnings("unused")
	private void printPattern() {
		System.out.print("Pattern : ");
		for (int i = 0; i < pattern.length; i++) {
			System.out.print(pattern[i] + " - ");
		}
		System.out.println();
	}
}
