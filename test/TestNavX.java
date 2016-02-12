import edu.nr.lib.AngleUnit;
import edu.nr.lib.navx.NavX;

public class TestNavX extends NavX {

	//Saved as degrees
	private double roll;
	private double pitch;
	private double yaw;
	
	private static TestNavX singleton;

	public static TestNavX getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new TestNavX();
		}
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
