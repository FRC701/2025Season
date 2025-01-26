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

/** Add your docs here. */
public class Constants {
    public static final Transform3d robotToLimeLight3d =
        new Transform3d(
            new Translation3d(Units.inchesToMeters(0), Units.inchesToMeters(0), Units.inchesToMeters(0)),
            new Rotation3d(0.0, 0.0, 0.0));

            
    public static final Transform3d robotToFishEye3d =
    new Transform3d(
        new Translation3d(Units.inchesToMeters(0), Units.inchesToMeters(-2.4), Units.inchesToMeters(0)),
        new Rotation3d(0.0, 0.0, 0.0));

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
