package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.PID;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeArm extends Subsystem {
    
	private static IntakeArm singleton;

	CANTalon talon;
	AnalogPotentiometer pot;
	PID pid;
	
	private IntakeArm() {
		talon = new CANTalon(RobotMap.INTAKE_ARM_TALON);
		pot = new AnalogPotentiometer(RobotMap.INTAKE_ARM_POT);
		pot.setPIDSourceType(PIDSourceType.kDisplacement);
		pid = new PID(0.0001, 0, 0, pot, talon); //TODO: Get the value for the PID
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new IntakeArmNeutralCommand());
    }
    
    public static IntakeArm getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new IntakeArm();
		}
	}
	
	/**
	 * Set the PID setpoint
	 * @param value the value to set the setpoint to
	 */
	public void setSetpoint(double value) {
		
		pid.setSetpoint(value);	
	}
	
	public double getSetpoint() {
		return pid.getSetpoint();
	}
	
	/**
	 * Enable the pid
	 */
	public void enable() {
		pid.enable();
	}
	
	/**
	 * Disable the pid
	 */
	public void disable() {
		pid.disable();
	}
	
	/**
	 * Gets whether the pid is enabled or not
	 * @return whether the pid is enabled
	 */
	public boolean isEnable() {
		return pid.isEnable();
	}
}

