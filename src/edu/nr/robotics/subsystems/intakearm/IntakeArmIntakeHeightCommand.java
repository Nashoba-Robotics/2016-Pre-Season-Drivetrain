package edu.nr.robotics.subsystems.intakearm;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerIntakeCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerNeutralCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerIntakeUntilPhotoCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerNeutralCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeArmIntakeHeightCommand extends CommandGroup {

    public IntakeArmIntakeHeightCommand() {
        addParallel(new IntakeArmPositionCommand(RobotMap.INTAKE_INTAKE_POS));
    }
}