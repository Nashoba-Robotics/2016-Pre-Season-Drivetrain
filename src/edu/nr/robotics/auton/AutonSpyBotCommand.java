package edu.nr.robotics.auton;

import edu.nr.robotics.commandgroups.AlignAndShootCommandGroup;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonSpyBotCommand extends CommandGroup {
    
    public  AutonSpyBotCommand() {
        addSequential(new AlignAndShootCommandGroup());
    }
}
