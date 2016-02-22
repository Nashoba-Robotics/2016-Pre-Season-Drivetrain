package edu.nr.robotics;

import edu.nr.lib.AngleUnit;
import edu.nr.lib.CancelAllCommand;
import edu.nr.lib.DoubleJoystickButton;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.lib.interfaces.Periodic;
import edu.nr.robotics.commandgroups.*;
import edu.nr.robotics.subsystems.climb.*;
import edu.nr.robotics.subsystems.drive.*;
import edu.nr.robotics.subsystems.hood.Hood;
import edu.nr.robotics.subsystems.hood.HoodIncreaseDegreeCommand;
import edu.nr.robotics.subsystems.hood.HoodResetEncoderCommand;
import edu.nr.robotics.subsystems.intakearm.*;
import edu.nr.robotics.subsystems.intakeroller.*;
import edu.nr.robotics.subsystems.lights.*;
import edu.nr.robotics.subsystems.loaderroller.*;
import edu.nr.robotics.subsystems.shooter.ShooterHighCommand;
import edu.nr.robotics.subsystems.shooter.ShooterOffCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI implements SmartDashboardSource, Periodic {
	
	public SendableChooser drivingModeChooser;

	public double speedMultiplier = 1;

	private final double JOYSTICK_DEAD_ZONE = 0.15;

	public double gyroValueforPlayerStation = 0;

	private static OI singleton;

	Joystick driveLeft;
	Joystick driveRight;
	Joystick operatorLeft, operatorRight;

	JoystickButton dumbDrive;
	JoystickButton LEDCutout;
	
	public JoystickButton fireButton;
	public DoubleJoystickButton alignButton;
	
	AlignCommandGroup alignCommand;

	/**
	 * 
	 */
	private OI() {
		SmartDashboard.putNumber("Speed Multiplier", speedMultiplier);

		initDriveLeft();
		initDriveRight();
		initOperatorLeft();
		initOperatorRight();
	}
	
	public void initDriveLeft() {
		//Drive Left: (0)
		driveLeft = new Joystick(0);
		//->  1: Cancel all commands
		new JoystickButton(driveLeft, 1).whenPressed(new CancelAllCommand());
		//->  2: Reverse drive direction
		//->  3: Lights on
		new JoystickButton(driveLeft, 3).whenPressed(new LightsOnCommand());
		//->  4: Lights off
		new JoystickButton(driveLeft, 4).whenPressed(new LightsOffCommand());
		//->  5: Lights blink
		new JoystickButton(driveLeft, 5).whenPressed(new LightsBlinkCommand(200));

		new JoystickButton(driveLeft, 10).whenPressed(new ShooterHighCommand());
		new JoystickButton(driveLeft, 9).whenPressed(new ShooterOffCommand());

		new JoystickButton(driveLeft, 8).whenPressed(new HoodIncreaseDegreeCommand(2));

		new JoystickButton(driveLeft, 7).whenPressed(new DriveAnglePIDCommand(180, AngleUnit.DEGREE));
	}
	
	public void initDriveRight() {
		// Drive Right: (1)
		driveRight = new Joystick(1);
		// => 1: Slow Turn
		// -> 10: Reset drive encoders
		new JoystickButton(driveRight, 10).whenPressed(new DriveResetEncodersCommand());
		// => 11: Reset hood encoder
		new JoystickButton(driveRight, 11).whenPressed(new HoodResetEncoderCommand());
		// -> 12: Reset intake potentiomer
		new JoystickButton(driveRight, 9).whenPressed(new IntakeArmResetPotentiometerCommand());
	}

	public void initOperatorLeft() {
		// Operator Left: (3)
		operatorLeft = new Joystick(3);
		// -> 4: Auto Shovel of Fries
		// Auto shovel of fries routine, ends when drive joysticks are touched
		new JoystickButton(operatorLeft, 4).whenPressed(new AutoShovelOfFriesCommandGroup());
		// -> 2: Auto Guillotine
		// Auto guillotine routine, ends when drive joysticks are touched
		new JoystickButton(operatorLeft, 6).whenPressed(new AutoGuillotineCommandGroup());

		// -> 2 + 3: Align
		// Auto align the robot to target, ends when drive joysticks are touched
		alignButton = new DoubleJoystickButton(operatorLeft, 2, 3);
		alignCommand = new AlignCommandGroup();
		alignButton.whileHeld(alignCommand);
		// => 9: Get low
		// Puts robot in position to go under low bar (hood down, intake to
		// appropriate height)
		new JoystickButton(operatorLeft, 9).whenPressed(new GetLowCommandGroup());
		// => 11: Prepare Long Shot
		// Prepares long shot (shooter wheels to speed, hood up to approximate
		// angle, drops intake to position where it does not block shot, turns
		// on lights)
		new JoystickButton(operatorLeft, 11).whenPressed(new PrepareLongShotCommandGroup());
		// => 10: Prepare Close Shot
		// Prepares close shot (shooter wheels to speed, hood up to approximate
		// angle, drops intake to position where it does not block shot, turns
		// on lights)
		new JoystickButton(operatorLeft, 10).whenPressed(new PrepareCloseShotCommandGroup());
		// => 5: Prepare Low Goal
		// Prepares low goal dump (positions intake to proper height)
		new JoystickButton(operatorLeft, 5).whenPressed(new IntakeArmPrepareLowGoalCommand());
		// => 8: Low Goal
		// Double checks intake height, reverses intake and loader to spit ball
		// into low goal.
		new JoystickButton(operatorLeft, 8).whenPressed(new LowGoalCommandGroup());
		// -> 12: Puke
		// Reverses all ball handling systems (shooter, loader, intake) (SHOOTER
		// RAMPING REQUIRED)
		new JoystickButton(operatorLeft, 12).whenPressed(new LoaderRollerNeutralCommand());// .whenPressed(new
																							// PukeCommandGroup());
		// => 1: Laser Cannon Trigger (Shoot)
		// Forces intake on to shoot (loader auto off based on photo sensor 3,
		// turns off lights)
		fireButton = new JoystickButton(operatorLeft, 1);
		fireButton.whenPressed(new LaserCannonTriggerCommand());
	}

	public void initOperatorRight() {
		// Operator Right: (2)
		operatorRight = new Joystick(2);
		// => 1: Up Height (Climb Height)
		// Positions intake arm to vertical height, ensures intake off (also
		// used for climb)
		new JoystickButton(operatorRight, 4).whenPressed(new IntakeArmUpHeightCommandGroup());
		// => 2: Intake Height
		// Positions intake arm to collecting height turns on intake
		new JoystickButton(operatorRight, 3).whenPressed(new IntakeArmIntakeHeightCommandGroup());
		// => 3: Bumper Height (Home)
		// Positions intake arm to home height (such that it will contact the
		// bumper of another robot), ensures intake off
		new JoystickButton(operatorRight, 2).whenPressed(new IntakeArmHomeHeightCommandGroup());
		// => 4: Bottom Height
		// Positions intake arm to bottom height, ensures intake off
		new JoystickButton(operatorRight, 1).whenPressed(new IntakeArmBottomHeightCommandGroup());
		// -> 5: Intake On
		// Overrides intake rollers
		new JoystickButton(operatorRight, 5).whenPressed(new IntakeRollerSwapCommand());
		// => 7: Cancel all commands
		new JoystickButton(operatorRight, 7).whenPressed(new CancelAllCommand());
		// -> 8: Climb
		// Fully retracts elevator, stops after 1 second of motor stall
		new JoystickButton(operatorRight, 9).whenPressed(new ElevatorRetractCommand());
		// -> 9: Extend & Intake Up
		// Extends elevator completely, brings intake to up position
		new JoystickButton(operatorRight, 8).whenPressed(new ClimbExtendCommand());
		// -> 10: Prepare Climb
		// Un-latches elevator (drives the elevator down a little)
		new JoystickButton(operatorRight, 7).whenPressed(new ElevatorUnlatchCommand());
		// => 11: Dumb Drive switch
		// Switch closed loop drive off (in case of sensor failure)
		dumbDrive = new JoystickButton(operatorRight, 11);
		// -> 12: Brake Light Cutout Switch
		// Disables robot “shot ready” LED sequences (in the event that
		// signifying we are about to shoot enables defense robots to defend
		// more effectively
		LEDCutout = new JoystickButton(operatorRight, 12);
		// -> Joy1: Arm Position Joystick
		// Overrides intake arm position (overrides pot, not limit switches)
		// snapCoffinJoysticks(operatorRight.getRawAxis(0))

		// -> Joy2: Loader Joystick
		// Overrides loader motor power
		// snapCoffinJoysticks(operatorRight.getRawAxis(1))

		// -> Joy3: Hood Joystick
		// Overrides hood angle (undone if another auto hood angle command is
		// sent)
		// snapCoffinJoysticks(operatorRight.getRawAxis(2))

		// -> Joy4: Elevator Joystick
		// Overrides elevator (limit switches still operate)
		// snapCoffinJoysticks(operatorRight.getRawAxis(3))
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
	
	public double getIntakeArmMoveValue() {
		return -1 * (new JoystickButton(driveRight, 6).get() ? driveRight.getAxis(AxisType.kY) : 0);
		//return snapCoffinJoysticks(operatorRight.getRawAxis(0));
	}
	
	public double getLoaderRollerMoveValue() {
		return -1 * (new JoystickButton(driveRight, 7).get() ? driveRight.getAxis(AxisType.kY) : 0);
		//return snapCoffinJoysticks(operatorRight.getRawAxis(1));
	}
	
	public double getIntakeRollerMoveValue() {
		return new JoystickButton(driveRight, 8).get() ? driveRight.getAxis(AxisType.kY) : 0;
		//return snapCoffinJoysticks(operatorRight.getRawAxis(1));
	}
	
	public double getHoodMoveValue() {
		return -1 * (new JoystickButton(driveRight, 10).get() ? driveRight.getAxis(AxisType.kY) : 0);
		//return snapCoffinJoysticks(operatorRight.getRawAxis(2));
	}
	
	public double getElevatorMoveValue() {
		return new JoystickButton(driveRight, 11).get() ? driveRight.getAxis(AxisType.kY) : 0;
		//return snapCoffinJoysticks(operatorRight.getRawAxis(3));
	}
	
	public double getShooterMoveValue() {
		return -1 * (new JoystickButton(driveRight, 9).get() ? driveRight.getAxis(AxisType.kY) : 0);
		//return snapCoffinJoysticks(operatorRight.getRawAxis(0));
	}

	public double getArcadeMoveValue() {
		return snapDriveJoysticks(driveLeft.getY()) * (driveLeft.getRawButton(2) ? 1 : -1);
	}

	public double getArcadeTurnValue() {
		if(new JoystickButton(driveRight, 6).get() || new JoystickButton(driveRight, 7).get() || new JoystickButton(driveRight, 10).get() || new JoystickButton(driveRight, 11).get() || new JoystickButton(driveRight, 8).get()  || new JoystickButton(driveRight, 9).get())
			return 0;

		return -snapDriveJoysticks(driveRight.getX());
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
	
	private double snapCoffinJoysticks(double value)
	{
		if(value > -0.1 && value < 0.1)
			return 0;
		
		return (value-0.1) / 0.9;
	}

	public double getRawMove() {
		return -driveLeft.getY();
	}

	public double getRawTurn() {
		return -driveRight.getX();
	}

	public double getTurnAdjust() {
		return driveRight.getRawButton(1) ? 0.5 : 1;
	}

	@Override
	public void smartDashboardInfo() {
		speedMultiplier = SmartDashboard.getNumber("Speed Multiplier");
		SmartDashboard.putBoolean("Dumb Drive", dumbDrive.get());
	}
	
	public boolean isTankNonZero() {
		return getTankLeftValue() != 0 || getTankRightValue() != 0;
	}
	
	public boolean isArcadeNonZero() {
		return getArcadeMoveValue() != 0 || getArcadeTurnValue() != 0;
	}

	public boolean isCurrentModeNonZero() throws DrivingModeException {
		if(drivingModeChooser.getSelected() == DrivingMode.ARCADE) {
			return isArcadeNonZero();
		} else if(drivingModeChooser.getSelected() == DrivingMode.TANK) {
			return isTankNonZero();
		} else {
			throw new DrivingModeException((DrivingMode) drivingModeChooser.getSelected());
		}
	}
	
	public boolean getBrakeLightCutout() {
		return LEDCutout.get();
	}
		
	@Override
	public void periodic() {
		//TODO: Test joystick cancel functionality
		try {
			if(isCurrentModeNonZero()) {
				if(Drive.getInstance().getCurrentCommand() != null && !Drive.getInstance().getCurrentCommand().getName().equals("DriveJoystickCommand")) {
					Drive.getInstance().getCurrentCommand().cancel();
				}
			}
		} catch (DrivingModeException e) {
			System.out.println("Driving Mode " + e.getMode() + " is not supported by OI drive subsystem cancel");
		}
		
		if(getLoaderRollerMoveValue() != 0) {
			if(LoaderRoller.getInstance().getCurrentCommand() != null && !LoaderRoller.getInstance().getCurrentCommand().getName().equals("LoaderRollerJoystickCommand")) {
				LoaderRoller.getInstance().setLoaderSpeed(0);
			}
		}
		
		if(getIntakeArmMoveValue() != 0) {
			if(IntakeArm.getInstance().getCurrentCommand() != null && !IntakeArm.getInstance().getCurrentCommand().getName().equals("IntakeArmJoystickCommand")) {
				IntakeArm.getInstance().disable();
			}
		}
		
		if(getHoodMoveValue() != 0) {
			if(Hood.getInstance().getCurrentCommand() != null && !Hood.getInstance().getCurrentCommand().getName().equals("HoodJoystickCommand")) {
				Hood.getInstance().disable();
			}
		}
		
		if(getElevatorMoveValue() != 0) {
			if(Elevator.getInstance().getCurrentCommand() != null && !Elevator.getInstance().getCurrentCommand().getName().equals("ElevatorJoystickCommand")) {
				Elevator.getInstance().setMotorValue(0);
			}
		}
	}

	public boolean isAutoAlignRunning() {
		return alignCommand.isRunning();
	}
}
