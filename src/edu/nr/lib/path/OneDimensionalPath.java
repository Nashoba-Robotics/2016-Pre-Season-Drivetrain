package edu.nr.lib.path;

import edu.nr.robotics.RobotMap;

public class OneDimensionalPath {

	double distance, maxSpeed, maxAcc, length;
	
	//The times for ending speeding up and starting slowing down
	double endUp, startDown;
	
	public OneDimensionalPath (double distance, double maxSpeed, double maxAcc) {
		this.distance = distance;
		this.maxSpeed = maxSpeed;
		this.maxAcc = maxAcc;
		double i = 0;
		for(; i*maxAcc < maxSpeed; i += 0.01) {}
		endUp = i;
		double timeAtMaxSpeed = (distance - maxSpeed * endUp * 2)/maxSpeed;
		startDown = endUp + timeAtMaxSpeed;
		if(startDown < endUp) {
			endUp = startDown; //Make sure that endUp <= endDown
		}
		length = startDown + endUp;
	}
	
	public OneDimensionalPath(double distance) {
		this(distance, RobotMap.MAX_SPEED, RobotMap.MAX_ACCELERATION);
	}
	
	public double getPosition(double time) {
		if(time > endUp && time <= startDown) {
			return getPosition(endUp) + maxSpeed*(time - endUp); //If we've finished speeding up, but haven't started slowing down, we're at a constant speed
		} else if(time <= endUp) {
			return maxAcc * time * time * 0.5; //If we haven't finished speeding up, we've been accelerating at maxAcc, and x = 1/2 a * t^2
		} else {
			//We must not have finished slowing down, we've been accelerating at -maxAcc, we started at maxSpeed.
			//This uses area of a trapezoid, with one of the bases being maxSpeed and the other base being the speed at the current time.
			//Speed at current time = maxSpeed - maxAcc * time since maxSpeed
			return getPosition(startDown) + 0.5 * (time - startDown) * (maxSpeed + maxSpeed - maxAcc * (time - startDown)); 
		}
	}
	
	public double getSpeed(double time) {
		if(time > endUp && time <= startDown) {
			return maxSpeed; //If we've finished speeding up, but haven't started slowing down, we're at maxSpeed
		} else if(time <= endUp) {
			return time * maxAcc; //If we haven't finished speeding up, we're at the integral of maxAcc from 0 to time
		} else {
			return (time - startDown) * maxAcc; //We must not have finished slowing down, so we're at the integral of maxAcc from startDown to time
		}
	}
	
	public double getAcc(double time) {
		if(time > endUp && time <= startDown) {
			return 0; //If we've finished speeding up, but haven't started slowing down, we're at a constant speed
		} else if(time <= endUp) {
			return maxAcc; //If we haven't finished speeding up, we're accelerating at maxAcc
		} else {
			return -maxAcc; //We must not have finished slowing down, so we're accelerating at -maxAcc
		}
	}
	
	/*
	 * @returns length in seconds of the path
	 */
	public double getLength() {
		return length;
	}
	
	/*
	 * @returns length in meters of the path
	 */
	public double getDistance() {
		return getPosition(length);
	}

}
