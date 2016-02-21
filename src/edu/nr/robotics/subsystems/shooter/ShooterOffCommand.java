package edu.nr.robotics.subsystems.shooter;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class ShooterOffCommand extends NRCommand {

    public ShooterOffCommand() {
    	requires(Shooter.getInstance());
    }

	@Override
	protected void onStart() {
		if(Shooter.getInstance().isPIDEnable()) {
			Shooter.getInstance().pid.disable();
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
