package edu.nr.robotics.auton;

import edu.nr.lib.AngleUnit;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.commandgroups.AlignAndShootCommandGroup;
import edu.nr.robotics.subsystems.drive.DriveAnglePIDCommand;
import edu.nr.robotics.subsystems.drive.DriveSimpleDistanceWithGyroCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonOverAlignShootCommandGroup extends CommandGroup {
    
    public  AutonOverAlignShootCommandGroup() {
        addSequential(new DriveSimpleDistanceWithGyroCommand(RobotMap.OVER_DISTANCE, 1.0));
        addSequential(new DriveAnglePIDCommand(50, AngleUnit.DEGREE));
        addSequential(new AlignAndShootCommandGroup());
        addSequential(new AutonReturnToNormalBackCommandGroup());
        addSequential(new DriveSimpleDistanceWithGyroCommand(RobotMap.ONTO_DISTANCE, 1.0));
    }
}
