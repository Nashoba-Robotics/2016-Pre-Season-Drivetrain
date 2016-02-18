package edu.nr.robotics.auton;

import edu.nr.lib.network.UDPServer;
import edu.nr.robotics.OI;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.commandgroups.AlignSubcommandGroup;
import edu.nr.robotics.subsystems.drive.DriveAngleJetsonPIDCommand;
import edu.nr.robotics.subsystems.hood.Hood;
import edu.nr.robotics.subsystems.hood.HoodJetsonPositionCommand;
import edu.nr.robotics.subsystems.lights.LightsBlinkCommand;
import edu.nr.robotics.subsystems.shooter.Shooter;
import edu.nr.robotics.subsystems.shooter.ShooterHighCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutonAlignCommand extends CommandGroup {
    
    public  AutonAlignCommand() {
    	addSequential(new WaitCommand(0.25));
        addSequential(new AlignSubcommandGroup());
        addSequential(new WaitCommand(0.25));
    }
    
    @Override
    public void end() {
    	if(Math.abs(Hood.getInstance().get() - UDPServer.getInstance().getShootAngle()) > RobotMap.HOOD_THRESHOLD || Math.abs(UDPServer.getInstance().getTurnAngle()) > RobotMap.TURN_THRESHOLD || Math.abs(Shooter.getInstance().getSpeed() - RobotMap.SHOOTER_FAST_SPEED) > RobotMap.SHOOTER_THRESHOLD)
    		new AutonAlignCommand();
    }
}
