package edu.nr.robotics.subsystems.climb.norequire;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.subsystems.climb.Elevator;

/**
 *
 */
public class ElevatorNoRequireWaitUntilChangedByCommand extends NRCommand {
    
   	double value;
   	
   	double startValue;
		
    public ElevatorNoRequireWaitUntilChangedByCommand(double value) {
    	this.value = value;
    	requires(ResetElevatorSubsystem.getInstance());
    }
    
   	@Override
    public void onStart() {
    	startValue = Elevator.getInstance().getEncoder();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinishedNR() {
        return Math.abs(Elevator.getInstance().getEncoder() - startValue) > value;
    }
}
