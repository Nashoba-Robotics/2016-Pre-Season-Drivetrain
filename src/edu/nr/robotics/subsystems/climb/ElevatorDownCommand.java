package edu.nr.robotics.subsystems.climb;

import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ElevatorDownCommand extends CommandGroup {
    
    public  ElevatorDownCommand() {
        addSequential(new ElevatorSpeedCommand(-RobotMap.ELEVATOR_UP_SPEED));
    }
}
