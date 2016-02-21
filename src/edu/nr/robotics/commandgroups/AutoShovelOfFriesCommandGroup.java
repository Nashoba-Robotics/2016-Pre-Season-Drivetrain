package edu.nr.robotics.commandgroups;

import edu.nr.robotics.subsystems.drive.DriveSimpleDistanceCommand;
import edu.nr.robotics.subsystems.hood.HoodBottomCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoShovelOfFriesCommandGroup extends CommandGroup {
	
	public static final double firstdistance = 6;
	public static final double firstspeed = 1;
	public static final double seconddistance = 6;
	public static final double secondspeed = 1;
	
	//TODO: Confirm the behaviour of auto shovel of fries

    public  AutoShovelOfFriesCommandGroup() {
    	addParallel(new HoodBottomCommand());
    	addSequential(new IntakeArmUpHeightCommandGroup());
        addSequential(new DriveSimpleDistanceCommand(firstdistance,firstspeed));
        addSequential(new IntakeArmBottomHeightCommandGroup());
        addSequential(new DriveSimpleDistanceCommand(seconddistance,secondspeed));

    }
}
