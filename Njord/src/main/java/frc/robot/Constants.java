// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

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
    public static final double kLevel1Angle = 0;
    public static final double kLevel2Angle = 0;//placeholder
    public static final double kLevel3Angle = 0;//placeholder
    public static final double kLevel4Angle = 0;//placeholder
    public static final double kLevel5Angle = 0;
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
  public static final Transform3d robotToLimeLight3d =
        new Transform3d(
            new Translation3d(Units.inchesToMeters(12.6), Units.inchesToMeters(0), Units.inchesToMeters(29.75)),
            new Rotation3d(0.0, 0.0, 0.0));

            
    public static final Transform3d robotToFishEye3d =
    new Transform3d(
        new Translation3d(Units.inchesToMeters(-12), Units.inchesToMeters(-2.4), Units.inchesToMeters(30)),
        new Rotation3d(0, 0, Math.PI));

    public static final edu.wpi.first.math.Vector<N3> statdev = VecBuilder.fill(0.05, 0.05,
    Units.degreesToRadians(5));

    
    public static final edu.wpi.first.math.Vector<N3> visdev = VecBuilder.fill(0.05, 0.05,
    Units.degreesToRadians(5));

    public static class TrajectoryConstants {

    public static final Pose2d kStationPose = new Pose2d(new Translation2d(1.579, 1.535), new Rotation2d(-128.928));
    

    public static final double kMaxSpeedMetersPerSecond = 4.73;
    public static final double kMaxAccelerationMetersPerSecondSquared = 4.73;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kPXController = 5;
    public static final double kPYController = 5;
    public static final double kPThetaController = 5;

    public static final PathConstraints constraints = new PathConstraints(
        kMaxAngularSpeedRadiansPerSecond,
     kMaxAccelerationMetersPerSecondSquared,
      kMaxAngularSpeedRadiansPerSecond,
       kMaxAngularSpeedRadiansPerSecondSquared);

    // Constraint for the motion profiled robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
        new TrapezoidProfile.Constraints(
            kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
  }
}