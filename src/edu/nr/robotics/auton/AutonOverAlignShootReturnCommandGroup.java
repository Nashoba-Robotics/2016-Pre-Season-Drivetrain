package edu.nr.robotics.auton;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.auton.AutonOverAlignShootCommandGroup.Positions;
import edu.nr.robotics.subsystems.drive.DriveSimpleDistanceWithGyroCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonOverAlignShootReturnCommandGroup extends CommandGroup {
    	
    public  AutonOverAlignShootReturnCommandGroup(final Positions pos) {
    	
    	final double ontoDistance;
    	
    	if(pos == Positions.two || pos == Positions.five) {
    		ontoDistance = RobotMap.ONTO_DISTANCE_25;
    	} else {
    		ontoDistance = RobotMap.ONTO_DISTANCE_134;
    	}
    	
        addSequential(new AutonOverAlignShootCommandGroup(pos));
        addSequential(new AutonReturnToNormalBackCommandGroup());
        addSequential(new DriveSimpleDistanceWithGyroCommand(ontoDistance, 1.0));
    }
}
