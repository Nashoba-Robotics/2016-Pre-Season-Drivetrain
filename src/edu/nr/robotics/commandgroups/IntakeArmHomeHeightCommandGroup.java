package edu.nr.robotics.commandgroups;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakearm.IntakeArmPositionCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerNeutralCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeArmHomeHeightCommandGroup extends CommandGroup {

    public IntakeArmHomeHeightCommandGroup() {
        addParallel(new IntakeRollerNeutralCommand());
        addSequential(new IntakeArmPositionCommand(RobotMap.INTAKE_HOME_POS));
    }
}
