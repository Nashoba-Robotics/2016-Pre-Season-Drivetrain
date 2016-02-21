package edu.nr.lib;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.*;

public class DoubleJoystickButton extends Button {

	GenericHID m_joystick;
	int m_buttonNumberOne;
	int m_buttonNumberTwo;
	
	public DoubleJoystickButton(GenericHID joystick, int buttonNumberOne, int buttonNumberTwo) {
		m_joystick = joystick;
		m_buttonNumberOne = buttonNumberOne;
		m_buttonNumberTwo = buttonNumberTwo;
	}
	
	public boolean get() {
		return m_joystick.getRawButton(m_buttonNumberOne) && m_joystick.getRawButton(m_buttonNumberTwo);
	}
}
