package edu.nr.robotics.subsystems.climb;

import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ElevatorUpCommand extends CommandGroup {
    
    public  ElevatorUpCommand() {
        addSequential(new ElevatorSpeedCommand(RobotMap.ELEVATOR_UP_SPEED));
    }
}
