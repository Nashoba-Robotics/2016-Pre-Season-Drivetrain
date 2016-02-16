package edu.nr.robotics.commandgroups;

import edu.nr.robotics.subsystems.intakearm.IntakeArmUpHeightCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerNeutralCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerReverseCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerNeutralCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerReverseCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class LowGoalCommandGroup extends CommandGroup {
    
    public  LowGoalCommandGroup() {
        addSequential(new IntakeArmUpHeightCommand());
        addParallel(new IntakeRollerReverseCommand());
        addParallel(new LoaderRollerReverseCommand());
        addSequential(new WaitCommand(1.5));
        addParallel(new IntakeRollerNeutralCommand());
        addParallel(new LoaderRollerNeutralCommand());
    }
}
