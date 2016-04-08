package edu.nr.robotics.subsystems.climb;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.hood.HoodPositionCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmUpHeightCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ElevatorExtendCommand extends CommandGroup {
    
    public  ElevatorExtendCommand() {
    	
        addSequential(new ElevatorUpUntilGreaterThanCommand(RobotMap.ELEVATOR_EXTEND_DISTANCE));
        addSequential(new ElevatorOffCommand());
    }
}
