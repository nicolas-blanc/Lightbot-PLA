/**
 * 
 */
package lightbot.system.generator;

/**
 * @author Nasheis
 *
 */
public class Probabilities {
	private int probaLight;
	private int probaForward;
	private int probaJump;
	private int probaRight;
	private int probaLeft;
	private int probaPattern;
	private int probaPointer;
	private int probaITE;
	
	final private int rangeBase = 12;
	final private int range = 17;
	
	/**
	 * 
	 */
	public Probabilities() {
		initProba();
	}
	
	/**
	 * @return the probaLight
	 */
	public int getProbaLight() {
		return probaLight;
	}

	/**
	 * @return the probaForward
	 */
	public int getProbaForward() {
		return probaForward;
	}

	/**
	 * @return the probaJump
	 */
	public int getProbaJump() {
		return probaJump;
	}

	/**
	 * @return the probaRight
	 */
	public int getProbaRight() {
		return probaRight;
	}

	/**
	 * @return the probaLeft
	 */
	public int getProbaLeft() {
		return probaLeft;
	}
	
	/**
	 * @return the probaPattern
	 */
	public int getProbaPattern() {
		return probaPattern;
	}

	/**
	 * @return the probaPointer
	 */
	public int getProbaPointer() {
		return probaPointer;
	}

	/**
	 * @return the probaITE
	 */
	public int getProbaITE() {
		return probaITE;
	}

	/**
	 * @return the range of the base of the game
	 */
	public int getRangeBase() {
		return rangeBase;
	}
	
	/**
	 * @return the range of the base, plus the pattern
	 */
	public int getRangePattern() {
		return rangeBase + 4;
	}
	
	public int getRange() {
		return range;
	}

	/**
	 * 
	 */
	public void initProba() {
		probaLight = 2;
		probaForward = 6;
		probaJump = 10;
		probaRight= 11;
		probaLeft = 12;
		probaPattern = 16;
		probaPointer = 17;
		probaITE = 18;
	}
	

	/**
	 * Update the probabilities of the element depending on the previous instruction
	 * @param instruction The current instruction used to create the cell
	 */
	public void setUpProbalities(int instruction) {
//		System.out.println("action in proba :" + instruction);
		
		switch(instruction){
		case 0:
			initProba();
			break;
		case 1:
			probaLight++;
			probaForward--;
			break;
		case 2:
			probaLight++;
			probaJump--;
			break;
		case 3:
			probaLight++;
			probaRight--;
			break;
		case 4:
			probaLight++;
			probaLeft--;
			break;
		case 5:
			break;
		default:
			System.out.println("The instruction does not exist!");
		}
		
		//printProba();
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private void printProba() {
		System.out.println("Light : " + probaLight + " / Forward : " + probaForward  + " / Jump : " + probaJump  + " / Left : " + probaLeft + " / Right : " + probaRight);
	}
	
}
