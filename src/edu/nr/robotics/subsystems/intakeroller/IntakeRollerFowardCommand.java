package edu.nr.robotics.subsystems.intakeroller;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeRollerFowardCommand extends CommandGroup {
    
    public  IntakeRollerFowardCommand() {
        addSequential(new IntakeRollerSpeedCommand(1));
    }
}
