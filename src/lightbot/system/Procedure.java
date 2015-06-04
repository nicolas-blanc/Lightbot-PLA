package lightbot.system;

import java.util.ArrayList;
import java.util.List;

import lightbot.system.action._Action;


public class Procedure {
	
	String name;
	int maxInstructions;
	List<_Action> actionList;
	
	/**
	 * Procedure constructor with a name and the max number of instructions
	 * @param name
	 * @param maxInstructions
	 */
	public Procedure(String name, int maxInstructions){
		this.actionList = new ArrayList(maxInstructions);
		this.name = name;
	}
		
	/**
	 * Adds an action in the procedure
	 * @param action
	 * @return
	 */
	public boolean addAction(_Action action){
		if(isFull()){
			return false;
		}
		else{
			this.actionList.add(action);
			return true;
		}
	}
	
	/**
	 * Adds an action at insertIndex in the procedure
	 * @param action
	 * @param insertIndex
	 * @return
	 */
	public boolean addAction(_Action action, int insertIndex){
		if(insertIndex < 0 || insertIndex > this.actionList.size() || isFull()){
			return false;
		}
		else{
			this.actionList.add(insertIndex, action);
			return true;
		}
	}
	
	/**
	 * Deletes an action from the procedure
	 * @param actionIndex
	 * @return
	 */
	public boolean deleteAction(int actionIndex){
		if(actionIndex < 0 || actionIndex > this.actionList.size()){
			return false;
		}
		else{
			this.actionList.remove(actionIndex);
			return true;
		}
	}
	
	/**
	 * Deletes all the actions contained in the list
	 */
	public void deleteAllActions(){
		this.actionList.clear();
	}
	
	/**
	 * Checks if the list is full
	 */
	private boolean isFull(){
		return this.actionList.size()==maxInstructions;
	}
	
	
	
}
