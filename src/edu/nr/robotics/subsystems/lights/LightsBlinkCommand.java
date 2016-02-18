package edu.nr.robotics.subsystems.lights;

import edu.nr.lib.CMD;
import edu.nr.robotics.RobotMap;

public class LightsBlinkCommand extends CMD {

	long millisBetweenChange;
	long startTime;
	
	public LightsBlinkCommand(long millisBetweenChange) {
		this.millisBetweenChange = millisBetweenChange;
		requires(Lights.getInstance());
	}
	
	public LightsBlinkCommand() {
		this(RobotMap.LIGHTS_BLINK_PERIOD);
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
