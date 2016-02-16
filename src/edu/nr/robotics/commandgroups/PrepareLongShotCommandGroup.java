package edu.nr.robotics.commandgroups;

import edu.nr.lib.UDPServer;
import edu.nr.robotics.OI;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.hood.HoodPositionCommand;
import edu.nr.robotics.subsystems.lights.LightsBlinkCommand;
import edu.nr.robotics.subsystems.shooter.ShooterHighCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PrepareLongShotCommandGroup extends CommandGroup {
    
    public  PrepareLongShotCommandGroup() {
        addParallel(new ShooterHighCommand());
        addParallel(new HoodPositionCommand(UDPServer.getInstance().getShootAngle()));
        addParallel(new IntakeArmBottomHeightCommand());
        if(!OI.getInstance().getBrakeLightCutout()) {
        	addParallel(new LightsBlinkCommand(RobotMap.LIGHTS_BLINK_PERIOD));
        }
    }
}
