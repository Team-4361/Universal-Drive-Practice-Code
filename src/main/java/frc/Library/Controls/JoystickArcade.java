package frc.Library.Controls;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickArcade extends Joystick
{
	public JoystickArcade(int pos)
	{
		super(pos);
	}
	
	public double[] GetDrive()
	{
		double[] val = new double[2];
		//Left
		val[0] = getX() - getY();
		//Right
		val[1] = getX() + getY();
		
		return val;
	}

	public double[] GetDriveDiv(int div)
	{
		double[] val = new double[2];
		//Left
		val[0] = ((getX() - getY())/div);
		//Right
		val[1] = ((getX() + getY())/div);
		
		return val;
	}
}
