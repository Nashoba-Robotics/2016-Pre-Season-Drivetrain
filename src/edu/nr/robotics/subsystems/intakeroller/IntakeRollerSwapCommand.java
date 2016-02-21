package edu.nr.robotics.subsystems.intakeroller;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeRollerSwapCommand extends CommandGroup {
    
    public  IntakeRollerSwapCommand() {
    }
    
    public void onStart() {
    	if(IntakeRoller.getInstance().getRollerSpeed() != 0)
        	new IntakeRollerNeutralCommand();
    	else
        	new IntakeRollerIntakeCommand();
    }
}
