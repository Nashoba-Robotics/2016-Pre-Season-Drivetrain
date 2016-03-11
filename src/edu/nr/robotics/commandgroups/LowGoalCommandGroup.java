package edu.nr.robotics.commandgroups;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakearm.IntakeArmPositionCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmUpHeightCommandGroup;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerNeutralCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerOuttakeCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerNeutralCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerOuttakeCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class LowGoalCommandGroup extends CommandGroup {
    
    public  LowGoalCommandGroup() {
        addParallel(new IntakeArmPositionCommand(RobotMap.INTAKE_INTAKE_POS));
        addParallel(new IntakeRollerOuttakeCommand());
        addParallel(new LoaderRollerOuttakeCommand());
        addSequential(new WaitCommand(1.5));
        addParallel(new IntakeRollerNeutralCommand());
        addParallel(new LoaderRollerNeutralCommand());
    }
}
