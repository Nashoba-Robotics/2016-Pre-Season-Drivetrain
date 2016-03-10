package edu.nr.robotics.subsystems.climb;

import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ElevatorUpUntilGreaterThanCommand extends CommandGroup {
    
   	double value;
		
    public ElevatorUpUntilGreaterThanCommand(double value) {
    	this.value = value;
    	requires(Elevator.getInstance());
    }
    
 // Called just before this Command runs the first time
    protected void onStart() {
    	Elevator.getInstance().setMotorValue(RobotMap.ELEVATOR_UP_SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinishedNR() {
        return Elevator.getInstance().getEncoder() > value;
    }
}
