package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.AngleUnit;
import edu.nr.robotics.subsystems.hood.HoodJetsonPositionCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveAnglePIDCommandGroup extends CommandGroup {
    	
    public  DriveAnglePIDCommandGroup(double angle, AngleUnit unit) {
        addSequential(new DriveAnglePIDCommand(angle, unit));
        addSequential(new DriveSimpleDistanceWithGyroCommand(1, 0.2));
        addSequential(new DriveSimpleDistanceWithGyroCommand(-1, 0.2));
    }
}