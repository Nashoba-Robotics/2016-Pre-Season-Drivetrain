package edu.nr.robotics.commandgroups;

import edu.nr.lib.WaitForPIDSourceGreaterThanCommand;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.elevator.Elevator;
import edu.nr.robotics.subsystems.elevator.ElevatorOffCommand;
import edu.nr.robotics.subsystems.elevator.ElevatorUpCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ExtendAndIntakeUpCommandGroup extends CommandGroup {
    
    public  ExtendAndIntakeUpCommandGroup() {
        addSequential(new IntakeArmUpHeightCommandGroup());
        addSequential(new ElevatorUpCommand());
        addSequential(new WaitForPIDSourceGreaterThanCommand(Elevator.getInstance().enc, RobotMap.ELEVATOR_EXTEND_DISTANCE));
        addSequential(new ElevatorOffCommand());
    }
}
