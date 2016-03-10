package edu.nr.robotics.commandgroups;

import edu.nr.robotics.subsystems.drive.DriveDistanceCommand;
import edu.nr.robotics.subsystems.hood.HoodBottomCommand;
import edu.nr.robotics.subsystems.hood.HoodWaitForBottomCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmBottomHeightCommandGroup;
import edu.nr.robotics.subsystems.intakearm.IntakeArmUpHeightCommandGroup;
import edu.nr.robotics.subsystems.intakearm.IntakeArmWaitForBottomCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmWaitForTopCommand;
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
    	addParallel(new HoodBottomCommand());
    	addParallel(new IntakeArmBottomHeightCommandGroup());
    	addSequential(new IntakeArmWaitForBottomCommand());
    	addSequential(new HoodWaitForBottomCommand());
        addSequential(new DriveDistanceCommand(firstdistance,firstspeed));
        addSequential(new IntakeArmUpHeightCommandGroup());
    	addSequential(new IntakeArmWaitForTopCommand());
        addSequential(new DriveDistanceCommand(seconddistance,secondspeed));
    }
}
