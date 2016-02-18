package edu.nr.robotics.subsystems.hood;

import edu.nr.lib.PID;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Hood extends Subsystem implements SmartDashboardSource {
    
	CANTalon talon;	
	AnalogPotentiometer pot;
	PID pid;
	
	private static Hood singleton;
	
	private Hood() {
		talon = new CANTalon(RobotMap.HOOD_TALON);
		pot = new AnalogPotentiometer(RobotMap.HOOD_POT);
		pot.setPIDSourceType(PIDSourceType.kDisplacement);
		pid = new PID(0.0001, 0, 0, pot, talon); //TODO: Get the value for the Hood PID
	}

	@Override
	protected void initDefaultCommand() {
	}
	
	public static Hood getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new Hood();
		}
	}
	
	/**
	 * Disable the PID and set the motor to the given speed
	 * @param speed the speed to set the motor to, from -1 to 1
	 */
	public void setMotor(double speed) {
		pid.disable();
		talon.set(speed);
	}
	
	/**
	 * Set the PID setpoint
	 * @param value the value to set the setpoint to
	 */
	public void setSetpoint(double value) {
		pid.setSetpoint(value);	
	}
	
	/**
	 * Get the PID setpoint
	 * @return the PID setpoint
	 */
	public double getSetpoint() {
		return pid.getSetpoint();
	}
	
	/**
	 * Enable the PID
	 */
	public void enable() {
		pid.enable();
	}
	
	/**
	 * Disable the PID
	 */
	public void disable() {
		pid.disable();
	}
	
	/**
	 * Gets whether the PID is enabled or not
	 * @return whether the PID is enabled
	 */
	public boolean isEnable() {
		return pid.isEnable();
	}
	
	/**
	 * Gets the value of the potentiometer
	 * @return the value of the potentiometer
	 */
	public double get() {
		return pot.get();
	}
	
	/**
	 * Gets whether the motor is still moving
	 * @return whether the motor is still moving
	 */
	public boolean getMoving() {
		return Math.abs(pid.getError()) > 0.05;
		//0.05 is a number I just made up
	}
	
	public static double distanceToAngle(double distance) {
		return distance;
		//TODO: Find the distance to angle function
	}

	@Override
	public void smartDashboardInfo() {
		SmartDashboard.putNumber("Hood Potentiometer", get());
		SmartDashboard.putBoolean("Hood Moving", getMoving());
	}
}

