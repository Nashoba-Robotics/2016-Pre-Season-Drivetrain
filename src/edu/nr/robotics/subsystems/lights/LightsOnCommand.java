package edu.nr.robotics.subsystems.lights;

import edu.nr.lib.CMD;

/**
 *
 */
public class LightsOnCommand extends CMD {

    public LightsOnCommand() {
        requires(Lights.getInstance());
    }

    @Override
    public void onStart() {
    	Lights.getInstance().enable();
    }
    
    @Override
    public boolean isFinished() {
    	return false;
    }

	@Override
	protected void onExecute() {		
	}

	@Override
	protected void onEnd(boolean interrupted) {
		Lights.getInstance().disable();
	}
}
