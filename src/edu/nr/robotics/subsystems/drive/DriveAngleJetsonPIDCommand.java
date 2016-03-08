package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.AngleGyroCorrectionSource;
import edu.nr.lib.AngleUnit;
import edu.nr.lib.NRCommand;
import edu.nr.lib.PID;
import edu.nr.lib.network.UDPServer;
import edu.nr.robotics.OI;
import edu.nr.robotics.Robot;
import edu.nr.robotics.commandgroups.AlignCommandGroup;
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
    protected void onExecute() {
    	if(canRun) {
    		SmartDashboard.putData("Jetson Angle PID", pid);
	    	SmartDashboard.putNumber("Jetson Angle PID Error", pid.getError());
			
			if(Math.abs(pid.getError()) > integralDisableDistance) {
				pid.setPID(SmartDashboard.getNumber("Turn Angle P"), 0, SmartDashboard.getNumber("Turn Angle D"));
				pid.resetTotalError();
			} else {
				pid.setPID(SmartDashboard.getNumber("Turn Angle P"), SmartDashboard.getNumber("Turn Angle I"), SmartDashboard.getNumber("Turn Angle D"));
			}
			
			if(Math.signum(pid.getError()) != Math.signum(pid.getTotalError())) {
				pid.resetTotalError();
			}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinishedNR() {
    	if(!canRun)
    		return true;
    	if(Math.abs(pid.getError()) < 0.5) {
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
	}

	@Override
	protected void onStart() {
		System.out.println("Drive angle Jetson PID start");
		try {
			if(this.getGroup() instanceof AlignSubcommandGroup) {
				angle = ((AlignSubcommandGroup )this.getGroup()).getJetsonPacket().getTurnAngle();
			} else {
					angle = UDPServer.getInstance().getLastPacket().getTurnAngle();
			}
		} catch(NullPointerException e) {
			System.out.println("No value from Jetson yet, so we can't run " + this.getName());
			canRun = false;
			return;
		}
		/*if(Math.abs(angle) < .5) {
			angle = 0;
		} else {
			angle = angle - (0.2768*angle - 3.1668) * Math.signum(angle);
		}*/
		correction = new AngleGyroCorrectionSource(AngleUnit.DEGREE);
		pid = new PID(SmartDashboard.getNumber("Turn Angle P"),SmartDashboard.getNumber("Turn Angle I"),SmartDashboard.getNumber("Turn Angle D"), correction, new AngleController());
		pid.setOutputRange(-0.3, 0.3);
		pid.setSetpoint(angle);
    	pid.enable();
	}
}
