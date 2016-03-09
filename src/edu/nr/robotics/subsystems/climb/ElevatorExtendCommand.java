package edu.nr.robotics.subsystems.climb;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakearm.IntakeArmUpHeightCommandGroup;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ElevatorExtendCommand extends CommandGroup {
    
    public  ElevatorExtendCommand() {
        addSequential(new IntakeArmUpHeightCommandGroup());
        addSequential(new ElevatorUpUntilGreaterThanCommand(RobotMap.ELEVATOR_EXTEND_DISTANCE));
    }
}
