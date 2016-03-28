package edu.nr.robotics.subsystems.climb;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.hood.HoodPositionCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmUpHeightCommandGroup;
import edu.nr.robotics.subsystems.shooter.ShooterHighCommand;
import edu.nr.robotics.subsystems.shooter.ShooterOnCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ElevatorExtendCommand extends CommandGroup {
    
    public  ElevatorExtendCommand() {
        
    	//TODO: Add intake arm back
    	
    	addParallel(new IntakeArmUpHeightCommandGroup());
    	addParallel(new HoodPositionCommand(34.5));
    	addParallel(new ShooterHighCommand());
        addSequential(new ElevatorUpUntilGreaterThanCommand(RobotMap.ELEVATOR_EXTEND_DISTANCE));
        addSequential(new ElevatorOffCommand());
    }
}
