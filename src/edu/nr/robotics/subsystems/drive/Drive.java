package edu.nr.robotics.subsystems.drive;

import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive extends Subsystem {

	//This is a constant that is used for driving with PID control
	//It's a magic number.
	public static final double JOYSTICK_DRIVE_P = 0.25;

	
	private static Drive singleton;
	private PIDController leftPid, rightPid;
	CANTalon leftTalon, rightTalon;
	Encoder leftEnc, rightEnc;
	
	//ticksPerRev is something...
	private double ticksPerRev = 1440, wheelDiameter = .33; //TicksPerRev was 256 in 2015, wheelDiameter was 0.4975 in 2015
	//TODO Get ticksPerRev and wheelDiameter
	
	private Drive() {
		leftTalon = new CANTalon(RobotMap.TALON_LEFT_A);
		leftTalon.enableBrakeMode(true);
		
		CANTalon tempLeftTalon = new CANTalon(RobotMap.TALON_LEFT_B);
		tempLeftTalon.changeControlMode(ControlMode.Follower);
		tempLeftTalon.set(leftTalon.getDeviceID());
		tempLeftTalon.enableBrakeMode(true);

		rightTalon = new CANTalon(RobotMap.TALON_RIGHT_A);
		rightTalon.enableBrakeMode(true);
		
		leftEnc = new Encoder(RobotMap.ENCODER_LEFT_A, RobotMap.ENCODER_LEFT_B);
		rightEnc = new Encoder(RobotMap.ENCODER_RIGHT_A, RobotMap.ENCODER_RIGHT_B);
		
		CANTalon tempRightTalon = new CANTalon(RobotMap.TALON_RIGHT_B);
		tempRightTalon.changeControlMode(ControlMode.Follower);
		tempRightTalon.set(rightTalon.getDeviceID());
		tempRightTalon.enableBrakeMode(true);
		
		leftEnc.setPIDSourceParameter(PIDSourceParameter.kRate);
		rightEnc.setPIDSourceParameter(PIDSourceParameter.kRate);

		double distancePerPulse = (1 / ticksPerRev) * Math.PI * wheelDiameter;
		
		//Max speed of robot is 20 ft/sec, so in order for our PIDController to work, the scale of encoder rate
		//in ft/sec must be on a scale of -1 to 1 (so it can be used to calculate motor output)
		leftEnc.setDistancePerPulse(distancePerPulse / RobotMap.MAX_ENCODER_RATE);
		rightEnc.setDistancePerPulse(distancePerPulse / RobotMap.MAX_ENCODER_RATE);
				
		leftPid = new PIDController(JOYSTICK_DRIVE_P, 0, 0, 1, leftEnc, leftTalon);
		rightPid = new PIDController(JOYSTICK_DRIVE_P, 0, 0, 1, rightEnc, rightTalon);
		leftPid.enable();
		rightPid.enable();
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
        setDefaultCommand(new DriveJoystickCommand());
    }
    
	/**
	 * @param value Maximum change in voltage, in volts / sec.
	 */
	public void setTalonRampRate(double value)
	{
		leftTalon.setVoltageRampRate(value);
		rightTalon.setVoltageRampRate(value);
	}

    /**
     * @param moveValue The speed, from -1 to 1 (inclusive), that the robot should go at. 1 is max forward, 0 is stopped, -1 is max backward
     * @param rotateValue The speed, from -1 to 1 (inclusive), that the robot should turn at. 1 is max right, 0 is stopped, -1 is max left
     */
    public void arcadeDrive(double moveValue, double rotateValue)
	{	
    	double leftMotorSpeed, rightMotorSpeed;
        rightMotorSpeed = leftMotorSpeed = moveValue;
        leftMotorSpeed += rotateValue;
        rightMotorSpeed -= rotateValue;

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
        
        tankDrive(leftMotorSpeed, -rightMotorSpeed);
	}
	
    //Notable is that to go forward, they should be opposite signs, and to turn in place, they should be the same sign.
	public void tankDrive(double leftMotorSpeed, double rightMotorSpeed) {
		if(leftPid.isEnable() && rightPid.isEnable())
        {
        	leftPid.setSetpoint(leftMotorSpeed);
            rightPid.setSetpoint(rightMotorSpeed);
        }
        else
        {
        	setRawMotorSpeed(leftMotorSpeed, rightMotorSpeed);
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
		if(enabled)
		{
			leftPid.enable();
			rightPid.enable();
		}
		else 
		{
			leftPid.disable();
			rightPid.disable();
		}
	}
	
	public void resetEncoders()
	{
		leftEnc.reset();
		rightEnc.reset();
	}
	
	public double getEncoderLeftDistance()
	{
		return leftEnc.getDistance() * RobotMap.MAX_ENCODER_RATE;
	}
	
	public double getEncoderRightDistance()
	{
		return -rightEnc.getDistance() * RobotMap.MAX_ENCODER_RATE;
	}
	
	public double getEncoderLeftSpeed()
	{
		return leftEnc.getRate() * RobotMap.MAX_ENCODER_RATE;
	}
	
	public double getEncoderRightSpeed()
	{
		return -rightEnc.getRate() * RobotMap.MAX_ENCODER_RATE;
	}
	
	public double getEncoderAverageDistance()
	{
		return (getEncoderLeftDistance() + getEncoderRightDistance()) / 2f;
	}
	
	public double getEncoderAverageSpeed()
	{
		return (getEncoderRightSpeed() + getEncoderLeftSpeed()) / 2;
	}
	
	public void putSmartDashboardInfo()
	{	
		SmartDashboard.putNumber("Encoders Distance Ave", this.getEncoderAverageDistance());
		SmartDashboard.putNumber("Encoders Speed Ave", this.getEncoderAverageSpeed());
		SmartDashboard.putData("Encoder Left", leftEnc);
		SmartDashboard.putData("Encoder Right", rightEnc);
		
		SmartDashboard.putData("PID Left", leftPid);
		SmartDashboard.putData("PID Right", rightPid);
	}
}

