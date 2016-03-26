package edu.nr.robotics.subsystems.climb;

import edu.nr.robotics.subsystems.intakearm.IntakeArmUpHeightCommandGroup;
import edu.nr.robotics.subsystems.shooter.ShooterHighCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ElevatorRetractCommand extends CommandGroup {

    public ElevatorRetractCommand() {
    	    	
    	addParallel(new IntakeArmUpHeightCommandGroup());
    	addParallel(new ShooterHighCommand());
    	addParallel(new ElevatorVoltageCommand(-1));
        addSequential(new ElevatorWaitForMotorStallTimeCommand(1));
        addParallel(new ElevatorOffCommand());
        
    }
}
