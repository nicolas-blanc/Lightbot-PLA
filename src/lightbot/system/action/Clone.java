package lightbot.system.action;

import lightbot.LightCore;
import lightbot.graphics.Game;
import lightbot.system.Colour;
import lightbot.system.Robot;
import lightbot.system._Executable;
import lightbot.system.world.Grid;
import lightbot.system.world.OutOfGridException;
import lightbot.system.world.Position;
import lightbot.system.world.cell.EmptyCell;
import lightbot.system.world.cell.FullCell;

public class Clone extends _Action{

	public Clone() {
		super(Colour.WHITE);
	}
	
	public Clone(Colour c){
		super(c);
	}
	
	/**
	 * 
	 * @param l : line position
	 * @param c : column position
	 */
	public void setClone(int l, int c){
		Robot.wheatleyClone.setPosition(l, c);
	}
	
	public void killClone(){
		Robot.wheatleyClone.setVisibility(false);
	}

	@Override
	public void execute(Grid grid, Robot robot) throws OutOfGridException {
		Position clonePosition = findClonePosition(grid, robot);
		if(clonePosition == null)
			throw new OutOfGridException();
		Robot.wheatleyClone.setPosition(clonePosition.getX(), clonePosition.getY());
		Robot.wheatleyClone.setVisibility(true);
		
	}
	
	public Position firstCheckPosition(Grid grid, int posX, int posY, int level, boolean less){
		Position pos = null;
		if(less){
			if(!(posX < 0 || posY < 0  || grid.getCell(posX, posY) instanceof EmptyCell 
					|| (grid.getCell(posX, posY) instanceof FullCell && ((FullCell)grid.getCell(posX, posY)).getHeight() != level))){
				pos = new Position(posX, posY);
			}
		}
		else{
			if(!(posX > grid.getSize() || posY > grid.getSize() || grid.getCell(posX, posY) instanceof EmptyCell 
					|| (grid.getCell(posX, posY) instanceof FullCell && ((FullCell)grid.getCell(posX, posY)).getHeight() != level))){
				pos = new Position(posX, posY);
			}
		}
		return pos;
	}
	
	public Position secondCheckPosition(Grid grid, int posX, int posY, int level, boolean less){
		Position pos = null;
		if(less){
			if(!(posX < 0 || posY < 0 || grid.getCell(posX, posY) instanceof EmptyCell 
					|| (grid.getCell(posX, posY) instanceof FullCell && (Math.abs(((FullCell)grid.getCell(posX, posY)).getHeight() - level)) > 1 ))){
				pos = new Position(posX, posY);
			}
		}
		else{
			if(!(posX > grid.getSize() || posY > grid.getSize() || grid.getCell(posX, posY) instanceof EmptyCell 
					|| (grid.getCell(posX, posY) instanceof FullCell && (Math.abs(((FullCell)grid.getCell(posX, posY)).getHeight() - level)) > 1 ))){
				pos = new Position(posX, posY);
			}
		}
		return pos;
	}
	
	public Position findClonePosition(Grid grid, Robot robot){
		Position clonePos = null;
		int posX = robot.getLine();
		int posY = robot.getColumn();
		int level = ((FullCell)grid.getCell(posX, posY)).getHeight();
		switch(robot.getDirection()){
			case EAST:
				clonePos = firstCheckPosition(grid, posX-1, posY, level, true); 
				if(clonePos == null){
					clonePos = firstCheckPosition(grid, posX+1, posY, level, false);
					if(clonePos == null){
						clonePos = firstCheckPosition(grid, posX, posY+1, level, false);
						if(clonePos == null){
							clonePos = firstCheckPosition(grid, posX, posY-1, level, true);
							if(clonePos == null){
								
								// second possibilities
								clonePos = secondCheckPosition(grid, posX-1, posY, level, true);
								if(clonePos == null){
									clonePos = secondCheckPosition(grid, posX+1, posY, level, false);
									if(clonePos == null){
										clonePos = secondCheckPosition(grid, posX, posY+1, level, false);
										if(clonePos == null){
											clonePos = secondCheckPosition(grid, posX, posY-1, level, true);
											if(clonePos == null){
												System.out.println("Oh God ! It's a trap !");
											}
										}
									}
								}
							}	
						}
					}
				}
				break;
			case NORTH:
				clonePos = firstCheckPosition(grid, posX, posY-1, level, true); 
				if(clonePos == null){
					clonePos = firstCheckPosition(grid, posX, posY+1, level, false);
					if(clonePos == null){
						clonePos = firstCheckPosition(grid, posX-1, posY, level, true);
						if(clonePos == null){
							clonePos = firstCheckPosition(grid, posX+1, posY, level, false);
							if(clonePos == null){
								
								// second possibilities
								clonePos = secondCheckPosition(grid, posX, posY-1, level, true);
								if(clonePos == null){
									clonePos = secondCheckPosition(grid, posX, posY+1, level, false);
									if(clonePos == null){
										clonePos = secondCheckPosition(grid, posX-1, posY, level, true);
										if(clonePos == null){
											clonePos = secondCheckPosition(grid, posX+1, posY, level, false);
											if(clonePos == null){
												System.out.println("Oh God ! It's a trap !");
											}
										}
									}
								}
							}	
						}
					}
				}
				break;
			case SOUTH:
				clonePos = firstCheckPosition(grid, posX, posY+1, level, false); 
				if(clonePos == null){
					clonePos = firstCheckPosition(grid, posX, posY-1, level, true);
					if(clonePos == null){
						clonePos = firstCheckPosition(grid, posX+1, posY, level, false);
						if(clonePos == null){
							clonePos = firstCheckPosition(grid, posX-1, posY, level, true);
							if(clonePos == null){
								
								// second possibilities
								clonePos = secondCheckPosition(grid, posX, posY+1, level, false);
								if(clonePos == null){
									clonePos = secondCheckPosition(grid, posX, posY-1, level, true);
									if(clonePos == null){
										clonePos = secondCheckPosition(grid, posX+1, posY, level, false);
										if(clonePos == null){
											clonePos = secondCheckPosition(grid, posX-1, posY, level, true);
											if(clonePos == null){
												System.out.println("Oh God ! It's a trap !");
											}
										}
									}
								}
							}	
						}
					}
				}
				break;
			case WEST:
				
				clonePos = firstCheckPosition(grid, posX+1, posY, level, false); 
				if(clonePos == null){
					clonePos = firstCheckPosition(grid, posX-1, posY, level, true);
					if(clonePos == null){
						clonePos = firstCheckPosition(grid, posX, posY-1, level, true);
						if(clonePos == null){
							clonePos = firstCheckPosition(grid, posX, posY+1, level, false);
							if(clonePos == null){
								
								// second possibilities
								clonePos = secondCheckPosition(grid, posX+1, posY, level, false);
								if(clonePos == null){
									clonePos = secondCheckPosition(grid, posX-1, posY, level, true);
									if(clonePos == null){
										clonePos = secondCheckPosition(grid, posX, posY-1, level, true);
										if(clonePos == null){
											clonePos = secondCheckPosition(grid, posX, posY+1, level, false);
											if(clonePos == null){
												System.out.println("Oh God ! It's a trap !");
											}
										}
									}
								}
							}	
						}
					}
				}
				break;
			default:
				break;
		}
		
		return clonePos;
	}

	@Override
	public _Executable cloneWithNewColor(_Executable e, Colour newColor) {
		return new Clone();
	}
	
	
}
