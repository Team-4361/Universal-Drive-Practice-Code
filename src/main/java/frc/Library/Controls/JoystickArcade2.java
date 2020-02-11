package frc.Library.Controls;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickArcade2
{
    public Joystick left, right;
	public JoystickArcade2(int lPos, int rPos)
	{
		left = new Joystick(lPos);
		right = new Joystick(rPos);
	}

    //Y vals do forward/backward
    //X vals do left/right turn
    public double[] GetDrive()
    {
        double[] val = new double[2];
        //Left drive
        val[0] = right.getX() - left.getY();
        //Right drive
        val[1] = right.getX() + left.getY();
        
        return val;
    }

    public double[] GetDriveDiv(int div)
    {
        double[] val = new double[2];
        //Left drive
        val[0] = ((right.getX() - left.getY())/div);
        //Right drive
        val[1] = ((right.getX() + left.getY())/div);
        
        return val;
    }
}