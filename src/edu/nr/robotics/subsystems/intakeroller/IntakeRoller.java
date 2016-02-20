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
	TalonEncoder encoder;
	PID pid;
	
	DigitalInput gate;

	private IntakeRoller() {
		gate = new DigitalInput(RobotMap.INTAKE_PHOTO_GATE);
		talon = new CANTalon(RobotMap.INTAKE_ROLLER_TALON);
		encoder = new TalonEncoder(talon);
		encoder.setPIDSourceType(PIDSourceType.kRate);
		pid = new PID(0, 0, 0, encoder, talon); //TODO: Get the PID value for the intake roller
		pid.enable();
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
	public void setRollerSetpoint(double value) {
		pid.setSetpoint(value);	
	}
	
	public boolean getRollerRunning() {
		return encoder.get() > 0.1;
	}

    public void initDefaultCommand() {
    }

	@Override
	public void smartDashboardInfo() {
		SmartDashboard.putNumber("Intake Roller Speed", encoder.getRate());		
	}
	
	public boolean hasBall() {
		return !gate.get();
	}
}

