package edu.nr.robotics.subsystems.intakeroller;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRoller;

/**
 *
 */
public class IntakeRollerSwapCommand extends NRCommand {
    
    public  IntakeRollerSwapCommand() {
    	requires(IntakeRoller.getInstance());
    }
    
    public void onStart() {
    	if(IntakeRoller.getInstance().isRunning()) {
        	IntakeRoller.getInstance().setRollerSpeed(0);
        	System.out.println("Set roller speed to 0");
    	} else {
        	IntakeRoller.getInstance().setRollerSpeed(-1);
        	System.out.println("Set roller speed to -1");
    	}
    }
}
