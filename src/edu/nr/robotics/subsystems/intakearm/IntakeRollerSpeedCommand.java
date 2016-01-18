package edu.nr.robotics.subsystems.intakearm;

import edu.nr.robotics.subsystems.loaderroller.LoaderRoller;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeRollerSpeedCommand extends Command {

	double val;
	
    public IntakeRollerSpeedCommand(double val) {
    	this.val = val;
    	requires(LoaderRoller.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	IntakeArm.getInstance().setRollerSetpoint(val);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
