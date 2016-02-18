package edu.nr.robotics.auton;

import edu.nr.lib.NRCommand;

public class AutonDoNothingCommand extends NRCommand {
	@Override
	protected void onStart() {

	}

	@Override
	protected void onExecute() {
	}

	@Override
	protected void onEnd(boolean interrupted) {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}