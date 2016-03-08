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
    	requires(LoaderRoller.getInstance());
    }
    
    public void onStart() {
    	if(IntakeRoller.getInstance().isRunning() || LoaderRoller.getInstance().isRunning()) {
        	new IntakeRollerNeutralCommand().start();
        	new LoaderRollerNeutralCommand().start();
        	System.out.println("Set roller speed to 0");
    	} else {
    		new IntakeRollerIntakeCommand().start();
    		new LoaderRollerIntakeCommand().start();
    		System.out.println("Set roller speed to -1");
    	}
    }
}
