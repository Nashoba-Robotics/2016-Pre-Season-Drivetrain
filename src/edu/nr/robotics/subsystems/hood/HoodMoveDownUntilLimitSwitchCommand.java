package edu.nr.robotics.subsystems.hood;

import edu.nr.lib.NRCommand;

public class HoodMoveDownUntilLimitSwitchCommand extends NRCommand {

	public HoodMoveDownUntilLimitSwitchCommand() {
    	requires(Hood.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    protected void onStart() {
    	Hood.getInstance().disable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void onExecute() {
    	Hood.getInstance().setMotor(-0.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinishedNR() {
        return Hood.getInstance().isBotLimitSwitchClosed();
    }

    // Called once after isFinished returns true
    @Override
    protected void onEnd(boolean interrupted) {
    	Hood.getInstance().enable();
    }
}
