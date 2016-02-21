package edu.nr.robotics.subsystems.climb;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.OI;

/**
 *
 */
public class ClimbJoystickCommand extends NRCommand {

    public ClimbJoystickCommand() {
        requires(Climb.getInstance());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void onExecute() {
    	Climb.getInstance().setMotorValue(OI.getInstance().getElevatorMoveValue());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
}
