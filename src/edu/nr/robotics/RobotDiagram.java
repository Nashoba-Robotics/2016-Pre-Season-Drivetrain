package edu.nr.robotics;

import edu.nr.lib.network.AndroidConnection;
import edu.nr.robotics.commandgroups.AlignCommandGroup;
import edu.nr.robotics.subsystems.climb.Elevator;
import edu.nr.robotics.subsystems.hood.Hood;
import edu.nr.robotics.subsystems.intakearm.IntakeArm;
import edu.nr.robotics.subsystems.intakeroller.IntakeRoller;
import edu.nr.robotics.subsystems.loaderroller.LoaderRoller;
import edu.nr.robotics.subsystems.shooter.Shooter;
import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.tables.ITable;

public class RobotDiagram implements NamedSendable {

	public RobotDiagram() {
	}

	private ITable table;

	@Override
	public void initTable(ITable table) {
		this.table = table;
		if (table != null) {
			

			table.putString("~TYPE~", "robo-diagram");

			table.putBoolean("Auto Align Happening", Robot.getInstance().state == AlignCommandGroup.State.ALIGNING);
			table.putBoolean("All Systems Go", Shooter.getInstance().getSped());
			
			//Hood
			table.putBoolean("Hood Bottom", Hood.getInstance().isAtPosition(Hood.Position.BOTTOM));
			table.putBoolean("Hood Top", Hood.getInstance().isAtPosition(Hood.Position.TOP));
			table.putNumber("Hood Angle", Hood.getInstance().get());
			
			AndroidConnection connection = new AndroidConnection();
	    	connection.run();
	    	boolean hoodAtThreshold;
	    	if(!connection.goodToGo()) { 
	    		System.out.println("Android connection not good to go");
	    		hoodAtThreshold = false;
	    	} else {
	    		hoodAtThreshold = Math.abs(Hood.getInstance().get() - Hood.distanceToAngle(connection.getDistance())) > RobotMap.HOOD_THRESHOLD;
	    	}
	    	
			table.putBoolean("Hood at Threshold", hoodAtThreshold);
			table.putNumber("Shot distance at angle", Hood.angleToDistance(Hood.getInstance().get()));
			
			//Intake Arm
			table.putBoolean("Intake Top Stop", IntakeArm.getInstance().get() < RobotMap.INTAKE_TOP_POS - RobotMap.INTAKE_ARM_THRESHOLD);
			table.putBoolean("Intake Top", IntakeArm.getInstance().get() > RobotMap.INTAKE_TOP_POS - RobotMap.INTAKE_ARM_THRESHOLD && IntakeArm.getInstance().get() < RobotMap.INTAKE_TOP_POS + RobotMap.INTAKE_ARM_THRESHOLD);
			table.putBoolean("Intake Top Intake", IntakeArm.getInstance().get() > RobotMap.INTAKE_TOP_POS + RobotMap.INTAKE_ARM_THRESHOLD && IntakeArm.getInstance().get() < RobotMap.INTAKE_INTAKE_POS - RobotMap.INTAKE_ARM_THRESHOLD);
			table.putBoolean("Intake Intake", IntakeArm.getInstance().get() > RobotMap.INTAKE_INTAKE_POS - RobotMap.INTAKE_ARM_THRESHOLD && IntakeArm.getInstance().get() < RobotMap.INTAKE_INTAKE_POS + RobotMap.INTAKE_ARM_THRESHOLD);
			table.putBoolean("Intake Intake Home", IntakeArm.getInstance().get() > RobotMap.INTAKE_INTAKE_POS + RobotMap.INTAKE_ARM_THRESHOLD && IntakeArm.getInstance().get() < RobotMap.INTAKE_HOME_POS - RobotMap.INTAKE_ARM_THRESHOLD);
			table.putBoolean("Intake Home", IntakeArm.getInstance().get() > RobotMap.INTAKE_HOME_POS - RobotMap.INTAKE_ARM_THRESHOLD && IntakeArm.getInstance().get() < RobotMap.INTAKE_HOME_POS + RobotMap.INTAKE_ARM_THRESHOLD);
			table.putBoolean("Intake Home Bottom", IntakeArm.getInstance().get() > RobotMap.INTAKE_HOME_POS + RobotMap.INTAKE_ARM_THRESHOLD && IntakeArm.getInstance().get() < RobotMap.INTAKE_BOTTOM_POS - RobotMap.INTAKE_ARM_THRESHOLD);
			table.putBoolean("Intake Bottom", IntakeArm.getInstance().get() > RobotMap.INTAKE_BOTTOM_POS - RobotMap.INTAKE_ARM_THRESHOLD && IntakeArm.getInstance().get() < RobotMap.INTAKE_BOTTOM_POS + RobotMap.INTAKE_ARM_THRESHOLD);
			table.putBoolean("Intake Bottom Stop", IntakeArm.getInstance().get() > RobotMap.INTAKE_BOTTOM_POS + RobotMap.INTAKE_ARM_THRESHOLD);
			
			table.putBoolean("Photo 1", IntakeRoller.getInstance().hasBall());
			table.putBoolean("Photo 2", LoaderRoller.getInstance().hasBall());
			table.putBoolean("Photo 3", Shooter.getInstance().hasBall());
			
			//Elevator
			table.putBoolean("Elevator Bottom Height", false);
			table.putBoolean("Elevator Top Height", false);
			table.putBoolean("Elevator Motor Running", Elevator.getInstance().isMoving());

			//Shooter
			table.putNumber("Shooter Speed", Shooter.getInstance().getScaledSpeed());
			table.putNumber("Shooter Target Speed", Shooter.getInstance().getSetpoint() * RobotMap.SHOOTER_MAX_SPEED);
			
			table.putBoolean("Got Low", Hood.getInstance().isAtBottom() && (IntakeArm.getInstance().get() < RobotMap.INTAKE_INTAKE_POS + RobotMap.INTAKE_ARM_THRESHOLD || IntakeArm.getInstance().get() > RobotMap.INTAKE_INTAKE_POS - RobotMap.INTAKE_ARM_THRESHOLD));
			

		}
	}

	@Override
	public ITable getTable() {
		return table;
	}

	@Override
	public String getSmartDashboardType() {
		return "Robot Diagram";
	}

	@Override
	public String getName() {
		return "Robot Diagram";
	}

}
