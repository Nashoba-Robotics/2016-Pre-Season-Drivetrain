package edu.nr.lib.drive;

public class DriveSignal {
	public double leftMotor;
    public double rightMotor;
    public double middleMotor;

    public DriveSignal(double left, double right, double middle) {
        this.leftMotor = left;
        this.rightMotor = right;
        this.middleMotor = middle;
    }

    public static DriveSignal NEUTRAL = new DriveSignal(0, 0, 0);
}
