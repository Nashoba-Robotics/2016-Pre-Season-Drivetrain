package edu.nr.robotics.subsystems.climb.norequire;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.subsystems.climb.Elevator;

/**
 *
 */
public class ElevatorNoRequireVoltageCommand extends NRCommand {

	double val;
	
    public ElevatorNoRequireVoltageCommand(double val) {
    	this.val = val;
    	requires(ResetElevatorSubsystem.getInstance());
    }

    // Called just before this Command runs the first time
    @Override
	protected void onStart() {
    	Elevator.getInstance().setMotorValue(val);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinishedNR() {
        return false;
    }
}
