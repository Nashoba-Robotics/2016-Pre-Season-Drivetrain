package edu.nr.robotics.commandgroups;

import edu.nr.lib.network.JetsonImagePacket;
import edu.nr.lib.network.UDPServer;
import edu.nr.robotics.subsystems.drive.DriveAngleJetsonPIDCommand;
import edu.nr.robotics.subsystems.hood.HoodJetsonPositionCommand;
import edu.nr.robotics.subsystems.shooter.ShooterHighCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AlignSubcommandGroup extends CommandGroup {
    	
    public  AlignSubcommandGroup() {
        addParallel(new HoodJetsonPositionCommand());
        addParallel(new ShooterHighCommand());
        addSequential(new DriveAngleJetsonPIDCommand());
    }
}
