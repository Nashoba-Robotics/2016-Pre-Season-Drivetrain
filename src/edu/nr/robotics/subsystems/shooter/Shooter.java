package edu.nr.robotics.subsystems.shooter;

import edu.nr.lib.PID;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter extends Subsystem implements SmartDashboardSource{
    
	private static final double FULL_SPEED = 0; //TODO: Find the full speed of the shooter

	private static Shooter singleton;

	CANTalon talonOne, talonTwo;
	Encoder enc;
	PID pidOne, pidTwo;
	
	private Shooter() {
		talonOne = new CANTalon(RobotMap.INTAKE_ARM_TALON);
		talonTwo = new CANTalon(RobotMap.INTAKE_ARM_TALON);

		enc = new Encoder(RobotMap.SHOOTER_ENCODER_A, RobotMap.SHOOTER_ENCODER_B);
		enc.setPIDSourceType(PIDSourceType.kRate);
		pidOne = new PID(0.0001, 0, 0, enc, talonOne); //TODO: Get the value for the Shooter PID
		pidTwo = new PID(0.0001, 0, 0, enc, talonTwo); //TODO: Get the value for the Shooter PID

	}
	
    public void initDefaultCommand() {
    }
    
    public static Shooter getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new Shooter();
		}
	}
	
	/**
	 * Disable the PID and set the motor to the given speed
	 * @param speed the speed to set the motor to, from -1 to 1
	 */
	public void setMotor(double speed) {
		pidOne.disable();
		pidTwo.disable();
		talonOne.set(speed);
		talonTwo.set(speed);
	}
	
	/**
	 * Set the PID setpoint
	 * @param value the value to set the setpoint to
	 */
	public void setSetpoint(double value) {
		pidOne.setSetpoint(value);	
		pidTwo.setSetpoint(value);
	}
	
	/**
	 * Get the average PID setpoint
	 * @return the PID setpoint
	 */
	public double getAveSetpoint() {
		return (pidOne.getSetpoint() + pidTwo.getSetpoint())/2;
	}
	
	/**
	 * Enable the PID
	 */
	public void enable() {
		pidOne.enable();
		pidTwo.enable();
	}
	
	/**
	 * Disable the PID
	 */
	public void disable() {
		pidOne.disable();
		pidTwo.disable();
	}
	
	/**
	 * Gets whether the PIDs are enabled or not
	 * technically only checks one of the PIDs, but they should be the same
	 * @return whether the PIDs are enabled
	 */
	public boolean isEnable() {
		return pidOne.isEnable();
	}
	
	/**
	 * Gets the speed of the shooter
	 * @return the speed of the shooter
	 */
	public double getSpeed() {
		return enc.getRate();
	}
	
	public boolean getRunning() {
		return getSpeed() > 0.1;
	}
	
	public double getSpeedPercent() {
		return enc.getRate() / FULL_SPEED;
	}
	
	/**
	 * Tells if the shooter is up to speed
	 * @return
	 */
	public boolean getSped() {
		return getSpeed() > FULL_SPEED;
	}

	@Override
	public void putSmartDashboardInfo() {
		SmartDashboard.putNumber("Shooter Speed", getSpeed());
		SmartDashboard.putBoolean("Shooter Running", getAveSetpoint() != 0);
	}
}