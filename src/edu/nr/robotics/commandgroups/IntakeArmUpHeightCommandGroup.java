package edu.nr.robotics.commandgroups;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakearm.IntakeArmPositionCommand;
import edu.nr.robotics.subsystems.intakeroller.IntakeRollerNeutralCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeArmUpHeightCommandGroup extends CommandGroup {

    public IntakeArmUpHeightCommandGroup() {
        addParallel(new IntakeRollerNeutralCommand());
        addSequential(new IntakeArmPositionCommand(RobotMap.INTAKE_TOP_POS));
    }
}
