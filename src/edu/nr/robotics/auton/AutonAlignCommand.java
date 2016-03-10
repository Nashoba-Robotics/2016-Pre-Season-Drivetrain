package edu.nr.robotics.auton;

import edu.nr.lib.network.JetsonImagePacket;
import edu.nr.lib.network.UDPServer;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.commandgroups.AlignSubcommandGroup;
import edu.nr.robotics.subsystems.hood.Hood;
import edu.nr.robotics.subsystems.shooter.Shooter;
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
    	JetsonImagePacket packet = UDPServer.getInstance().getLastPacket();

    	
    	if(Math.abs(Hood.getInstance().get() - packet.getHoodAngle()) > RobotMap.HOOD_THRESHOLD || Math.abs(packet.getTurnAngle()) > RobotMap.TURN_THRESHOLD || Math.abs(Shooter.getInstance().getScaledSpeed() - RobotMap.SHOOTER_FAST_SPEED) > RobotMap.SHOOTER_THRESHOLD)
    		new AutonAlignCommand().start();
    }
}
