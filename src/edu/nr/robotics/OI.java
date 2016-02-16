package edu.nr.robotics;

import edu.nr.lib.CancelAllCommand;
import edu.nr.lib.Periodic;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.robotics.commandgroups.*;
import edu.nr.robotics.subsystems.drive.*;
import edu.nr.robotics.subsystems.elevator.*;
import edu.nr.robotics.subsystems.intakearm.*;
import edu.nr.robotics.subsystems.loaderroller.*;
import edu.wpi.first.wpilibj.Joystick;
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
	JoystickButton brakeLightCutout;

	/**
	 * 
	 */
	private OI() {
		SmartDashboard.putNumber("Speed Multiplier", speedMultiplier);
		
		  //Drive Left: (0)
		driveLeft = new Joystick(0);
		  //->  1: Cancel all commands
		new JoystickButton(driveLeft, 1).whenPressed(new CancelAllCommand());
          //->  2: Reverse drive direction

        //Drive Right: (1)
		driveRight = new Joystick(1);
		  //->  1: Slow Turn
		  //-> 10: Reset encoders
		new JoystickButton(driveRight, 10).whenPressed(new ResetEncodersCommand());

		  //Operator Left: (2)
		operatorLeft = new Joystick(2);
		  //->  1: Cancel all commands
		new JoystickButton(operatorLeft, 1).whenPressed(new CancelAllCommand());
		  //x>  2: Auto Guillotine
		  //           Auto guillotine routine, ends when drive joysticks are touched
		new JoystickButton(operatorLeft, 2).whenPressed(new AutoGuillotineCommandGroup());
		  //x>  3: Auto Shovel of Fries
		  //           Auto shovel of fries routine, ends when drive joysticks are touched
		new JoystickButton(operatorLeft, 3).whenPressed(new AutoShovelOfFriesCommandGroup());
		  //x>  4: Align
		  //           Auto align the robot to target, ends when drive joysticks are touched
		new JoystickButton(operatorLeft, 4).whenPressed(new AlignCommand());
		  //x>  5: Brake Light Cutout Switch
		  //           Disables robot “shot ready” LED sequences (in the event that signifying we are about to shoot enables defense robots to defend more effectively
		brakeLightCutout = new JoystickButton(operatorLeft, 5);
		  //x>  6: Get low
		  //           Puts robot in position to go under low bar (hood down, intake to appropriate height)
		new JoystickButton(operatorLeft, 6).whenPressed(new GetLowCommandGroup());
		  //x>  7: Prepare Long Shot
		  //           Prepares long shot (shooter wheels to speed, hood up to approximate angle, drops intake to position where it does not block shot)
		new JoystickButton(operatorLeft, 7).whenPressed(new PrepareLongShotCommandGroup());
		  //x>  8: Prepare Close Shot
		  //           Prepares close shot (shooter wheels to speed, hood up to approximate angle, drops intake to position where it does not block shot)
		new JoystickButton(operatorLeft, 8).whenPressed(new PrepareCloseShotCommandGroup());
		  //->  9: Dumb Drive switch
		  //           Switch closed loop drive off (in case of sensor failure)
		dumbDrive = new JoystickButton(operatorLeft, 9);
		  //x> 10: Prepare Low Goal
		  //           Prepares low goal dump (positions intake to proper height)
		new JoystickButton(operatorLeft, 10).whenPressed(new PrepareLowGoalCommand());
		  //x> 11: Low Goal
		  //           Double checks intake height, reverses intake and loader to spit ball into low goal.
		new JoystickButton(operatorLeft, 11).whenPressed(new LowGoalCommandGroup());
		  //x> 12: Puke
		  //           Reverses all ball handling systems (shooter, loader, intake) (SHOOTER RAMPING REQUIRED)
		new JoystickButton(operatorLeft, 12).whenPressed(new PukeCommandGroup());

		  //Operator Right: (3)
		operatorRight = new Joystick(3);
		  //x>  1: Up Height (Climb Height)
		  //           Positions intake arm to vertical height, ensures intake off (also used for climb)
		new JoystickButton(operatorRight, 1).whenPressed(new UpHeightCommand());
		  //x>  2: Intake Height
		  //           Positions intake arm to collecting height turns on intake
		new JoystickButton(operatorRight, 2).whenPressed(new IntakeHeightCommand());
		  //x>  3: Bumper Height (Home)
		  //           Positions intake arm to home height (such that it will contact the bumper of another robot), ensures intake off
		new JoystickButton(operatorRight, 3).whenPressed(new BumperHeightCommand());
		  //x>  4: Bottom Height
		  //           Positions intake arm to bottom height, ensures intake off
		new JoystickButton(operatorRight, 4).whenPressed(new BottomHeightCommand());
		  //x>  5: Intake On
		  //           Overrides intake rollers
		new JoystickButton(operatorRight, 5).whenPressed(new IntakeRollerSpeedCommand(1));
		  //x>  6: Laser Cannon Trigger (Shoot)
		  //           Forces intake on to shoot (loader auto off based on photo sensor 3)
		new JoystickButton(operatorRight, 6).whenPressed(new LaserCannonTriggerCommand());
		  //x>  7: Prepare Climb
		  //           Un-latches elevator
		new JoystickButton(operatorRight, 7).whenPressed(new PrepareClimbCommand());
		  //x>  8: Extend & Intake Up
		  //           Extends elevator completely, brings intake to up position
		new JoystickButton(operatorRight, 8).whenPressed(new ExtendAndIntakeUpCommandGroup());
		  //x>  9: Climb
		  //           Fully retracts elevator, stops after 1 second of motor stall
		new JoystickButton(operatorRight, 9).whenPressed(new ClimbCommand());
		  //-> 10: Cancel all commands
		new JoystickButton(operatorRight, 10).whenPressed(new CancelAllCommand());
		  //x> Joy1: Arm Position Joystick
		  //           Overrides intake arm position (overrides pot, not limit switches)
		  //x> Joy2: Loader Joystick
		  //           Overrides loader motor power
		  //x> Joy3: Hood Joystick
		  //           Overrides hood angle (undone if another auto hood angle command is sent)
		  //x> Joy4: Elevator Joystick
		  //           Overrides elevator (limit switches still operate)
		

		

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
		return snapDriveJoysticks(driveLeft.getY()) * (driveLeft.getRawButton(2) ? -1 : 1);
	}

	public double getArcadeTurnValue() {
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
	}
	
	public boolean isTankNonZero() {
		return getTankLeftValue() != 0 || getTankRightValue() != 0;
	}
	
	public boolean isArcadeNonZero() {
		return getArcadeMoveValue() != 0 || getArcadeTurnValue() != 0;
	}

	public boolean isCurrentModeNonZero() throws DrivingModeException {
		if(drivingModeChooser.getSelected().equals("arcade")) {
			return isArcadeNonZero();
		} else if(drivingModeChooser.getSelected().equals("tank")) {
			return isTankNonZero();
		} else {
			throw new DrivingModeException((DrivingMode) drivingModeChooser.getSelected());
		}
	}
		
	@Override
	public void periodic() {
		try {
			if(isCurrentModeNonZero()) {
				if(Drive.getInstance().getCurrentCommand().getName() != "DriveJoystickCommand") {
					Drive.getInstance().getCurrentCommand().cancel();
					//TODO: Test this cancel functionality
				}
			}
		} catch (DrivingModeException e) {
			System.out.println("Driving Mode " + e.getMode().toString() + " is not supported by OI drive subsystem cancel");
		}
	}
}
