package edu.nr.robotics.subsystems.shooter;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class ShooterOnCommand extends NRCommand {

	double val;
	boolean PID;
	
    public ShooterOnCommand(double val, boolean PID) {
    	this.val = val;
    	this.PID = PID;
    	requires(Shooter.getInstance());
    }

	@Override
	protected void onStart() {
		if(PID) {
			Shooter.getInstance().setSetpoint(val);
		} else {
			Shooter.getInstance().setMotor(val);
		}
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
