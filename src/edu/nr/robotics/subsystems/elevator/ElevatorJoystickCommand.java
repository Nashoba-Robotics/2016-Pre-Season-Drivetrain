package edu.nr.robotics.subsystems.elevator;

import edu.nr.lib.CMD;
import edu.nr.robotics.OI;

/**
 *
 */
public class ElevatorJoystickCommand extends CMD {

    public ElevatorJoystickCommand() {
        requires(Elevator.getInstance());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void onExecute() {
    	Elevator.getInstance().setMotorValue(OI.getInstance().getElevatorMoveValue());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
}
