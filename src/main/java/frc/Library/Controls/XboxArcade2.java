package frc.Library.Controls;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public class XboxArcade2 extends XboxController
{
	public XboxArcade2(int position)
	{
		super(position);
	}
	
	public double[] GetDrive()
	{
		double[] val = new double[2];
		//Left
		val[0] = getX(Hand.kRight) - getY(Hand.kLeft);
		//Right
		val[1] = getX(Hand.kRight) + getY(Hand.kLeft);
		
		return val;
	}

	public double[] GetDriveDiv(double div)
	{
		double[] val = new double[2];
		//Left
		val[0] = ((getX(Hand.kRight) - getY(Hand.kLeft))*div);
		//Right
		val[1] = ((getX(Hand.kRight) + getY(Hand.kLeft))*div);
		
		return val;
	}
}