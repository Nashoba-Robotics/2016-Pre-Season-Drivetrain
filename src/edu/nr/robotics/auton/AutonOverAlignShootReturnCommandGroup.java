package edu.nr.robotics.auton;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.commandgroups.AlignAndShootCommandGroup;
import edu.nr.robotics.subsystems.drive.DriveSimpleDistanceCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonOverAlignShootReturnCommandGroup extends CommandGroup {
    
    public  AutonOverAlignShootReturnCommandGroup() {
        addSequential(new DriveSimpleDistanceCommand(RobotMap.OVER_DISTANCE, 1.0));
        addSequential(new AlignAndShootCommandGroup());
    }
}
