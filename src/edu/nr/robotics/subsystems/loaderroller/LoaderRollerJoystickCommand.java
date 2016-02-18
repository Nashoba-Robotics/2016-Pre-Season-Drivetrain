package edu.nr.robotics.subsystems.loaderroller;

import edu.nr.lib.CMD;
import edu.nr.robotics.OI;

/**
 *
 */
public class LoaderRollerJoystickCommand extends CMD {

    public LoaderRollerJoystickCommand() {
        requires(LoaderRoller.getInstance());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void onExecute() {
    	LoaderRoller.getInstance().setLoaderSetpoint(OI.getInstance().getLoaderRollerMoveValue());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
}
