package frc.libraries.Controls;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickTank
{
	public Joystick left, right;
	public JoystickTank(int lPos, int rPos)
	{
		left = new Joystick(lPos);
		right = new Joystick(rPos);
	}
	
	//Left = 0, Right = 1
	public double[] GetDrive()
	{
		double[] val = new double[2];
		val[0] = -left.getY();
		val[1] = right.getY();
		
		return val;
	}

	public double[] GetDriveSpeed(double div)
	{
		double[] val = new double[2];
		val[0] = -(left.getY()*div);
		val[1] = (right.getY()*div);
		
		return val;
	}
	
	public double[] GetPerfectStraight()
	{
		double[] val = new double[2];
		val[0] = -right.getY();
		val[1] = right.getY();
		
		return val;
	}
	
	public double[] GetPerfectTurn()
	{
		double[] val = new double[2];
		val[0] = -right.getX();
		val[1] = right.getX();
		
		return val;
	}
}
