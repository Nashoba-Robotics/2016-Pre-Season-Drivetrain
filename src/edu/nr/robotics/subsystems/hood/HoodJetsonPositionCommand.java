package edu.nr.robotics.subsystems.hood;

import edu.nr.lib.NRCommand;
import edu.nr.lib.network.UDPServer;
import edu.nr.robotics.commandgroups.AlignSubcommandGroup;

public class HoodJetsonPositionCommand extends NRCommand {

	double val;
	
	boolean canRun = true;
	
	public HoodJetsonPositionCommand() {
		requires(Hood.getInstance());
	}
	
	@Override
	protected void onStart() {
		try {
			if(this.getGroup() instanceof AlignSubcommandGroup) {
				val = ((AlignSubcommandGroup )this.getGroup()).getJetsonPacket().getHoodAngle();
			} else {
				val = UDPServer.getInstance().getLastPacket().getHoodAngle();
			}
		} catch(NullPointerException e) {
			System.out.println("No value from Jetson yet, so we can't run " + this.getName());
			canRun = false;
			return;
		}
		Hood.getInstance().enable();
		Hood.getInstance().setSetpoint(val);
	}

	@Override
	protected void onEnd(boolean interrupted) {
		System.out.println("Just finished Hood Jetson check");
	}

	@Override
	protected boolean isFinishedNR() {
		return !canRun || Math.abs(Hood.getInstance().get() - val) < 0.25;
	}

}
