package edu.nr.robotics.commandgroups;

import edu.nr.robotics.subsystems.drive.DriveAnglePIDAutonCommand;
import edu.nr.robotics.subsystems.drive.DriveSimpleDistanceWithGyroCommand;
import edu.nr.robotics.subsystems.hood.HoodJetsonPositionCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AlignSubcommandGroup extends CommandGroup {
    	
    public  AlignSubcommandGroup() {
        addSequential(new DriveAnglePIDAutonCommand(true));
        addSequential(new DriveSimpleDistanceWithGyroCommand(1, 0.2));
        addSequential(new DriveSimpleDistanceWithGyroCommand(-1, 0.2));
        addSequential(new HoodJetsonPositionCommand());
    }
}
