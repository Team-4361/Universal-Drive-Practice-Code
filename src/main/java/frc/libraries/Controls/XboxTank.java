package frc.libraries.Controls;

import edu.wpi.first.wpilibj.XboxController;

public class XboxTank extends XboxController
{
	public XboxTank(int position)
	{
		super(position);
	}
	
	public double[] GetDrive()
	{
		double[] val = new double[2];
		//Left
		val[0] = -getY(Hand.kLeft);
		//Right
		val[1] = getY(Hand.kRight);
		
		return val;
	}

	public double[] GetDriveSpeed(double div)
	{
		double[] val = new double[2];
		//Left
		val[0] = -(getY(Hand.kLeft)*div);
		//Right
		val[1] = (getY(Hand.kRight)*div);
		
		return val;
	}
}
