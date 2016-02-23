package edu.nr.robotics.subsystems.shooter;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.OI;

/**
 *
 */
public class ShooterJoystickCommand extends NRCommand {
	
    public ShooterJoystickCommand() {
        requires(Shooter.getInstance());
    }
    
    public void onStart() {
    	Shooter.getInstance().enable();
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void onExecute() {
    	Shooter.getInstance().setSetpoint(OI.getInstance().getShooterMoveValue());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
}
