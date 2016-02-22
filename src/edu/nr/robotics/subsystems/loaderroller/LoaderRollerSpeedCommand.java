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

    protected void onStart() {
    	LoaderRoller.getInstance().setLoaderSpeed(val);
    }
}
