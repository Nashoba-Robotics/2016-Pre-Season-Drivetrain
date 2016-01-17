package edu.nr.robotics.subsystems.shooter;

import edu.nr.lib.CMD;

/**
 *
 */
public class ShooterOnCommand extends CMD {

	double val;
	
    public ShooterOnCommand(double val) {
    	this.val = val;
    	requires(Shooter.getInstance());
    }

	@Override
	protected void onStart() {
		Shooter.getInstance().setSetpoint(val);
	}

	@Override
	protected void onExecute() {
		
	}

	@Override
	protected void onEnd(boolean interrupted) {
		
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
