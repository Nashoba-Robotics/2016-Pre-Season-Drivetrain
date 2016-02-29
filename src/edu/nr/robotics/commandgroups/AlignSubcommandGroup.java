package edu.nr.robotics.commandgroups;

import edu.nr.lib.network.JetsonImagePacket;
import edu.nr.lib.network.UDPServer;
import edu.nr.robotics.subsystems.drive.DriveAngleJetsonPIDCommand;
import edu.nr.robotics.subsystems.hood.HoodJetsonPositionCommand;
import edu.nr.robotics.subsystems.lights.LightsBlinkCommand;
import edu.nr.robotics.subsystems.shooter.ShooterHighCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AlignSubcommandGroup extends CommandGroup {
    
	JetsonImagePacket packet;
	
    public  AlignSubcommandGroup() {
        addParallel(new HoodJetsonPositionCommand());
        addParallel(new ShooterHighCommand());
        addSequential(new DriveAngleJetsonPIDCommand());
    }
    
    @Override
    public void start() {
    	this.packet = UDPServer.getInstance().getLastPacket();
    }

	public JetsonImagePacket getJetsonPacket() {
		return packet;
	}
}
