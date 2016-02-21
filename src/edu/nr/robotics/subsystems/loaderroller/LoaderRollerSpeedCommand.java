package edu.nr.robotics.subsystems.loaderroller;

import edu.nr.lib.NRCommand;

/**
 *
 */
public class LoaderRollerSpeedCommand extends NRCommand {

	double val;
	
    public LoaderRollerSpeedCommand(double val) {
    	this.val = val;
    	requires(LoaderRoller.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	LoaderRoller.getInstance().setLoaderSpeed(val);
    }
}
