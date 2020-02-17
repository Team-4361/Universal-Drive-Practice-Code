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
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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
  //if driveMode = 2; One sitck arcade (Behaves like Xbox arcade)
  //if driveMode = 3; Two stick arcade (Left - F/B, Right - L/R)
  //if driveMode = 4; Xbox tank control
  //if driveMode = 5; Xbox one-stick arcade control
  //if driveMode = 6; Xbox two-stick arcade control

  //Value creation
  public static int driveMode, stickSide;//stickSide - Left=0 Right=1
  public static double div;//divDef is the Default of div
  public static boolean slowMode;//make sure kyle uses this
  
  //Drive creation
  WPI_TalonSRX lDrive1;
  WPI_TalonSRX lDrive2;
  //WPI_TalonSRX[] lDriveMotors;
  Drive lDrive;
  WPI_TalonSRX rDrive1;
  WPI_TalonSRX rDrive2;
  //WPI_TalonSRX[] rDriveMotors;
  Drive rDrive;
  TankDrive theTank;
  
  //Stick creation
  Joystick lStick;
  Joystick rStick;

  //Drive controls creation
  JoystickTank stickTank;
  JoystickArcade stickArcade;
  JoystickArcade2 stickArcade2;
  XboxController xContOp;
  XboxTank xContTCon;
  XboxArcade xContArcade;
  XboxArcade2 xContArcade2;


  @Override
  public void robotInit()
  {
    //Value assignment
    driveMode = 1;
    stickSide = 1;//stickSide - Left=0 Right=1
    div=0.5;//Slow Mode Multiplier (default=0.5)
    slowMode = true;
    
    //Drive assignment
    lDrive1 = new WPI_TalonSRX(1);
    lDrive2 = new WPI_TalonSRX(5);
    WPI_TalonSRX[] lDriveMotors = {lDrive1, lDrive2};
    lDrive = new Drive(lDriveMotors);
    rDrive1 = new WPI_TalonSRX(3);
    rDrive2 = new WPI_TalonSRX(4);
    WPI_TalonSRX[] rDriveMotors = {rDrive1, rDrive2};
    rDrive = new Drive(rDriveMotors);
    theTank = new TankDrive(lDrive, rDrive);
    
    //Stick assignment
    lStick = new Joystick(0);
    rStick = new Joystick(1);

    //Drive controls assignment
    stickTank = new JoystickTank(0, 1);
    stickArcade = new JoystickArcade(stickSide);
    stickArcade2 = new JoystickArcade2(0, 1);
    xContOp = new XboxController(2);
    xContTCon = new XboxTank(2);
    xContArcade = new XboxArcade(2, Hand.kLeft);
    xContArcade2 = new XboxArcade2(2);

    //Initialize and add values to SmartDashboard/ShuffleBoard
    SmartDashboard.putNumber("Drive Mode", driveMode);
    SmartDashboard.putNumber("Arcade Stick Side", stickSide);
    SmartDashboard.putBoolean("Slow Mode", slowMode);
    SmartDashboard.putString("Drive Mode List", " 1 = Two-Stick(Normal) | Tank 2 = One-Stick(Right side) Arcade | 3 = Two-Stick(L=F/B R=L/R) Arcade | 4 = Xbox Tank | 5 = Xbox One-Stick Arcade | 6 = Xbox Two-Stick Arcade");
    SmartDashboard.putNumber("Slow Mode Multiplier (default=0.5)", div);
    
    try
		{
      CameraServer.getInstance().startAutomaticCapture("Camera 0", 0);
      CameraServer.getInstance().startAutomaticCapture("Camera 1", 1);
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
    /*Xbox Start Button*/if(xContOp.getStartButtonPressed()) { if(slowMode==false){slowMode=true;} else if(slowMode==true){slowMode=false;} }
    /*Xbox A Button*/if(xContOp.getAButtonPressed()) { if(slowMode==false){slowMode=true;} else if(slowMode==true){slowMode=false;} }
    /*Xbox Bumpers*/if(xContOp.getBumperPressed(Hand.kLeft) || xContOp.getBumperPressed(Hand.kRight)) { if(slowMode==false){slowMode=true;} else if(slowMode==true){slowMode=false;} }
    /*Left Stick*/if(lStick.getTriggerPressed()) { if(slowMode==false){slowMode=true;} else if(slowMode==true){slowMode=false;} }
    /*Right Stick*/if(rStick.getTriggerPressed()) { if(slowMode==false){slowMode=true;} else if(slowMode==true){slowMode=false;} }

    //Update values to/from SmartDashboard/ShuffleBoard
    if (SmartDashboard.getNumber("Slow Mode Multiplier (default=0.5)", div)>0.9)
    { div=0.5; SmartDashboard.putNumber("Slow Mode Multiplier (default=0.5)", 0.5); }
    else { div=(double)SmartDashboard.getNumber("Slow Mode Multiplier (default=0.5)", div); SmartDashboard.putNumber("Slow Mode Multiplier (default=0.5)", div); }
    SmartDashboard.putNumber("Drive Mode", driveMode);
    SmartDashboard.putNumber("Arcade Stick Side", stickSide);
    SmartDashboard.putBoolean("Slow Mode", slowMode);
    driveMode=(int)SmartDashboard.getNumber("Drive Mode", driveMode);
    stickSide=(int)SmartDashboard.getNumber("Arcade Stick Side", stickSide);
    slowMode=SmartDashboard.getBoolean("Slow Mode", slowMode);    


    if(driveMode == 1)//two stick tank, use stickTank
    {
      if(slowMode==false) { theTank.drive(stickTank.GetDrive()); }
      else { theTank.drive(stickTank.GetDriveDiv(div)); }
    }

    if(driveMode == 2)//one stick arcade, use stickArcade
    {
      if(slowMode==false) { theTank.drive(stickArcade.GetDrive()); }
      else { theTank.drive(stickArcade.GetDriveDiv(div)); }
    }

    if (driveMode == 3)//two stick arcade, use stickArcade2
    {
      if(slowMode==false) { theTank.drive(stickArcade2.GetDrive()); }
      else { theTank.drive(stickArcade2.GetDriveDiv(div)); }
    }
    
    if (driveMode==4)//xbox tank control, use xContTCon
    {
      if (slowMode==false) { theTank.drive(xContTCon.GetDrive()); }
      else { theTank.drive(xContTCon.GetDriveDiv(div)); }
    }

    if (driveMode==5)//xbox one-stick arcade control, use xContArcade
    {
      if (slowMode==false) { theTank.drive(xContArcade.GetDrive()); }
      else { theTank.drive(xContArcade.GetDriveDiv(div)); }
    }

    if (driveMode==6)//xbox two-stick arcade control, use xContArcade2
    {
      if (slowMode==false) { theTank.drive(xContArcade2.GetDrive()); }
      else { theTank.drive(xContArcade2.GetDriveDiv(div)); } 
    }
  }
}