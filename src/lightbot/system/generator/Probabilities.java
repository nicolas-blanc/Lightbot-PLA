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

	final private int range = 12;

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
	 * @return the range
	 */
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
		default:
			System.out.println("The instruction does not exist!");
		}
		
		//printProba();
	}
	
	/**
	 * 
	 */
	private void printProba() {
		System.out.println("Light : " + probaLight + " / Forward : " + probaForward  + " / Jump : " + probaJump  + " / Left : " + probaLeft + " / Right : " + probaRight);
	}
	
}
