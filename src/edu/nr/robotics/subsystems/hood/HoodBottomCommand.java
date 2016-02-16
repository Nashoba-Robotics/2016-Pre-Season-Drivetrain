package edu.nr.robotics.subsystems.hood;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class HoodBottomCommand extends CommandGroup {
    
	private static final double bottomHoodPosition = 0;
	//TODO: Find the bottom hood position
	
    public  HoodBottomCommand() {
        addSequential(new HoodPositionCommand(bottomHoodPosition));
    }
}
