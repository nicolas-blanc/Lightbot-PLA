/**
 * 
 */
package lightcore.simulator.generator;

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
	 * Create Probabilities for the random generation
	 */
	public Probabilities() {
		initProba();
	}
	
	/**
	 * The probabilities of light, initialization : 2/range
	 * @return the probaLight
	 */
	public int getProbaLight() {
		return probaLight;
	}

	/**
	 * The probabilities of forward, initialization : 4/range
	 * @return the probaForward
	 */
	public int getProbaForward() {
		return probaForward;
	}

	/**
	 * The probabilities of jump, initialization : 4/range
	 * @return the probaJump
	 */
	public int getProbaJump() {
		return probaJump;
	}

	/**
	 * The probabilities of right turn, initialization : 1/range
	 * @return the probaRight
	 */
	public int getProbaRight() {
		return probaRight;
	}

	/**
	 * The probabilities of left turn, initialization : 1/range
	 * @return the probaLeft
	 */
	public int getProbaLeft() {
		return probaLeft;
	}
	
	/**
	 * The probabilities of use the pattern, initialization : 4/range
	 * @return the probaPattern
	 */
	public int getProbaPattern() {
		return probaPattern;
	}

	/**
	 * The probabilities of use pointers, initialization : 1/range
	 * @return the probaPointer
	 */
	public int getProbaPointer() {
		return probaPointer;
	}

	/**
	 * The probabilities of if then else, initialization : 1/range
	 * @return the probaITE
	 */
	public int getProbaITE() {
		return probaITE;
	}

	/**
	 * The range of the base of the game (Light, Forward, Jump, Turn), current : 12
	 * @return the range of the base of the game
	 */
	public int getRangeBase() {
		return rangeBase;
	}
	
	/**
	 * The range of the base plus the pattern, current : 16
	 * @return the range of the base, plus the pattern
	 */
	public int getRangePattern() {
		return rangeBase + 4;
	}
	
	/**
	 * The range with all extension, current : 18
	 * @return the range with all extension in the generator
	 */
	public int getRange() {
		return range;
	}

	/**
	 * Initialize the probabilities, see getters for the probabilities
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
//			System.out.println("Utilisation d'un pattern");
			break;
		case 6:
//			System.out.println("Utilisation d'un pointers");
			break;
		default:
//			System.out.println("The instruction does not exist!");
		}
		
//		printProba();
	}
	
	/**
	 * Print the probabilities in the console
	 */
	@SuppressWarnings("unused")
	private void printProba() {
		//System.out.println("Light : " + probaLight + " / Forward : " + probaForward  + " / Jump : " + probaJump  + " / Left : " + probaLeft + " / Right : " + probaRight);
	}
	
}
