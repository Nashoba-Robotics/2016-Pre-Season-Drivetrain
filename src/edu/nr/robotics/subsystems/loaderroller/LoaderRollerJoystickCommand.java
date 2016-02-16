package edu.nr.robotics.subsystems.loaderroller;

import edu.nr.robotics.OI;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LoaderRollerJoystickCommand extends Command {

    public LoaderRollerJoystickCommand() {
        requires(LoaderRoller.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	LoaderRoller.getInstance().setLoaderSetpoint(OI.getInstance().getLoaderRollerMoveValue());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
