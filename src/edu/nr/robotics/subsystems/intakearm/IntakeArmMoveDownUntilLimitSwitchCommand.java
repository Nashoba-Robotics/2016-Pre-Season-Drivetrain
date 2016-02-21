package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class IntakeArmMoveDownUntilLimitSwitchCommand extends NRCommand {

    public IntakeArmMoveDownUntilLimitSwitchCommand() {
    	requires(IntakeArm.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    protected void onStart() {
    	IntakeArm.getInstance().disable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void onExecute() {
    	IntakeArm.getInstance().setMotor(-0.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return IntakeArm.getInstance().isBotLimitSwitchClosed();
    }

    // Called once after isFinished returns true
    protected void onEnd() {
    	IntakeArm.getInstance().enable();
    }
}
