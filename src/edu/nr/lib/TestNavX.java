package edu.nr.lib;

import edu.nr.lib.navx.NavX;

public class TestNavX extends NavX {

	//Saved as degrees
	private double roll;
	private double pitch;
	private double yaw;
	
	public TestNavX(double yaw, double pitch, double roll, AngleUnit unit) {
		setPitch(pitch, unit);
		setRoll(roll, unit);
		setYaw(yaw, unit);
	}
	
	public TestNavX() {
		pitch = 0;
		yaw = 0;
		roll = 0;
	}
	
	@Override
	public double getRoll(AngleUnit unit) {
		if(unit == AngleUnit.RADIAN) {
			return Math.toRadians(roll);
		} else {
			return roll;
		}
	}
	
	@Override
	public double getPitch(AngleUnit unit) {
		if(unit == AngleUnit.RADIAN) {
			return Math.toRadians(pitch);
		} else {
			return pitch;
		}
	}
	
	@Override
	public double getYaw(AngleUnit unit) {
		if(unit == AngleUnit.RADIAN) {
			return Math.toRadians(yaw);
		} else {
			return yaw;
		}
	}
	
	@Override
	public double getYawAbsolute(AngleUnit unit) {
		return getYaw(unit);
	}

	public void setRoll(double roll, AngleUnit unit) {
		if(unit == AngleUnit.RADIAN) {
			this.roll = Math.toDegrees(roll);
		} else {
			this.roll = roll;
		}
	}

	public void setPitch(double pitch, AngleUnit unit) {
		if(unit == AngleUnit.RADIAN) {
			this.pitch = Math.toDegrees(pitch);
		} else {
			this.pitch = pitch;
		}
	}

	public void setYaw(double yaw, AngleUnit unit) {
		if(unit == AngleUnit.RADIAN) {
			this.yaw = Math.toDegrees(yaw);
		} else {
			this.yaw = yaw;
		}
	}
}
