package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.NRMath;
import edu.nr.lib.PID;
import edu.nr.lib.Periodic;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.lib.navx.NavX;
import edu.nr.robotics.OI;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive extends Subsystem implements SmartDashboardSource, Periodic{

	/**
	 *  This is a constant that is used for driving with PID control
	 */
	public static final double JOYSTICK_DRIVE_P = 0.25;

	private static Drive singleton;
	private PID leftPid, rightPid;
	CANTalon leftTalon, rightTalon, tempLeftTalon, tempRightTalon;
	Encoder leftEnc, rightEnc;

	// These values are right so that one distance  
	// unit given by the encoders is one meter
	private final double ticksPerRev = 360, wheelDiameter = 0.33333;

	private Drive() {
		leftTalon = new CANTalon(RobotMap.TALON_LEFT_A);
		leftTalon.enableBrakeMode(true);

		tempLeftTalon = new CANTalon(RobotMap.TALON_LEFT_B);
		tempLeftTalon.changeControlMode(TalonControlMode.Follower);
		tempLeftTalon.set(leftTalon.getDeviceID());
		tempLeftTalon.enableBrakeMode(true);
		
		rightTalon = new CANTalon(RobotMap.TALON_RIGHT_A);
		rightTalon.enableBrakeMode(true);
		rightTalon.setInverted(true);

		leftEnc = new Encoder(RobotMap.ENCODER_LEFT_A, RobotMap.ENCODER_LEFT_B);
		rightEnc = new Encoder(RobotMap.ENCODER_RIGHT_A, RobotMap.ENCODER_RIGHT_B);

		tempRightTalon = new CANTalon(RobotMap.TALON_RIGHT_B);
		tempRightTalon.changeControlMode(TalonControlMode.Follower);
		tempRightTalon.set(rightTalon.getDeviceID());
		tempRightTalon.enableBrakeMode(true);

		leftEnc.setPIDSourceType(PIDSourceType.kRate);

		leftEnc.setReverseDirection(true);

		rightEnc.setPIDSourceType(PIDSourceType.kRate);

		double distancePerPulse = 1 / ticksPerRev * Math.PI * wheelDiameter;

		leftEnc.setDistancePerPulse(distancePerPulse / RobotMap.MAX_SPEED);
		rightEnc.setDistancePerPulse(distancePerPulse / RobotMap.MAX_SPEED);

		leftPid = new PID(JOYSTICK_DRIVE_P, 0, 0, 1, leftEnc, leftTalon);
		rightPid = new PID(JOYSTICK_DRIVE_P, 0, 0, 1, rightEnc, rightTalon);
	}

	public static Drive getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new Drive();
		}
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveJoystickCommand());
	}

	/**
	 * Set the voltage ramp rate for both drive talons. 
	 * Limits the rate at which the throttle will change.
	 * 
	 * @param rampRate
	 *            Maximum change in voltage, in volts / sec.
	 */
	public void setTalonRampRate(double rampRate) {
		leftTalon.setVoltageRampRate(rampRate);
		rightTalon.setVoltageRampRate(rampRate);
	}

	/**
	 * Sets left and right motor speeds to the speeds needed for the given move
	 * and turn values
	 * 
	 * @param move
	 *            The speed, from -1 to 1 (inclusive), that the robot should go
	 *            at. 1 is max forward, 0 is stopped, -1 is max backward
	 * @param turn
	 *            The speed, from -1 to 1 (inclusive), that the robot should
	 *            turn at. 1 is max right, 0 is stopped, -1 is max left
	 */
	public void arcadeDrive(double move, double turn) {
		arcadeDrive(move,turn,false);
	}
	
	/**
	 * Sets left and right motor speeds to the speeds needed for the given move
	 * and turn values, multiplied by the OI speed multiplier if the speed multiplier
	 * parameter is true. If you don't care about the speed multiplier parameter, you
	 * might want to use {@link arcadeDrive(double move, double turn)}
	 * 
	 * @param move
	 *            The speed, from -1 to 1 (inclusive), that the robot should go
	 *            at. 1 is max forward, 0 is stopped, -1 is max backward
	 * @param turn
	 *            The speed, from -1 to 1 (inclusive), that the robot should
	 *            turn at. 1 is max right, 0 is stopped, -1 is max left
	 * @param speedMultiplier 
	 * 			  whether or not to use the OI speed multiplier
	 *            It should really only be used for operator driving
	 * 
	 */
	public void arcadeDrive(double move, double turn, boolean speedMultiplier) {
		move = NRMath.limit(move);
		turn = NRMath.limit(turn);
		double leftMotorSpeed, rightMotorSpeed;
		rightMotorSpeed = leftMotorSpeed = move;
		leftMotorSpeed += turn;
		rightMotorSpeed -= turn;

		if (move > 0.0) {
			if (turn > 0.0) {
				leftMotorSpeed = move - turn;
				rightMotorSpeed = Math.max(move, turn);
			} else {
				leftMotorSpeed = Math.max(move, -turn);
				rightMotorSpeed = move + turn;
			}
		} else {
			if (turn > 0.0) {
				leftMotorSpeed = -Math.max(-move, turn);
				rightMotorSpeed = move + turn;
			} else {
				leftMotorSpeed = move - turn;
				rightMotorSpeed = -Math.max(-move, -turn);
			}
		}

		double multiplier = speedMultiplier? OI.getInstance().speedMultiplier : 1;
		tankDrive(leftMotorSpeed*multiplier, rightMotorSpeed*multiplier);
	}

	/**
	 * Sets both left and right motors to the given speed If PID is enabled,
	 * then sets the PID setpoints, otherwise sets the raw motor speeds Notable
	 * is that to go forward, they should be opposite signs, and to turn in
	 * place, they should be the same sign.
	 * 
	 * @param left
	 *            the left motor speed
	 * @param right
	 *            the right motor speed
	 */
	public void tankDrive(double left, double right) {
		if (getPIDEnabled()) {
			setPIDSetpoint(left, right);
		} else {
			setRawMotorSpeed(left, right);
		}
	}

	/**
	 * Enables the PID and sets the setpoint for the left and right motors
	 * 
	 * @param left
	 *            the left motor speed, from -1 to 1
	 * @param right
	 *            the right motor speed, from -1 to 1
	 */
	public void setPIDSetpoint(double left, double right) {
		setPIDSetpoint(left, right, true);
	}

	/**
	 * Optionally enables the PID and sets the setpoint for the left and right
	 * motors
	 * 
	 * @param left
	 *            the left motor speed, from -1 to 1
	 * @param right
	 *            the right motor speed, from -1 to 1
	 * @param enable
	 *            whether or not to enable the PID before setting the setpoints
	 */
	public void setPIDSetpoint(double left, double right, boolean enable) {
		if (enable) {
			setPIDEnabled(true);
		}

		leftPid.setSetpoint(left);
		rightPid.setSetpoint(right);
	}

	/**
	 * Disables the PID and sets the motor speed for the left and right motors
	 * A raw motor speed is actually a scaled voltage value
	 * 
	 * @param left
	 *            the left motor speed, from -1 to 1
	 * @param right
	 *            the right motor speed, from -1 to 1
	 */
	public void setRawMotorSpeed(double left, double right) {
		setPIDEnabled(false);
		
		leftTalon.set(left);
		rightTalon.set(right);
	}

	/**
	 * Gets whether the PIDs are enabled or not. If both are enabled, then
	 * returns true, otherwise returns false
	 * 
	 * @return whether the PIDs are enabled
	 */
	public boolean getPIDEnabled() {
		return leftPid.isEnable() && rightPid.isEnable();
	}

	/**
	 * Enables or disables both left and right PIDs. Disabling also resets the
	 * integral term and the previous error of the PID, and sets the output to
	 * zero
	 * 
	 * Doesn't do anything if they are already that state.
	 * 
	 * @param enabled
	 *            whether to enable (true) or disable (false)
	 */
	public void setPIDEnabled(boolean enabled) {
		if (enabled) {
			if(!getPIDEnabled()) {
				leftPid.enable();
				rightPid.enable();
			}
		} else {
			if(getPIDEnabled()) {
				leftPid.reset();
				rightPid.reset();
			}
		}
	}

	/**
	 * Resets both the left and right encoders
	 */
	public void resetEncoders() {
		leftEnc.reset();
		rightEnc.reset();
	}

	/**
	 * Get the distance the left encoder has driven since the last reset
	 * 
	 * @return The distance the left encoder has driven since the last reset as scaled by the value from setDistancePerPulse().
	 */
	public double getEncoderLeftDistance() {
		return -leftEnc.getDistance();
	}

	/**
	 * Get the distance the right encoder has driven since the last reset
	 * 
	 * @return The distance the right encoder has driven since the last reset as scaled by the value from setDistancePerPulse().
	 */
	public double getEncoderRightDistance() {
		return rightEnc.getDistance();
	}

	/**
	 * Get the current rate of the left encoder. 
	 * Units are distance per second as scaled by the value from setDistancePerPulse().
	 * 
	 * @return The current rate of the encoder
	 */
	public double getEncoderLeftSpeed() {
		return -leftEnc.getRate();
	}

	/**
	 * Get the current rate of the right encoder. 
	 * Units are distance per second as scaled by the value from setDistancePerPulse().
	 * 
	 * @return The current rate of the encoder
	 */
	public double getEncoderRightSpeed() {
		return rightEnc.getRate();
	}

	/**
	 * Gets the average distance of the encoders
	 * 
	 * @return 
	 * 		The average distance the encoders have driven since the 
	 * 		last reset as scaled by the value from setDistancePerPulse().
	 */
	public double getEncoderAverageDistance() {
		return (getEncoderLeftDistance() + getEncoderRightDistance()) / 2;
	}

	/**
	 * Get the average current rate of the encoders. 
	 * Units are distance per second as scaled by the value from setDistancePerPulse().
	 * 
	 * @return The current average rate of the encoders
	 */
	public double getEncoderAverageSpeed() {
		return (getEncoderRightSpeed() + getEncoderLeftSpeed()) / 2;
	}

	/**
	 * Sends data to the SmartDashboard
	 */
	@Override
	public void smartDashboardInfo() {
		SmartDashboard.putNumber("Encoders Distance Ave", getEncoderAverageDistance());
		SmartDashboard.putNumber("Encoders Speed Ave", getEncoderAverageSpeed());
		SmartDashboard.putData("Encoder Left", leftEnc);
		SmartDashboard.putData("Encoder Right", rightEnc);

		SmartDashboard.putData("PID Left", leftPid);
		SmartDashboard.putData("PID Right", rightPid);
		
		SmartDashboard.putNumber("Drive Talon Left Out", leftTalon.get());
		SmartDashboard.putNumber("Drive Talon Right Out", rightTalon.get());

		SmartDashboard.putNumber("Drive Talon Average Current Draw", (rightTalon.getOutputCurrent() + rightTalon.getOutputCurrent() + tempLeftTalon.getOutputCurrent() + tempRightTalon.getOutputCurrent())/4);
		SmartDashboard.putNumber("Drive Right Main Talon Current", rightTalon.getOutputCurrent());
		SmartDashboard.putNumber("Drive Left Main Talon Current", leftTalon.getOutputCurrent());
		SmartDashboard.putNumber("Drive Right Temp Talon Current", tempRightTalon.getOutputCurrent());
		SmartDashboard.putNumber("Drive Left Temp Talon Current", tempLeftTalon.getOutputCurrent());

	}

	@Override
	public void periodic() {
		if(leftTalon.getOutputCurrent() > 40 || tempLeftTalon.getOutputCurrent() > 40) {
			leftPid.setSetpoint(leftPid.getSetpoint()*0.99);
		}
		if(rightTalon.getOutputCurrent() > 40 || tempRightTalon.getOutputCurrent() > 40) {
			rightPid.setSetpoint(rightPid.getSetpoint()*0.99);
		}
	}

}
