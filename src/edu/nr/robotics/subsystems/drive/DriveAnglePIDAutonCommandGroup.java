package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.AngleUnit;
import edu.nr.robotics.subsystems.hood.HoodAndroidPositionCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveAnglePIDAutonCommandGroup extends CommandGroup {
    	
    public  DriveAnglePIDAutonCommandGroup(double angle, AngleUnit unit) {
        addSequential(new DriveAnglePIDAutonCommand(angle, unit));
        addSequential(new DriveSimpleDistanceWithGyroCommand(1, 0.2));
        addSequential(new DriveSimpleDistanceWithGyroCommand(-1, 0.2));
    }
}