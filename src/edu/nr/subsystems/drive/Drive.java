package edu.nr.subsystems.drive;

import edu.nr.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive extends Subsystem {

	private static Drive singleton;
	private PIDController leftPid, rightPid;
	CANTalon leftTalon, rightTalon;


	
	private Drive() {
		leftTalon = new CANTalon(RobotMap.leftDriveTalon1);
		leftTalon.enableBrakeMode(true);

		rightTalon = new CANTalon(RobotMap.rightDriveTalon1);
		rightTalon.enableBrakeMode(true);

	}
	
	public static Drive getInstance()
    {
		init();
        return singleton;
    }
	
	public static void init()
	{
		if(singleton == null)
		{
			singleton = new Drive();
		}
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void arcadeDrive(double moveValue, double rotateValue)
	{
		this.arcadeDrive(moveValue, rotateValue, false);
	}
	
	public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) 
	{
		double leftMotorSpeed;
        double rightMotorSpeed;

        moveValue = limit(moveValue);
        rotateValue = limit(rotateValue);

        if (squaredInputs) 
        {
            // square the inputs (while preserving the sign) to increase fine control while permitting full power
            if (moveValue >= 0.0) {
                moveValue = (moveValue * moveValue);
            } else {
                moveValue = -(moveValue * moveValue);
            }
            if (rotateValue >= 0.0) {
                rotateValue = (rotateValue * rotateValue);
            } else {
                rotateValue = -(rotateValue * rotateValue);
            }
        }

        if (moveValue > 0.0)
        {
            if (rotateValue > 0.0) 
            {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } 
            else
            {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } 
        else 
        {
            if (rotateValue > 0.0) 
            {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            } 
            else 
            {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }
        rightMotorSpeed = -rightMotorSpeed;
        
        SmartDashboard.putNumber("Arcade Left Motors", leftMotorSpeed);
        SmartDashboard.putNumber("Arcade Right Motors", rightMotorSpeed);
        SmartDashboard.putBoolean("Half Speed", false);
        
        if(leftPid.isEnable() && rightPid.isEnable())
        {
        	leftPid.setSetpoint(leftMotorSpeed);
            rightPid.setSetpoint(rightMotorSpeed);
        }
        else
        {
        	setRawMotorSpeed(leftMotorSpeed, -rightMotorSpeed);
        }
	}
	
	
	//Source for a lot of this from 254's code from 2015.
	public void arcadeDrive(double throttle, double wheel, double oldWheel, boolean squaredInputs) 
	{
        double leftMotorSpeed;
        double rightMotorSpeed;

        throttle = limit(throttle);
        wheel = limit(wheel);
        
        if (squaredInputs) 
        {
            // square the inputs (while preserving the sign) to increase fine control while permitting full power
            if (throttle >= 0.0) {
            	throttle = (throttle * throttle);
            } else {
            	throttle = -(throttle * throttle);
            }
            if (wheel >= 0.0) {
                wheel = (wheel * wheel);
            } else {
            	wheel = -(wheel * wheel);
            }
        }
        
        double negInertia = wheel - oldWheel;
               
        // Negative inertia!
        double negInertiaScalar;
        
        if (wheel * negInertia > 0) {
            negInertiaScalar = 2.5;
        } else {
            if (Math.abs(wheel) > 0.65) {
                negInertiaScalar = 5.0;
            } else {
                negInertiaScalar = 3.0;
            }
        }
        
        wheel = wheel + negInertia * negInertiaScalar;
        
        rightMotorSpeed = leftMotorSpeed = throttle;
        leftMotorSpeed += wheel;
        rightMotorSpeed -= wheel;

        if (leftMotorSpeed > 1.0) {
        	rightMotorSpeed -= leftMotorSpeed - 1.0;
            leftMotorSpeed = 1.0;
        } else if (rightMotorSpeed > 1.0) {
        	leftMotorSpeed -= rightMotorSpeed - 1.0;
        	rightMotorSpeed = 1.0;
        } else if (leftMotorSpeed < -1.0) {
        	rightMotorSpeed += -1.0 - leftMotorSpeed;
            leftMotorSpeed = -1.0;
        } else if (rightMotorSpeed < -1.0) {
        	leftMotorSpeed += -1.0 - rightMotorSpeed;
        	rightMotorSpeed = -1.0;
        }
        
        SmartDashboard.putNumber("Arcade Left Motors", leftMotorSpeed);
        SmartDashboard.putNumber("Arcade Right Motors", rightMotorSpeed);
        SmartDashboard.putBoolean("Half Speed", false);
        
        if(leftPid.isEnable() && rightPid.isEnable())
        {
        	leftPid.setSetpoint(leftMotorSpeed);
            rightPid.setSetpoint(-rightMotorSpeed);
        }
        else
        {
        	setRawMotorSpeed(leftMotorSpeed, -rightMotorSpeed);
        }
	}
	
	public void setRawMotorSpeed(double left, double right)
	{
		setPIDEnabled(false);
		
		leftTalon.set(left);
		rightTalon.set(-right);
	}
	
	public void setPIDEnabled(boolean enabled)
	{
		if(enabled && !leftPid.isEnable())
		{
			leftPid.enable();
			rightPid.enable();
		}
		else if(!enabled && leftPid.isEnable())
		{
			leftPid.disable();
			rightPid.disable();
		}
	}
	
	private double limit(double num)
	{
		if (num > 1.0) {
            return 1.0;
        }
        if (num < -1.0) {
            return -1.0;
        }
        return num;
	}


	public void putSmartDashboardInfo() {
		//TODO: Push smart dashboard info (eg. NavX data, encoder data)
	}
}

