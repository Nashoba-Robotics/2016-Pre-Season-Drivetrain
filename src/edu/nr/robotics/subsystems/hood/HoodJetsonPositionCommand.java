package edu.nr.robotics.subsystems.hood;

import edu.nr.lib.NRCommand;
import edu.nr.lib.network.UDPServer;
import edu.nr.robotics.commandgroups.AlignSubcommandGroup;

public class HoodJetsonPositionCommand extends NRCommand {

	double val;
	
	public HoodJetsonPositionCommand() {
		requires(Hood.getInstance());
	}
	
	@Override
	protected void onStart() {
		if(this.getGroup() instanceof AlignSubcommandGroup) {
			val = ((AlignSubcommandGroup )this.getGroup()).getJetsonPacket().getHoodAngle();
		} else {
			val = UDPServer.getInstance().getLastPacket().getHoodAngle();
		}
		Hood.getInstance().enable();
		Hood.getInstance().setSetpoint(val);
	}

	@Override
	protected void onExecute() {
	}

	@Override
	protected void onEnd(boolean interrupted) {
		System.out.println("Just finished Hood Jetson check");
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(Hood.getInstance().get() - val) < 0.25;
	}

}
