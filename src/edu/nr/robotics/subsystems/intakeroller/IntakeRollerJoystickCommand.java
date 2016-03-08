package edu.nr.robotics.subsystems.intakeroller;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.OI;

/**
 *
 */
public class IntakeRollerJoystickCommand extends NRCommand {

    public IntakeRollerJoystickCommand() {
        requires(IntakeRoller.getInstance());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void onExecute() {
    	IntakeRoller.getInstance().setRollerSpeed(OI.getInstance().getIntakeRollerMoveValue());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinishedNR() {
        return false;
    }
}
