package edu.nr.robotics.subsystems.hood;

import edu.nr.lib.NRCommand;
import edu.nr.lib.network.UDPServer;

public class HoodJetsonPositionCommand extends NRCommand {

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
		System.out.println("Hood jetson pos test " + Math.abs(Hood.getInstance().get() - val));
		return Math.abs(Hood.getInstance().get() - val) < 0.25;
	}

}
