package edu.nr.robotics.commandgroups;

import edu.nr.robotics.subsystems.drive.DriveSimpleDistanceCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmBottomHeightCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmUpHeightCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGuillotineCommandGroup extends CommandGroup {
    
	public static final double firstdistance = 6;
	public static final double firstspeed = 1;
	public static final double seconddistance = 1;
	public static final double secondspeed = 1;

	//TODO: Confirm the behaviour of auto guillotine
	
    public  AutoGuillotineCommandGroup() {
    	addSequential(new IntakeArmBottomHeightCommand());
        addSequential(new DriveSimpleDistanceCommand(firstdistance,firstspeed));
        addSequential(new IntakeArmUpHeightCommand());
        addSequential(new DriveSimpleDistanceCommand(seconddistance,secondspeed));
    }
}
