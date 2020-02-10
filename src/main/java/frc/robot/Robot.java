/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.cameraserver.CameraServer;
import frc.Library.Chassis.TankDrive;
import frc.Library.Controllers.Drive;
import frc.Library.Controls.JoystickTank;
import frc.Library.Controls.JoystickArcade;
import frc.Library.Controls.JoystickArcade2;
import frc.Library.Controls.XboxTank;
import frc.Library.Controls.XboxArcade;
import frc.Library.Controls.XboxArcade2;


public class Robot extends TimedRobot
//public class Robot extends IterativeRobot
{
  //if driveMode = 1; Two stick tank control
  //if driveMode = 2; Two stick arcade (Left - F/B, Right - L/R)
  //if driveMode = 3; One sitck arcade (Behaves like Xbox arcade)
  //if driveMode = 4; Xbox tank control
  //if driveMode = 5; Xbox one-stick arcade control
  //if driveMode = 6; Xbox two-stick arcade control
  public static int driveMode = 1;
  public static int stickSide = 1;//stickSide - Left=0 Right=1
  public static boolean slowMode = true;
  
  WPI_TalonSRX lDrive1 = new WPI_TalonSRX(1);
  WPI_TalonSRX lDrive2 = new WPI_TalonSRX(2);
  WPI_TalonSRX[] lDriveMotors = {lDrive1,lDrive2};
  Drive lDrive = new Drive(lDriveMotors);
  WPI_TalonSRX rDrive1 = new WPI_TalonSRX(4);
  WPI_TalonSRX rDrive2 = new WPI_TalonSRX(6);
  WPI_TalonSRX[] rDriveMotors = {rDrive1,rDrive2};
  Drive rDrive = new Drive(rDriveMotors);
  TankDrive theTank = new TankDrive(lDrive, rDrive);
  
  //Stick names
  Joystick lStick = new Joystick(0);
  Joystick rStick = new Joystick(1);

  //Drive controls creation
  JoystickTank stickTank = new JoystickTank(0, 1);
  JoystickArcade stickArcade = new JoystickArcade(stickSide);
  JoystickArcade2 stickArcade2 = new JoystickArcade2(0, 1);
  XboxController xContOp = new XboxController(2);
  XboxTank xContTCon = new XboxTank(2);
  XboxArcade xContArcade = new XboxArcade(2, Hand.kLeft);
  XboxArcade2 xContArcade2 = new XboxArcade2(2);


  @Override
  public void robotInit()
  {
    //Initialize and add values to SmartDashboard/ShuffleBoard
    SmartDashboard.putNumber("Drive Mode", driveMode);
    SmartDashboard.putNumber("Arcade Stick Side", stickSide);
    SmartDashboard.putBoolean("Slow Mode", slowMode);
    
    try 
		{
      CameraServer.getInstance().startAutomaticCapture("Camera Front", 0);
      CameraServer.getInstance().startAutomaticCapture("Camera Rear", 1);
		}
		catch (Exception e)
		{
			System.out.println("Camera Error: " + e.getMessage());
		}
  }

  @Override
  public void teleopPeriodic()
  {
    //Cycle drive modes
    if(xContOp.getBackButtonPressed()) { if(driveMode<6){driveMode++;} else{driveMode=1;} }
    //Toggle slow drive mode
    if(xContOp.getStartButtonPressed()) { if(slowMode==false){slowMode=true;} else if(slowMode==true){slowMode=false;} }

    //Update values on SmartDashboard/ShuffleBoard
    SmartDashboard.putNumber("Drive Mode", driveMode);
    SmartDashboard.putNumber("Arcade Stick Side", stickSide);
    SmartDashboard.putBoolean("Slow Mode", slowMode);
    //driveMode=(int)SmartDashboard.getNumber("Drive Mode", driveMode);
    //stickSide=(int)SmartDashboard.getNumber("Arcade Stick Side", stickSide);
    //slowMode=SmartDashboard.getBoolean("Slow Mode", slowMode);


    if(driveMode == 1)//two stick tank, use stickTank
    {
      if(slowMode==false) { theTank.drive(stickTank.GetDrive()); }
      else { theTank.drive(stickTank.GetDriveDiv(2)); }
    }

    if(driveMode == 2)//one stick arcade, use stickArcade
    {
      if(slowMode==false) { theTank.drive(stickArcade.GetDrive()); }
      else { theTank.drive(stickArcade.GetDriveDiv(2)); }
    }

    if (driveMode == 3)//two stick arcade, use stickArcade2
    {
      if(slowMode==false) { theTank.drive(stickArcade2.GetDrive()); }
      else { theTank.drive(stickArcade2.GetDriveDiv(2)); }
    }
    
    if (driveMode==4)//xbox tank control, use xContTCon
    {
      if (slowMode==false) { theTank.drive(xContTCon.GetDrive()); }
      else { theTank.drive(xContTCon.GetDriveDiv(2)); }
    }

    if (driveMode==5)//xbox one-stick arcade control, use xContArcade
    {
      if (slowMode==false) { theTank.drive(xContArcade.GetDrive()); }
      else { theTank.drive(xContArcade.GetDriveDiv(2)); }
    }

    if (driveMode==6)//xbox two-stick arcade control, use xContArcade2
    {
      if (slowMode==false) { theTank.drive(xContArcade2.GetDrive()); }
      else { theTank.drive(xContArcade2.GetDriveDiv(2)); } 
    }
  }
}