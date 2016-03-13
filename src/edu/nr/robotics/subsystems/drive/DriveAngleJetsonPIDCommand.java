package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.AngleGyroCorrectionSource;
import edu.nr.lib.AngleUnit;
import edu.nr.lib.NRCommand;
import edu.nr.lib.PID;
import edu.nr.lib.network.UDPServer;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.commandgroups.AlignSubcommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveAngleJetsonPIDCommand extends NRCommand {

	PID pid;
	
	double angle;
	
	boolean canRun;
	
	AngleGyroCorrectionSource correction;
	boolean resetCorrection;
	
	double integralDisableDistance = 5;

	double accuracyFinishCount = 3;
	double currentCount = 0;
	
    public DriveAngleJetsonPIDCommand() {
    	requires(Drive.getInstance());
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void onExecute() {
    	if(canRun) {			
			if(Math.abs(pid.getError()) > integralDisableDistance) {
				pid.setPID(RobotMap.TURN_P, 0, RobotMap.TURN_D);
				pid.resetTotalError();
			} else {
				pid.setPID(RobotMap.TURN_P, RobotMap.TURN_I, RobotMap.TURN_D);
			}
			
			if(Math.signum(pid.getError()) != Math.signum(pid.getTotalError())) {
				pid.resetTotalError();
			}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinishedNR() {
    	if(!canRun)
    		return true;
    	if(Math.abs(pid.getError()) < RobotMap.TURN_THRESHOLD) {
    		currentCount++;
    		if(currentCount > 3)
    			pid.resetTotalError();

    	} else {
    		currentCount = 0;
    	}
    	
    	return currentCount > accuracyFinishCount;
    }

	@Override
	protected void onEnd(boolean interrupted) {
		System.out.println("Drive angle Jetson PID finish interrupted? " + interrupted);
		if(canRun) {
			pid.disable();
		}
		canRun = true;
	}

	@Override
	protected void onStart() {
		
		if(UDPServer.getInstance().getLastPacket().getPacketNum() == 0) {
			canRun = false;
			return;
		}
		
		System.out.println("Drive angle Jetson PID start");
		angle = UDPServer.getInstance().getLastPacket().getTurnAngle();
		

		/*if(Math.abs(angle) < .5) {
			angle = 0;
		} else {
			angle = angle - (0.2768*angle - 3.1668) * Math.signum(angle);
		}*/
		correction = new AngleGyroCorrectionSource(AngleUnit.DEGREE);
		pid = new PID(RobotMap.TURN_P,RobotMap.TURN_I,RobotMap.TURN_D, correction, new AngleController());
		pid.setOutputRange(-0.3, 0.3);
		pid.setSetpoint(angle);
    	pid.enable();
	}
}
