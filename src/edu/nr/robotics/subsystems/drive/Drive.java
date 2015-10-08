package edu.nr.robotics.subsystems.drive;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {

	private static Drive singleton;
	
	private Drive() {
		
	}
	
	public static Drive getInstance()
    {
		init();
        return singleton;
    }
	
	public static void init()
	{
		if(singleton == null)
		{
			singleton = new Drive();
		}
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	public void putSmartDashboardInfo() {
		//TODO: Push smart dashboard info (eg. NavX data, encoder data)
	}
}

