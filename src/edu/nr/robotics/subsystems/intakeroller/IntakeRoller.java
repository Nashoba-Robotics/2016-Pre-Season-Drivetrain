package edu.nr.robotics.subsystems.intakeroller;

import edu.nr.lib.interfaces.SmartDashboardSource;
import edu.nr.robotics.LiveWindowClasses;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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
		
		LiveWindow.addSensor("Intake Roller", "Speed", LiveWindowClasses.intakeRollerSpeed);

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

    @Override
	public void initDefaultCommand() {
    	setDefaultCommand(new IntakeRollerNeutralCommand());
    }

	@Override
	public void smartDashboardInfo() {
		LiveWindowClasses.intakeRollerSpeed.set(talon.get());
		SmartDashboard.putBoolean("Intake Roller Forward", isForward());
		SmartDashboard.putBoolean("Intake Roller Reverse", isReverse());
	}
	
	public boolean hasBall() {
		return !gate.get();
	}

	public double getRollerSpeed() {
		return talon.get();
	}

	public boolean isForward() {
		return talon.get() < -0.1 ;
	}
	
	public boolean isReverse() {
		return talon.get() > 0.1 ;
	}
}

