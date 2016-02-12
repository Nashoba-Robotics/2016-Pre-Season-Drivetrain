import static org.junit.Assert.*;

import org.junit.Test;

import edu.nr.lib.AngleUnit;
import edu.nr.lib.Position;

public class PositionTest {

	@Test
	public void testOrigin() {
		Position pos = new Position();
		System.out.println("@Test position origin: " + pos.x + " == 0, " + pos.y + " == 0");
		assertEquals(pos.x,0,0);
		assertEquals(pos.y,0,0);
	}
	
	@Test
	public void testInteger() {
		int x = 1;
		int y = 2;
		Position pos = new Position(x,y);
		
		System.out.println("@Test position int: " + pos.x + " == " + x + ", " + pos.y + " == " + y);
		assertEquals(pos.x,x,0);
		assertEquals(pos.y,y,0);
	}
	
	@Test
	public void testSetInteger() {
		int x = 1;
		int y = 2;
		Position pos = new Position();
		pos.setXY(x, y);
		
		System.out.println("@Test position set int: " + pos.x + " == " + x + ", " + pos.y + " == " + y);
		assertEquals(pos.x,x,0);
		assertEquals(pos.y,y,0);
	}
	
	@Test
	public void testDouble() {
		double xD = 1.57925;
		double yD = 2.93723;
		
		Position pos = new Position(xD, yD);
		System.out.println("@Test position double: " + pos.x + " == " + xD + ", " + pos.y + " == " + yD);
		assertEquals(pos.x,xD,0);
		assertEquals(pos.y,yD,0);
	}
	
	@Test
	public void testSetDouble() {
		double xD = 1.57925;
		double yD = 2.93723;
		
		Position pos = new Position();
		pos.setXY(xD, yD);
		System.out.println("@Test position set double: " + pos.x + " == " + xD + ", " + pos.y + " == " + yD);
		assertEquals(pos.x,xD,0);
		assertEquals(pos.y,yD,0);
	}
	
	@Test
	public void testSetPolarDegree() {
		double angle = 150.43;
		double magnitude = 2.93723;
		
		Position pos = new Position();
		pos.setPolar(magnitude, angle, AngleUnit.DEGREE);
		System.out.println("@Test position set polar angle degree: " + pos.getAngle(AngleUnit.DEGREE) + " == " + angle);
		assertEquals(pos.getAngle(AngleUnit.DEGREE),angle,0.0001);
		System.out.println("@Test position set polar magnitude: " + pos.getMagnitude() + " == " + magnitude);
		assertEquals(pos.getMagnitude(),magnitude,0.0001);
		System.out.println("@Test position set polar x degree: " + pos.x + " == " + -2.554666);
		assertEquals(pos.x, -2.554666, 0.000001);
		System.out.println("@Test position set polar y degree: " + pos.y + " == " + 1.449483);
		assertEquals(pos.y, 1.449483, 0.000001);
	}
	
	@Test
	public void testPolarDegree() {
		double angle = 150.43;
		double magnitude = 2.93723;
		
		Position pos = new Position(magnitude, angle, AngleUnit.DEGREE);
		System.out.println("@Test position polar angle degree: " + pos.getAngle(AngleUnit.DEGREE) + " == " + angle);
		assertEquals(pos.getAngle(AngleUnit.DEGREE),angle,0.0001);
		System.out.println("@Test position polar magnitude degree: " + pos.getMagnitude() + " == " + magnitude);
		assertEquals(pos.getMagnitude(),magnitude,0.0001);
		System.out.println("@Test position polar x degree: " + pos.x + " == " + -2.554666);
		assertEquals(pos.x, -2.554666, 0.000001);
		System.out.println("@Test position polar y degree: " + pos.y + " == " + 1.449483);
		assertEquals(pos.y, 1.449483, 0.000001);
	}
	
	@Test
	public void testSetPolarRadian() {
		double angle = 2.4 * Math.PI;
		double magnitude = 2.93723;
		
		Position pos = new Position();
		pos.setPolar(magnitude, angle, AngleUnit.RADIAN);
		System.out.println("@Test position set polar angle radian: " + pos.getAngle(AngleUnit.RADIAN) + " == " + angle);
		assertEquals(pos.getAngle(AngleUnit.RADIAN),angle%(2*Math.PI),0.0001);
		System.out.println("@Test position set polar magnitude radian: " + ", " + pos.getMagnitude() + " == " + magnitude);
		assertEquals(pos.getMagnitude(),magnitude,0.0001);
		System.out.println("@Test position set polar x radian: " + pos.x + " == " + -2.554666);
		assertEquals(pos.x, 0.907654, 0.000001);
		System.out.println("@Test position set polar y radian: " + pos.y + " == " + 1.449483);
		assertEquals(pos.y, 2.793472, 0.000001);
	}
	
	@Test
	public void testPolarRadian() {
		double angle = 2.4 * Math.PI;
		double magnitude = 2.93723;
		
		Position pos = new Position(magnitude, angle, AngleUnit.RADIAN);
		System.out.println("@Test position set polar angle radian: " + pos.getAngle(AngleUnit.RADIAN) + " == " + angle);
		assertEquals(pos.getAngle(AngleUnit.RADIAN),angle%(2*Math.PI),0.0001);
		System.out.println("@Test position set polar magnitude radian: " + ", " + pos.getMagnitude() + " == " + magnitude);
		assertEquals(pos.getMagnitude(),magnitude,0.0001);
		System.out.println("@Test position set polar x radian: " + pos.x + " == " + -2.554666);
		assertEquals(pos.x, 0.907654, 0.000001);
		System.out.println("@Test position set polar y radian: " + pos.y + " == " + 1.449483);
		assertEquals(pos.y, 2.793472, 0.000001);
	}

}
