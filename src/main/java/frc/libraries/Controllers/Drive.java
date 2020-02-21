package frc.libraries.Controllers;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;

public class Drive {
	WPI_TalonSRX[] talons;
	CANSparkMax[] sparks;
	
	static WPI_TalonSRX[] CANTalons;
	static CANSparkMax[] CANSparks;

	String controllerType;
	
	public Drive(final WPI_TalonSRX[] CAN) {
		this.talons = CAN;
		controllerType = "TalonSRX";
	}

	public Drive(final CANSparkMax[] CAN) {
		this.sparks = CAN;
		controllerType = "SparkMax";
	}

	public Drive(final int[] nums) {
		if(controllerType == "TalonSRX")
		{
			talons = new WPI_TalonSRX[nums.length];
			for (int i = 0; i < nums.length; i++) {
				talons[i] = CANTalons[nums[i]];
			}
		}
		if(controllerType == "SparkMax")
		{	
			sparks = new CANSparkMax[nums.length];
			for (int i = 0; i < nums.length; i++) {
				sparks[i] = CANSparks[nums[i]];
			}
		}
	}
	

	public void drive(final double val)
	{
		if(controllerType == "TalonSRX")
		{
			for (final WPI_TalonSRX tal : talons) {
				tal.set(val);
			}
		}
		else if(controllerType == "SparkMax")
		{
			for(final CANSparkMax spark : sparks)
			{
				spark.set(val);
			}
		}
	}

	public double GetSpeed() {
		if (controllerType == "TalonSRX" && talons != null && talons[0] != null)
			return talons[0].get();
		else if(controllerType == "SparkMAX" && sparks != null && sparks[0] != null)
			return sparks[0].get();
		return 0;
	}

	public static void SetFullCAN(final WPI_TalonSRX[] CAN)
	{
		CANTalons = CAN;
	}

	public static void SetFullSparks(final CANSparkMax[] sparks)
	{
		CANSparks = sparks;
	}
	
	public WPI_TalonSRX[] GetTalons()
	{
		return talons;
	}
	public CANSparkMax[] GetSparks()
	{
		return sparks;
	}
}