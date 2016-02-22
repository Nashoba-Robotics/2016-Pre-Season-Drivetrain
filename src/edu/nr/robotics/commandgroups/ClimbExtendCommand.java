package edu.nr.robotics.commandgroups;

import edu.nr.lib.WaitForPIDSourceGreaterThanCommand;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.climb.Elevator;
import edu.nr.robotics.subsystems.climb.ElevatorOffCommand;
import edu.nr.robotics.subsystems.climb.ElevatorUpCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class ClimbExtendCommand extends CommandGroup {
    
    public  ClimbExtendCommand() {
        addSequential(new IntakeArmUpHeightCommandGroup());
        addSequential(new ElevatorUpCommand());
        addSequential(new WaitForPIDSourceGreaterThanCommand(Elevator.getInstance().enc, RobotMap.ELEVATOR_EXTEND_DISTANCE));
        //TODO: Once elevator is on there, test encoder
        addSequential(new ElevatorOffCommand());
    }
}
