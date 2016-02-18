package edu.nr.robotics.subsystems.hood;

import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class HoodBottomCommand extends CommandGroup {
    
	
    public  HoodBottomCommand() {
        addSequential(new HoodPositionCommand(RobotMap.BOTTOM_HOOD_POSITION));
    }
}
