package edu.nr.robotics.subsystems.shooter;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class ShooterSpeedCommand extends NRCommand {

	double goalSpeed;
	
	double kP = 0.75;
	double kF = 1;
	
    public ShooterSpeedCommand(double goalSpeed) {
    	this.goalSpeed = goalSpeed;
    	requires(Shooter.getInstance());
    }
    
    @Override
	protected void onExecute() {
    	
    	double speed = Shooter.getInstance().getScaledSpeed();
    	double p = kP * (goalSpeed - speed);
    	double f = kF * goalSpeed;
    	
    	double output = p + f;
    	
    	Shooter.getInstance().setMotor(output);
	}
	
	@Override
	protected void onEnd() {
		Shooter.getInstance().setMotor(0);
	}
	
	@Override
	protected boolean isFinishedNR() {
		return false;
	}
}
