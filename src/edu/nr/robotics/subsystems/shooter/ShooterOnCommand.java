package edu.nr.robotics.subsystems.shooter;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class ShooterOnCommand extends NRCommand {

	double val;
	
    public ShooterOnCommand(double val) {
    	this.val = val;
    	requires(Shooter.getInstance());
    }

	@Override
	protected void onStart() {
		Shooter.getInstance().enable();
		Shooter.getInstance().setSetpoint(val);

	}
}
