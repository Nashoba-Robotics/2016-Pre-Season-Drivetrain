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
		Shooter.getInstance().setSetpoint(0);
		Shooter.getInstance().disable();
		Shooter.getInstance().setMotor(0);
	}
}
