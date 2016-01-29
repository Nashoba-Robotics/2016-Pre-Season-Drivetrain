package edu.nr.robotics.subsystems.shooter;

import edu.nr.lib.CMD;

/**
 *
 */
public class ShooterOffCommand extends CMD {

    public ShooterOffCommand() {
    	requires(Shooter.getInstance());
    }

	@Override
	protected void onStart() {
		if(Shooter.getInstance().isPIDEnable()) {
			Shooter.getInstance().setSetpoint(0);
		} else {
			Shooter.getInstance().setMotor(0);
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
