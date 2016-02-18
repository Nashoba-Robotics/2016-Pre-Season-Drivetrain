package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.UDPServer;
import edu.nr.robotics.OI;
import edu.nr.robotics.RobotMap;
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
public class AlignCommand extends CommandGroup {
    
    public  AlignCommand() {
    	addSequential(new WaitCommand(0.25));
        addParallel(new HoodJetsonPositionCommand());
        addParallel(new ShooterHighCommand());
        addParallel(new LightsBlinkCommand(200));
        addSequential(new DriveAngleJetsonPIDCommand());
        addSequential(new WaitCommand(0.25));
    }
    
    @Override
    public void end() {
    	if(Hood.getInstance().get() != UDPServer.getInstance().getShootAngle() || Math.abs(UDPServer.getInstance().getTurnAngle()) > 1 || Shooter.getInstance().getSpeed() != RobotMap.SHOOTER_FAST_SPEED)
    		new AlignCommand();
    	while(!OI.getInstance().fireButton.get() && OI.getInstance().alignButton.get()) {}
    }
}
