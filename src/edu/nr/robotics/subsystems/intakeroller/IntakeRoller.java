package edu.nr.robotics.subsystems.intakeroller;

import edu.nr.lib.PID;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.lib.TalonEncoder;
import edu.nr.robotics.RobotMap;
import edu.nr.robotics.subsystems.intakearm.IntakeArm;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class IntakeRoller extends Subsystem implements SmartDashboardSource {
    
	private static IntakeRoller singleton;
	
	CANTalon talon;
	DigitalInput gate;

	private IntakeRoller() {
		gate = new DigitalInput(RobotMap.INTAKE_PHOTO_GATE);
		talon = new CANTalon(RobotMap.INTAKE_ROLLER_TALON);
	}
	
	public static IntakeRoller getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new IntakeRoller();
		}
	}
	
	/**
	 * Set the roller PID setpoint
	 * @param value the value to set the setpoint to
	 */
	public void setRollerSpeed(double value) {
		talon.set(value);	
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new IntakeRollerJoystickCommand());
    }

	@Override
	public void smartDashboardInfo() {
		SmartDashboard.putNumber("Intake Roller Speed", talon.get());	
		SmartDashboard.putData(this);
	}
	
	public boolean hasBall() {
		return !gate.get();
	}
}

