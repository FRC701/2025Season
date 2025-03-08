// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final String kCarnivoreName = "Cani";
  
  public static class IntakeConstants {
    public static final int kIntakeMotor1 = 28;
  }

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kCoDriverControllerPort = 1;
  }

  public static class PivotConstants{
    public static final int kPivotMotor = 23;
    public static final double kLevel1Angle = -4.2;
    public static final double kLevel2Angle = -2;//placeholder
    public static final double kLevel3Angle = -3.5;//placeholder
    public static final double kLevel4Angle = -5;//placeholder
    public static final double kLevel5Angle = -6.5;
  }
  public static class ClimberConstants {
  public static final int kClimbLeft = 0;
  public static final int kClimbRight = 1;
  }
  public static class ElevatorConstants {
    public static final int kElevatorMotor = 4;
    public static final int kElevatorMotor2 = 2;

    public static final double kLevel1Height = 2;
    public static final double kLevel2Height = 10; //placeholder
    public static final double kLevel3Height = 20; //placeholder
    public static final double kLevel4Height = 27; //placeholder
    public static final double kLevel5Height = 15;
  }
}