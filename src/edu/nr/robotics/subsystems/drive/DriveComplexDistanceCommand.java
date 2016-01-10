package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.CMD;
import edu.nr.lib.FieldCentric;
import edu.nr.lib.path.OneDimensionalPath;

/**
 *
 */
public class DriveComplexDistanceCommand extends CMD {

	OneDimensionalPath path; //from 0 to 1
	double Kv,Ka,Kp,Kd;
	double startingTime;
	double prevTime;
	double errorLast;
	
    public DriveComplexDistanceCommand(OneDimensionalPath path, double Kv, double Ka, double Kp, double Kd) {
    	this.path = path;
    	this.startingTime = System.currentTimeMillis();
    	this.prevTime = 0;
    	this.errorLast = 0;
    	
    	this.Kv = Kv;
        this.Ka = Ka;
        this.Kp = Kp;
        this.Kd = Kd;
    	
        requires(Drive.getInstance());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return FieldCentric.getInstance().getDistance() > path.getDistance();
    }
    
	@Override
	protected void onStart() {
		FieldCentric.getInstance().reset();
	}

	@Override
	protected void onExecute() {
		double time = System.currentTimeMillis() - startingTime;
		double dt = time - prevTime;
		prevTime = time;
		
    	double error = path.getPosition(time) - FieldCentric.getInstance().getX();
    	double errorDeriv = (error - errorLast) / dt;
    	double motorSpeed = Kv * path.getSpeed(time) + Ka * path.getAcc(time) + Kp * error + Kd * errorDeriv;
    	errorLast = error;
		
		Drive.getInstance().arcadeDrive(motorSpeed, 0);
		
	}

	@Override
	protected void onEnd(boolean interrupted) {	
	}
}
