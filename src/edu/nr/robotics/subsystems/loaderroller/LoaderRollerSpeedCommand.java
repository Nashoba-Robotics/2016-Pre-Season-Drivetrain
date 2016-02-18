package edu.nr.robotics.subsystems.loaderroller;

import edu.nr.lib.CMD;

/**
 *
 */
public class LoaderRollerSpeedCommand extends CMD {

	double val;
	
    public LoaderRollerSpeedCommand(double val) {
    	this.val = val;
    	requires(LoaderRoller.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	LoaderRoller.getInstance().setLoaderSetpoint(val);
    }
}
