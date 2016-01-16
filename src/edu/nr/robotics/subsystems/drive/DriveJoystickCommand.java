package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.CMD;
import edu.nr.lib.NRMath;
import edu.nr.robotics.OI;
import edu.nr.robotics.subsystems.drive.Drive;

/**
 *
 */
public class DriveJoystickCommand extends CMD 
{
	private double oldTurn;
	
    public DriveJoystickCommand() 
    {
        requires(Drive.getInstance());
    }

    @Override
	protected void onStart()
    {
		oldTurn = OI.getInstance().getArcadeMoveValue();
	}
    
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void onExecute()
    {
    	if(OI.getInstance().drivingModeChooser.getSelected().equals("arcade"))
    	{
	    	double moveValue = OI.getInstance().getArcadeMoveValue();
	    	double driveMagnitude = moveValue;
			if(OI.getInstance().reverseDriveDirection())
	    		driveMagnitude  *= -1;
	    		    	
	    	double rotateValue = OI.getInstance().getArcadeTurnValue()/2;
	    	
            NRMath.squareWithSign(moveValue);
            NRMath.squareWithSign(rotateValue);

            double negInertia = rotateValue - oldTurn;
            
            // Negative inertia!
            double negInertiaScalar;
            
            if (rotateValue * negInertia > 0) {
                negInertiaScalar = 0.5;
            } else {
                if (Math.abs(rotateValue) > 0.65) {
                    negInertiaScalar = 1.0;
                } else {
                    negInertiaScalar = 0.6;
                }
            }
            
            rotateValue = rotateValue + negInertia * negInertiaScalar;
	    	
	    	Drive.getInstance().arcadeDrive(OI.getInstance().speedMultiplier*driveMagnitude, OI.getInstance().speedMultiplier*rotateValue);
	    	
	    	oldTurn = rotateValue;
    	}
    	else{
    		//Get values of the joysticks
    		double left = OI.getInstance().getTankLeftValue();
        	double right = OI.getInstance().getTankRightValue();
    		
        	//Do the math for turning
        	if(Math.abs(left - right) < .25)
        	{
        		left = (Math.abs(left) + Math.abs(right))/2*Math.signum(left);
        		right = (Math.abs(left) + Math.abs(right))/2*Math.signum(right);
        	}
        	
    		// cube the inputs (while preserving the sign) to increase fine control while permitting full power
            right = right*right*right;
            left = left*left*left;

    		Drive.getInstance().tankDrive(OI.getInstance().speedMultiplier*left, OI.getInstance().speedMultiplier*right);

    	}
    }

    //Always return false for a default command
    protected boolean isFinished() 
    {
        return false;
    }

    // Called once after isFinished returns true
    protected void onEnd(boolean interrupted) 
    {
    	Drive.getInstance().arcadeDrive(0, 0);
    }
}