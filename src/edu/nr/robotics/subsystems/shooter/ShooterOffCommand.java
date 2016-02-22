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
}
