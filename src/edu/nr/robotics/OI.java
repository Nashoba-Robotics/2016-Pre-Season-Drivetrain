package edu.nr.robotics;

import edu.nr.lib.CancelAllCommand;
import edu.nr.robotics.subsystems.drive.DriveConstantCommand;
import edu.nr.robotics.subsystems.drive.ResetEncodersCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	/** Used Buttons:
	 * Drive Left: (0)
	 * -> 1:  Cancel all commands
	 * 
	 * Drive Right: (1)
	 * -> 1:  Reverse drive direction
	 * -> 2:  Quick Turn
	 * -> 10: Reset encoders
	 * 
	 * Operator Left: (2)
	 * -> 1:  Cancel all commands
	 * -> 9:  Drive PID enable switch
	 * 
	 * Operator Right: (3)
	 * -> 1:  Drive Constant Command, left only, no PID, full throttle
	 */
	
	public SendableChooser drivingModeChooser;

	public double speedMultiplier = 1;

	private final double JOYSTICK_DEAD_ZONE = 0.15;

	public double gyroValueforPlayerStation = 0;

	private static OI singleton;

	Joystick driveLeft;
	Joystick driveRight;
	Joystick operatorLeft, operatorRight;

	JoystickButton fighter;

	private OI() {
		driveLeft = new Joystick(0);
		driveRight = new Joystick(1);

		operatorLeft = new Joystick(2);
		operatorRight = new Joystick(3);

		new JoystickButton(operatorRight, 1).whileHeld(new DriveConstantCommand(false, true, false,1));

		fighter = new JoystickButton(operatorLeft, 9);

		new JoystickButton(operatorLeft, 1).whenPressed(new CancelAllCommand());
		new JoystickButton(driveLeft, 1).whenPressed(new CancelAllCommand());
		new JoystickButton(driveRight, 10).whenPressed(new ResetEncodersCommand());

	}

	public static OI getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new OI();
		}
	}

	public double getArcadeMoveValue() {
		return -snapDriveJoysticks(driveLeft.getY());
	}

	public double getArcadeTurnValue() {
		return -snapDriveJoysticks(driveRight.getX());
	}

	// Reversing drive direction makes it easy to maneuver in reverse
	public boolean reverseDriveDirection() {
		return driveRight.getRawButton(1);
	}

	public double getTankLeftValue() {
		return -snapDriveJoysticks(driveLeft.getY());
	}

	public double getTankRightValue() {
		return snapDriveJoysticks(driveRight.getY());
	}

	private double snapDriveJoysticks(double value) {
		if (Math.abs(value) < JOYSTICK_DEAD_ZONE) {
			value = 0;
		} else if (value > 0) {
			value -= JOYSTICK_DEAD_ZONE;
		} else {
			value += JOYSTICK_DEAD_ZONE;
		}
		value /= 1 - JOYSTICK_DEAD_ZONE;

		return value;
	}

	public double getRawMove() {
		return -driveLeft.getY();
	}

	public double getRawTurn() {
		return -driveRight.getX();
	}

	public double getQuickTurn() {
		return driveRight.getRawButton(2) ? 1 : 0.5;
	}
}
