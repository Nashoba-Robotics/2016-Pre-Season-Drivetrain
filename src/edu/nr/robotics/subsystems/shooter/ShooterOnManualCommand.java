package edu.nr.robotics.subsystems.shooter;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class ShooterOnManualCommand extends NRCommand {

	double val;
	
    public ShooterOnManualCommand(double val) {
    	this.val = val;
    	requires(Shooter.getInstance());
    }

	@Override
	protected void onStart() {
		Shooter.getInstance().setMotor(val);

	}
}
