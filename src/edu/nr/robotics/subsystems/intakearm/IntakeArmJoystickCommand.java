package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.OI;

/**
 *
 */
public class IntakeArmJoystickCommand extends NRCommand {

    public IntakeArmJoystickCommand() {
        requires(IntakeArm.getInstance());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void onExecute() {
    	IntakeArm.getInstance().setMotor(OI.getInstance().getIntakeArmMoveValue());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
}
