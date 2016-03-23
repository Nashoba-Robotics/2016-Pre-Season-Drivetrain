package edu.nr.robotics.subsystems.climb.norequire;

import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ElevatorResetCommand extends CommandGroup {

	long resetTime;
	
    public ElevatorResetCommand() {
    	addSequential(new WaitForElevatorNoRequireStoppedForTwoSeconds());
        addParallel(new ElevatorNoRequireVoltageCommand(-0.3));
        addSequential(new ElevatorNoRequireWaitForMotorStallTimeCommand(1));
        addParallel(new ElevatorNoRequireVoltageCommand(RobotMap.ELEVATOR_UP_SPEED));
        addSequential(new ElevatorNoRequireWaitUntilChangedByCommand(180));
        addParallel(new ElevatorNoRequireVoltageCommand(0));
    }
    
    @Override
    public void start() {
    	super.start();
    	resetTime = System.currentTimeMillis();
    }
    
    @Override
    public boolean isFinished() {
    	return (System.currentTimeMillis() - resetTime > 10000) || super.isFinished();
    }
}
