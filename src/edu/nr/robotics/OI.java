package edu.nr.robotics;

import edu.nr.lib.CancelAllCommand;
import edu.nr.lib.EmptyCommand;
import edu.nr.lib.path.OneDimensionalPath;
import edu.nr.robotics.subsystems.drive.Drive;
import edu.nr.robotics.subsystems.drive.DriveComplexDistanceCommand;
import edu.nr.robotics.subsystems.drive.ResetEncodersCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI 
{	
    public SendableChooser drivingModeChooser;
	
	public double speedMultiplier = 1;
	
	private final double JOYSTICK_DEAD_ZONE = 0.15;
	
	public double gyroValueforPlayerStation = 0;
	
	private static OI singleton;
	
	Joystick driveLeft;
	Joystick driveRight;
	Joystick operatorLeft, operatorRight;
	
	public static int H_DRIVE_BUTTON = 2;

	private OI()
	{
		driveLeft = new Joystick(0);
		driveRight = new Joystick(1);

		operatorLeft = new Joystick(2);
		operatorRight = new Joystick(3);
		
		
		new JoystickButton(operatorRight, 1).whenPressed(new DriveComplexDistanceCommand(new OneDimensionalPath(1),
				1/RobotMap.MAX_SPEED, //Kv
				0.5, //Ka
				0, //Kp
				0  //Kd
				)); //The old score button
		
		JoystickButton fighter = new JoystickButton(operatorLeft, 9);
		fighter.whenPressed(new EmptyCommand()
		    	{
					@Override
					protected void onExecute()
					{
						Drive.getInstance().setPIDEnabled(false);
					}
		    	});
		fighter.whenReleased(new EmptyCommand()
		    	{
					@Override
					protected void onExecute()
					{
						Drive.getInstance().setPIDEnabled(true);
					}
		    	});		

		//TODO: Find out the button for the operature CancelAllCommand
		
		new JoystickButton(driveLeft, 1).whenPressed(new CancelAllCommand());
		new JoystickButton(driveRight, 10).whenPressed(new ResetEncodersCommand());

	}
	
	public static OI getInstance()
	{
		init();
		return singleton;
	}
	
	public static void init()
	{
		if(singleton == null)
            singleton = new OI();
	}
	
	public double getArcadeMoveValue()
	{
		return -snapDriveJoysticks(driveLeft.getY());
	}
	
	public double getArcadeTurnValue()
	{
		return -snapDriveJoysticks(driveRight.getX());
	}
	
	public double getHDriveValue()
	{
		if(driveRight.getRawButton(2))
		{
			return -driveRight.getX();
		}
		else
		{
			return snapCoffinJoysticks(-operatorLeft.getRawAxis(0));
		}
	}
	
	//Reversing drive direction makes it easy to maneuver in reverse
	public boolean reverseDriveDirection()
	{
		return driveRight.getRawButton(1);
	}
	
	public double getTankLeftValue()
	{
		return -snapDriveJoysticks(driveLeft.getY());
	}

	public double getTankRightValue()
	{
		return snapDriveJoysticks(driveRight.getY());
	}
	
	private double snapDriveJoysticks(double value)
	{
		if(Math.abs(value) < JOYSTICK_DEAD_ZONE)
    	{
			value = 0;
    	}
    	else if(value > 0)
    	{
    		value -= JOYSTICK_DEAD_ZONE;
    	}
    	else
    	{
    		value += JOYSTICK_DEAD_ZONE;
    	}
		value /=  (1 - JOYSTICK_DEAD_ZONE);
		
		return value;
	}
	
	private double snapCoffinJoysticks(double value)
	{
		if(Math.abs(value) < 0.1)
			return 0;
		
		return (value-0.1) / 0.9;
	}
	
	/**
	 * @return true if the DriveJoystickCommand should ignore joystick Z value and use the gyro to drive straight instead.
	 */
	public boolean useGyroCorrection()
	{
		return driveRight.getRawButton(H_DRIVE_BUTTON);
	}
	
	public double getRawMove()
	{
		return -driveLeft.getY();
	}
	
	public double getRawTurn()
	{
		return -driveRight.getX();
	}
}

