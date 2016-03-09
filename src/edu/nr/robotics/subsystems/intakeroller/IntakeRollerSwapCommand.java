package edu.nr.robotics.subsystems.intakeroller;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRoller;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerIntakeCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerNeutralCommand;

/**
 *
 */
public class IntakeRollerSwapCommand extends NRCommand {
    
    public  IntakeRollerSwapCommand() {
    	requires(IntakeRoller.getInstance());
    }
    
    public void onStart() {
    	if(IntakeRoller.getInstance().isRunning() || LoaderRoller.getInstance().isRunning()) {
        	new IntakeRollerNeutralCommand().start();
        	System.out.println("Set intake roller speed to 0");
    	} else {
    		new IntakeRollerIntakeCommand().start();
    		System.out.println("Set intake roller speed to -1");
    	}
    }
}
