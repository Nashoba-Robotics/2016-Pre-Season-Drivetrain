package edu.nr.robotics.subsystems.drive;

import edu.nr.lib.PID;
import edu.nr.lib.SmartDashboardSource;
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
public class Drive extends Subsystem implements SmartDashboardSource {

	// This is a constant that is used for driving with PID control
	public static final double JOYSTICK_DRIVE_P = 0.25;

	private static Drive singleton;
	private PID leftPid, rightPid;
	CANTalon leftTalon, rightTalon;
	Encoder leftEnc, rightEnc;

	// These values are right so that one distance unit given by the encoders is
	// one foot
	private final double ticksPerRev = 360, wheelDiameter = 0.33333;

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
	 * @param value
	 *            Maximum change in voltage, in volts / sec.
	 */
	public void setTalonRampRate(double value) {
		leftTalon.setVoltageRampRate(value);
		rightTalon.setVoltageRampRate(value);
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

		tankDrive(leftMotorSpeed, -rightMotorSpeed);
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
	 * integral term and the previous error of the PID
	 * 
	 * @param enabled
	 *            whether to enable (true) or disable (false)
	 */
	public void setPIDEnabled(boolean enabled) {
		if (enabled) {
			leftPid.enable();
			rightPid.enable();
		} else {
			leftPid.reset();
			rightPid.reset();
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
	 * Gets the distance of the left encoder
	 * 
	 * @return the distance in meters
	 */
	public double getEncoderLeftDistance() {
		return leftEnc.getDistance() * RobotMap.MAX_SPEED;
	}

	/**
	 * Gets the distance of the right encoder
	 * 
	 * @return the distance in meters
	 */
	public double getEncoderRightDistance() {
		return -rightEnc.getDistance() * RobotMap.MAX_SPEED;
	}

	/**
	 * Gets the speed of the left encoder
	 * 
	 * @return the speed in meters per second
	 */
	public double getEncoderLeftSpeed() {
		return leftEnc.getRate() * RobotMap.MAX_SPEED;
	}

	/**
	 * Gets the speed of the right encoder
	 * 
	 * @return the speed in meters per second
	 */
	public double getEncoderRightSpeed() {
		return -rightEnc.getRate() * RobotMap.MAX_SPEED;
	}

	/**
	 * Gets the average distance of the encoders
	 * 
	 * @return the average distance in meters
	 */
	public double getEncoderAverageDistance() {
		return (getEncoderLeftDistance() + getEncoderRightDistance()) / 2f;
	}

	/**
	 * Gets the average speed of the encoders
	 * 
	 * @return the average speed in meters per second
	 */
	public double getEncoderAverageSpeed() {
		return (getEncoderRightSpeed() + getEncoderLeftSpeed()) / 2;
	}

	/**
	 * Sends data to the SmartDashboard
	 */
	@Override
	public void putSmartDashboardInfo() {
		SmartDashboard.putNumber("Encoders Distance Ave", getEncoderAverageDistance());
		SmartDashboard.putNumber("Encoders Speed Ave", getEncoderAverageSpeed());
		SmartDashboard.putData("Encoder Left", leftEnc);
		SmartDashboard.putData("Encoder Right", rightEnc);

		SmartDashboard.putData("PID Left", leftPid);
		SmartDashboard.putData("PID Right", rightPid);
	}

}
