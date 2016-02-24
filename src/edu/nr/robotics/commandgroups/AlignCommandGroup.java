package edu.nr.robotics.commandgroups;

import edu.nr.lib.NRCommand;
import edu.nr.lib.network.UDPServer;
import edu.nr.robotics.OI;
import edu.nr.robotics.Robot;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.drive.DriveAngleJetsonPIDCommand;
import edu.nr.robotics.subsystems.hood.Hood;
import edu.nr.robotics.subsystems.shooter.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AlignCommandGroup extends CommandGroup {
    
	
	public enum State {
		ALIGNING, WAITING, OFF
	}
	
    public  AlignCommandGroup() {
    	//addSequential(new AlignStartCommand());
    	//addSequential(new WaitCommand(0.25));
        addSequential(new AlignSubcommandGroup());
        //addSequential(new WaitCommand(0.25));
    }
    
    @Override
    public void end() {
    	System.out.println("Align command group started ending");
    	/*if(OI.getInstance().alignButton.get() && (Math.abs(Hood.getInstance().get() - UDPServer.getInstance().getShootAngle()) > RobotMap.HOOD_THRESHOLD || Math.abs(UDPServer.getInstance().getTurnAngle()) > RobotMap.TURN_THRESHOLD || Math.abs(Shooter.getInstance().getScaledSpeed() - RobotMap.SHOOTER_FAST_SPEED) > RobotMap.SHOOTER_THRESHOLD)) {
    		System.out.println("Starting align again");
    		new AlignCommandGroup().start();
    		return;
    	}*/
    	System.out.println("Ended align correction");
		Robot.getInstance().state = State.WAITING;
    	//while(!OI.getInstance().fireButton.get() && OI.getInstance().alignButton.get()) {SmartDashboard.putBoolean("Ready to shoot", true);}
    	/*Robot.getInstance().state = State.OFF;
    	System.out.println("Ended align waiting");
    	SmartDashboard.putBoolean("Ready to shoot", false);*/
    }
    
    public void start() {
    	System.out.println("Align just started");
    	super.start();
    }
    
    public class AlignStartCommand extends NRCommand {
    	
    	public AlignStartCommand() {
    		//addSequential(new IntakeArmBottomHeightCommandGroup());
    	}
    	
    	@Override
    	public void onStart() {
    		Robot.getInstance().state = State.ALIGNING;
    	}
    }
}
