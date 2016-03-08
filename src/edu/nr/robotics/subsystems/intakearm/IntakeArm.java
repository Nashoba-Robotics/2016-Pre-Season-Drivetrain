package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.PID;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.lib.interfaces.Periodic;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
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
	private AnalogPotentiometer pot;
	PID pid;
	
	boolean pidDisabled = false;
		
	private IntakeArm() {		

		talon = new CANTalon(RobotMap.INTAKE_ARM_TALON);
		talon.setInverted(true);
		pot = new AnalogPotentiometer(RobotMap.INTAKE_ARM_POT);
		pot.setPIDSourceType(PIDSourceType.kDisplacement);
		pid = new PID(421.8*0.015, 421.8*0.0002, 0.00, pot, talon);
	}
	
    public void initDefaultCommand() {
		setDefaultCommand(new IntakeArmJoystickCommand());

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
		if(pid.isEnable())
			pid.disable();
		talon.set(speed);
	}
	
	/**
	 * Set the arm PID setpoint
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
		pidDisabled = false;
		pid.enable();
	}
	
	/**
	 * Disable the PID
	 */
	public void disable() {
		pidDisabled = true;
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
		SmartDashboard.putBoolean("Intake Arm Moving", Math.abs(pid.getError()) > 0.02);
		SmartDashboard.putData(this);
		SmartDashboard.putBoolean("Intake Arm bot limit switch", isBotLimitSwitchClosed());
		SmartDashboard.putBoolean("Intake Arm top limit switch", isTopLimitSwitchClosed());
		SmartDashboard.putData("Intake Arm PID", pid);
		SmartDashboard.putNumber("Intake arm pid output", pid.get());
		
	}

	public boolean isTopLimitSwitchClosed() {
		return talon.isFwdLimitSwitchClosed();
	}
	
	public boolean isBotLimitSwitchClosed() {
		return talon.isRevLimitSwitchClosed();
	}

	public void setMaxSpeed(double maxSpeed) {
		pid.setOutputRange(-maxSpeed, maxSpeed);
	}

	@Override
	public void periodic() {
		if(pid.getError() < 0.005) {
			pid.disable();
		}
	}
}

