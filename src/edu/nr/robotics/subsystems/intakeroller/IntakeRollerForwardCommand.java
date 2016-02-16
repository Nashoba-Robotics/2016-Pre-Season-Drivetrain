package edu.nr.robotics.subsystems.intakeroller;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeRollerForwardCommand extends CommandGroup {
    
    public  IntakeRollerForwardCommand() {
        addSequential(new IntakeRollerSpeedCommand(1));
    }
}
