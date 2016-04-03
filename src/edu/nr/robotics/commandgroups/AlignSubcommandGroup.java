package edu.nr.robotics.commandgroups;

import edu.nr.robotics.subsystems.drive.DriveAnglePIDCommand;
import edu.nr.robotics.subsystems.hood.HoodJetsonPositionCommand;
import edu.nr.robotics.subsystems.shooter.ShooterHighCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AlignSubcommandGroup extends CommandGroup {
    	
    public  AlignSubcommandGroup() {
        addParallel(new ShooterHighCommand());
        addSequential(new DriveAnglePIDCommand(true));
        addSequential(new HoodJetsonPositionCommand());
    }
}
