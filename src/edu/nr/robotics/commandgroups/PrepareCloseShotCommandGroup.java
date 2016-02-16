package edu.nr.robotics.commandgroups;

import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.hood.HoodPositionCommand;
import edu.nr.robotics.subsystems.intakearm.IntakeArmBottomHeightCommand;
import edu.nr.robotics.subsystems.lights.LightsBlinkCommand;
import edu.nr.robotics.subsystems.shooter.ShooterHighCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PrepareCloseShotCommandGroup extends CommandGroup {
    
    public  PrepareCloseShotCommandGroup() {
    	addParallel(new ShooterHighCommand());
        addParallel(new HoodPositionCommand(RobotMap.CLOSE_SHOT_POSITION));
        addParallel(new IntakeArmBottomHeightCommand());
        addParallel(new LightsBlinkCommand(RobotMap.LIGHTS_BLINK_PERIOD));
    }
}
