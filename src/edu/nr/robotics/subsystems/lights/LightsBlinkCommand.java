package edu.nr.robotics.subsystems.lights;

import edu.nr.lib.CMD;

public class LightsBlinkCommand extends CMD {

	long millisBetweenChange;
	long startTime;
	
	public LightsBlinkCommand(long millisBetweenChange) {
		this.millisBetweenChange = millisBetweenChange;
		requires(Lights.getInstance());
	}

	@Override
	protected void onStart() {
		startTime = System.currentTimeMillis();
	}

	@Override
	protected void onExecute() {
		if((System.currentTimeMillis() - startTime) > millisBetweenChange) {
			Lights.getInstance().swap();
			startTime = System.currentTimeMillis();
		}
	}

	@Override
	protected void onEnd(boolean interrupted) {
		Lights.getInstance().disable();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
