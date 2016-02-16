package edu.nr.robotics.subsystems.intakeroller;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeRollerReverseCommand extends CommandGroup {
    
    public  IntakeRollerReverseCommand() {
        addSequential(new IntakeRollerSpeedCommand(-1));
    }
}
