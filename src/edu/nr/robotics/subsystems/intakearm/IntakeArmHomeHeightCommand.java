package edu.nr.robotics.subsystems.intakearm;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerNeutralCommand;
import edu.nr.robotics.subsystems.loaderroller.LoaderRollerNeutralCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeArmHomeHeightCommand extends CommandGroup {

    public IntakeArmHomeHeightCommand() {
        addSequential(new IntakeArmPositionCommand(RobotMap.INTAKE_HOME_POS));
    }
}
