package edu.nr.robotics.subsystems.climb.norequire;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.OI;

public class WaitForElevatorNoRequireStoppedForTwoSeconds extends NRCommand {

	long startTime = -1;
		
	boolean isFinished = false;
	
	@Override
	protected void onExecute() {
		if(startTime != -1) {
			if(OI.getInstance().getElevatorMoveValue() == 0) {
				if(System.currentTimeMillis() - startTime > 3000) {
					isFinished = true;
				}
			} else {
				startTime = System.currentTimeMillis();
			}
		} else {
			if(OI.getInstance().getElevatorMoveValue() != 0) {
				startTime = System.currentTimeMillis();
			}
		}
	}
	
	@Override
	protected void onEnd() {
		isFinished = false;
		startTime = -1;
	}
	
	@Override
	protected boolean isFinishedNR() {
		return isFinished;
	}

}
