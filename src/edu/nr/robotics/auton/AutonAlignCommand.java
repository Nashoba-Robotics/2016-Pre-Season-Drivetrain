package edu.nr.robotics.auton;

import edu.nr.lib.network.AndroidConnection;
import edu.nr.robotics.OI;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.commandgroups.AlignCommandGroup;
import edu.nr.robotics.commandgroups.AlignSubcommandGroup;
import edu.nr.robotics.subsystems.hood.Hood;
import edu.nr.robotics.subsystems.hood.HoodMoveDownUntilLimitSwitchCommand;
import edu.nr.robotics.subsystems.loaderroller.LaserCannonTriggerCommand;
import edu.nr.robotics.subsystems.shooter.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutonAlignCommand extends CommandGroup {
    
	long startTime;
	
    public  AutonAlignCommand() {
    	addSequential(new HoodMoveDownUntilLimitSwitchCommand());
    	addSequential(new WaitCommand(0.25));
        addSequential(new AlignSubcommandGroup());
    }
    
    public  AutonAlignCommand(long startTime) {
    	this();
    	this.startTime = startTime;
    }
    
    @Override
    public void initialize() {
    	startTime = System.currentTimeMillis();
    }
    
    @Override
    public void end() {
    	AndroidConnection connection = new AndroidConnection();
    	connection.run();
    	if(!connection.goodToGo()) { 
    		System.out.println("Android connection not good to go");
    		return;
    	}

    	boolean flag = false;

    	double checkDist = RobotMap.TURN_THRESHOLD;

    	if ((Math.abs(System.currentTimeMillis() - startTime) > 5000)) {
    		checkDist = 2;
    	}

    	if(Math.abs(System.currentTimeMillis() - startTime) < 5000 && Hood.getInstance().get() - Hood.distanceToAngle(connection.getDistance()) > RobotMap.HOOD_THRESHOLD ) {flag = true;}
    	if(Math.abs(connection.getTurnAngle()) > checkDist) {flag = true;}
    	if(Math.abs(System.currentTimeMillis() - startTime) < 5000 && Shooter.getInstance().getScaledSpeed() < RobotMap.SHOOTER_FAST_SPEED - RobotMap.SHOOTER_THRESHOLD) {flag = true;}
    	if(flag) {
    		new AutonAlignCommand(startTime).start();
    		return;
    	}

    	new LaserCannonTriggerCommand().start();
    }
}
