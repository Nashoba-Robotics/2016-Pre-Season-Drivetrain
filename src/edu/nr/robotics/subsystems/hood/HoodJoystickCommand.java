package edu.nr.robotics.subsystems.hood;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.OI;

/**
 *
 */
public class HoodJoystickCommand extends NRCommand {

    public HoodJoystickCommand() {
        requires(Hood.getInstance());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void onExecute() {
    	Hood.getInstance().setMotor(OI.getInstance().getHoodMoveValue());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
}
