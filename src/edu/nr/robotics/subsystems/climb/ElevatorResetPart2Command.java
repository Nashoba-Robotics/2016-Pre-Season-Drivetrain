package edu.nr.robotics.subsystems.climb;

import edu.nr.lib.NRCommand;
import edu.nr.robotics.OI;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ElevatorResetPart2Command extends CommandGroup {

	public ElevatorResetPart2Command() {
        addParallel(new ElevatorVoltageCommand(-0.3));
        addSequential(new ElevatorWaitForMotorStallTimeCommand(1));
        addSequential(new ElevatorResetEncoderCommand());
        addParallel(new ElevatorOffCommand());
    }
}
