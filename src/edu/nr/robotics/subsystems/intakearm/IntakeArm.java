package edu.nr.robotics.subsystems.intakearm;

import edu.nr.lib.PID;
import edu.nr.lib.interfaces.Periodic;
import edu.nr.lib.interfaces.SmartDashboardSource;
import edu.nr.robotics.LiveWindowClasses;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class IntakeArm extends Subsystem implements SmartDashboardSource, Periodic {
    
	private static IntakeArm singleton;
	
	int counter = 0;

	CANTalon talon;
	private AnalogPotentiometer pot;
	PID pid;
	
	boolean pidDisabled = false;
		
	private IntakeArm() {		

		talon = new CANTalon(RobotMap.INTAKE_ARM_TALON);
		talon.setInverted(true);
		pot = new AnalogPotentiometer(RobotMap.INTAKE_ARM_POT);
		pot.setPIDSourceType(PIDSourceType.kDisplacement);
		pid = new PID(421.8*0.1, 421.8*0.0001, 0.00, pot, talon);
		
		LiveWindow.addSensor("Intake Arm", "PID", pid);
		
		LiveWindow.addSensor("Intake Arm", "Bottom Switch", LiveWindowClasses.intakeArmBottomSwitch);
		LiveWindow.addSensor("Intake Arm", "Top Switch", LiveWindowClasses.intakeArmTopSwitch);

	}
	
    @Override
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
		SmartDashboard.putNumber("Intake Arm Error", pid.getError());
		SmartDashboard.putBoolean("Intake Arm Moving", pid.isEnable());
		
		LiveWindowClasses.intakeArmBottomSwitch.set(isBotLimitSwitchClosed());
		LiveWindowClasses.intakeArmTopSwitch.set(isTopLimitSwitchClosed());
	}

	public boolean isTopLimitSwitchClosed() {
		return talon.isFwdLimitSwitchClosed();
	}
	
	public boolean isBotLimitSwitchClosed() {
		return talon.isRevLimitSwitchClosed();
	}

	public void setMaxSpeed(double maxSpeed) {
		pid.setOutputRange(0, maxSpeed);
	}

	@Override
	public void periodic() {
		if(Math.abs(pid.getError()) < RobotMap.INTAKE_ARM_THRESHOLD * 2/3) {
			counter++;
			if(counter > 2)
				pid.disable();
		} else {
			counter = 0;
		}
	} 

	public double getError() {
		return pid.getError();
	}
}

