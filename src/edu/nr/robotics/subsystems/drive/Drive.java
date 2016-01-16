package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.ChiefSubsystem;
import edu.nr.lib.navx.NavX;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive extends ChiefSubsystem {

	//This is a constant that is used for driving with PID control
	//It's a magic number.
	public static final double JOYSTICK_DRIVE_P = 0.25;

	
	private static Drive singleton;
	private PIDController leftPid, rightPid;
	CANTalon leftTalon, rightTalon;
	Encoder leftEnc, rightEnc;
	
	//These values are right so that one distance unit given by the encoders is one foot
	private final double ticksPerRev = 360, wheelDiameter = 0.33333; //TicksPerRev was 256 in 2015, wheelDiameter was 0.4975 in 2015
		
	private Drive() {
		leftTalon = new CANTalon(RobotMap.TALON_LEFT_A);
		leftTalon.enableBrakeMode(true);
		
		CANTalon tempLeftTalon = new CANTalon(RobotMap.TALON_LEFT_B);
		tempLeftTalon.changeControlMode(TalonControlMode.Follower);
		tempLeftTalon.set(leftTalon.getDeviceID());
		tempLeftTalon.enableBrakeMode(true);

		rightTalon = new CANTalon(RobotMap.TALON_RIGHT_A);
		rightTalon.enableBrakeMode(true);
		
		leftEnc = new Encoder(RobotMap.ENCODER_LEFT_A, RobotMap.ENCODER_LEFT_B);
		rightEnc = new Encoder(RobotMap.ENCODER_RIGHT_A, RobotMap.ENCODER_RIGHT_B);
		
		CANTalon tempRightTalon = new CANTalon(RobotMap.TALON_RIGHT_B);
		tempRightTalon.changeControlMode(TalonControlMode.Follower);
		tempRightTalon.set(rightTalon.getDeviceID());
		tempRightTalon.enableBrakeMode(true);
		
		leftEnc.setPIDSourceType(PIDSourceType.kRate);
		rightEnc.setPIDSourceType(PIDSourceType.kRate);

		double distancePerPulse = (1 / ticksPerRev) * Math.PI * wheelDiameter;
		
		//Max speed of robot is not just 1 foot per second, but in order for our PIDController to work, the scale of encoder rate
		//in ft/sec must be on a scale of -1 to 1 (so it can be used to calculate motor output), so this converts it
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

                
        SmartDashboard.putNumber("Arcade Left Motors", leftMotorSpeed);
        SmartDashboard.putNumber("Arcade Right Motors", rightMotorSpeed);
        SmartDashboard.putBoolean("Half Speed", false);
        
        tankDrive(leftMotorSpeed, -rightMotorSpeed);
	}
	
    //Notable is that to go forward, they should be opposite signs, and to turn in place, they should be the same sign.
	public void tankDrive(double leftMotorSpeed, double rightMotorSpeed) {
		if(leftPid.isEnabled() && rightPid.isEnabled())
        {
        	leftPid.setSetpoint(leftMotorSpeed);
            rightPid.setSetpoint(rightMotorSpeed);
        }
        else
        {
        	setRawMotorSpeed(leftMotorSpeed, rightMotorSpeed);
        }
	}
	
	public void setPIDSetpoint(double left, double right)
	{
		setPIDEnabled(true);
    	
		leftPid.setSetpoint(left);
        rightPid.setSetpoint(right);
	}
	
	public void setRawMotorSpeed(double left, double right)
	{
		setPIDEnabled(false);
		
		leftTalon.set(left);
		rightTalon.set(right);
	}
	
	public boolean getPIDEnabled()
	{
		return leftPid.isEnabled() && rightPid.isEnabled();
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

	public double getAngleDegrees() 
	{
		return NavX.getInstance().getYaw();
	}
	
	public double getAngleRadians()
	{
		return getAngleDegrees() * (Math.PI)/180d;
	}

}

