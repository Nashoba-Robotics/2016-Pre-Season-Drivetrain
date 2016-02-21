package edu.nr.robotics.subsystems.shooter;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.OI;

/**
 *
 */
public class ShooterJoystickCommand extends NRCommand {

	double prevRampRate;
	
    public ShooterJoystickCommand() {
        requires(Shooter.getInstance());
    }

    @Override
    public void onStart() {
    	prevRampRate = Shooter.getInstance().getRampRate();
    	//Shooter.getInstance().setRampRate(2.5);
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void onExecute() {
    	Shooter.getInstance().setMotor(OI.getInstance().getShooterMoveValue());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
    
    @Override
    public void onEnd(boolean interrupted) {
    	Shooter.getInstance().setRampRate(prevRampRate);
    }
}
