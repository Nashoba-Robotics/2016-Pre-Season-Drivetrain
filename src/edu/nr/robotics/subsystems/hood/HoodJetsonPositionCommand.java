package edu.nr.robotics.subsystems.hood;

import edu.nr.lib.CMD;
import edu.nr.lib.UDPServer;

public class HoodJetsonPositionCommand extends CMD {

	double val;
	
	public HoodJetsonPositionCommand() {
		requires(Hood.getInstance());
	}
	
	@Override
	protected void onStart() {
		val = UDPServer.getInstance().getShootAngle();
		Hood.getInstance().enable();
		Hood.getInstance().setSetpoint(val);
	}

	@Override
	protected void onExecute() {
	}

	@Override
	protected void onEnd(boolean interrupted) {

	}

	@Override
	protected boolean isFinished() {
		return Hood.getInstance().get() == val;
	}

}
