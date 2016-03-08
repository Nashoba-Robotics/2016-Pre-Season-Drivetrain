package edu.nr.lib;

import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 */
public class WaitForPIDSourceGreaterThanCommand extends NRCommand {

	PIDSource source;
	double value;
	
    public WaitForPIDSourceGreaterThanCommand(PIDSource source, double value) {
    	this.source = source;
    	this.value = value;
    }

    @Override
    protected boolean isFinishedNR() {
        return source.pidGet() > value;
    }
}
