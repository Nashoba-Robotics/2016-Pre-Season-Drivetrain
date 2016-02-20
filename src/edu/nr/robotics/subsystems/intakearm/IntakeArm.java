package edu.nr.robotics.subsystems.intakearm;

import edu.nr.ResettableAnalogPotentiometer;
import edu.nr.lib.PID;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.lib.interfaces.Periodic;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class IntakeArm extends Subsystem implements SmartDashboardSource, Periodic {
    
	private static IntakeArm singleton;

	CANTalon talon;
	ResettableAnalogPotentiometer pot;
	PID pid;
		
	private IntakeArm() {		

		talon = new CANTalon(RobotMap.INTAKE_ARM_TALON);
		pot = new ResettableAnalogPotentiometer(RobotMap.INTAKE_ARM_POT);
		pot.setPIDSourceType(PIDSourceType.kDisplacement);
		pid = new PID(0.0001, 0, 0, pot, talon); //TODO: Get the value for the Intake Arm PID
	}
	
    public void initDefaultCommand() {
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
	 * Disable the PID and set the motor to the given speed
	 * @param speed the speed to set the motor to, from -1 to 1
	 */
	public void setMotor(double speed) {
		pid.disable();
		talon.set(speed);
	}
	
	/**
	 * Set the arm PID setpoint
	 * @param value the value to set the setpoint to
	 */
	public void setArmSetpoint(double value) {
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

	@Override
	public void smartDashboardInfo() {
		SmartDashboard.putNumber("Intake Arm Potentiometer", get());
		SmartDashboard.putBoolean("Intake Arm Moving", Math.abs(pid.getError()) > 0.05);
	}

	@Override
	public void periodic() {
		if(talon.isRevLimitSwitchClosed()) {
			pot.reset();
		}
	}

	public void reset() {
		pot.reset();
	}
}

