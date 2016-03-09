package edu.nr.robotics.subsystems.intakearm;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerNeutralCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerNeutralCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeArmBottomHeightCommandGroup extends CommandGroup {

    public IntakeArmBottomHeightCommandGroup() {
        addParallel(new IntakeRollerNeutralCommand());
        addParallel(new LoaderRollerNeutralCommand());
    	addSequential(new IntakeArmPositionCommand(RobotMap.INTAKE_BOTTOM_POS));
    }
}