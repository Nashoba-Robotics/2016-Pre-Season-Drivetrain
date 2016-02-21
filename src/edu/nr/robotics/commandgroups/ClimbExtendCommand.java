package edu.nr.robotics.commandgroups;

import edu.nr.lib.WaitForPIDSourceGreaterThanCommand;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.climb.Climb;
import edu.nr.robotics.subsystems.climb.ClimbOffCommand;
import edu.nr.robotics.subsystems.climb.ClimbUpCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ClimbExtendCommand extends CommandGroup {
    
    public  ClimbExtendCommand() {
        addSequential(new IntakeArmUpHeightCommandGroup());
        addSequential(new ClimbUpCommand());
        addSequential(new WaitForPIDSourceGreaterThanCommand(Climb.getInstance().enc, RobotMap.ELEVATOR_EXTEND_DISTANCE));
        addSequential(new ClimbOffCommand());
    }
}
