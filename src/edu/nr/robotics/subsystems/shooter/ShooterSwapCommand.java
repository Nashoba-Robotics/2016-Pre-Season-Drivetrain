package edu.nr.robotics.subsystems.shooter;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.OI;

/**
 *
 */
public class ShooterSwapCommand extends NRCommand {
    
    public  ShooterSwapCommand() {
    	requires(Shooter.getInstance());
    }
    
    @Override
	public void onStart() {
    	if(OI.getInstance().getDumbShooter()) {
        	Shooter.getInstance().setDefaultCommandOff();
    	} else {
        	Shooter.getInstance().setDefaultCommandOn();
    	}
    }
}
