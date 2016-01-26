package edu.nr.robotics;

import edu.nr.robotics.subsystems.hood.Hood;
import edu.nr.robotics.subsystems.intakearm.IntakeArm;
import edu.nr.robotics.subsystems.loaderroller.LoaderRoller;
import edu.nr.robotics.subsystems.shooter.Shooter;
import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.tables.ITable;

public class RobotDiagram implements NamedSendable {

	public RobotDiagram() {
		// TODO Auto-generated constructor stub
	}

private ITable table;

	public void initTable(ITable table) {
		this.table = table;
		if (table != null) {
			table.putString("~TYPE~", "robo-diagram");

			//Hood
			table.putNumber("Hood Position", Hood.getInstance().get());
			//Intake Arm
			table.putNumber("Intake Arm Position", IntakeArm.getInstance().get());
			//Rollers
			table.putBoolean("Intake Roller Running", IntakeArm.getInstance().getRollerRunning());
			table.putBoolean("Loader Roller Runing", LoaderRoller.getInstance().getLoaderRunning());
			//Shooter
			table.putNumber("Shooter Percent", Shooter.getInstance().getSpeedPercent());
			table.putBoolean("Shooter Full Speed", Shooter.getInstance().getSped());
			//Ball
			table.putBoolean("Ball In Intake", IntakeArm.getInstance().getBallInIntake());
			table.putBoolean("Ball in Loader", LoaderRoller.getInstance().getBallInLoader());
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
