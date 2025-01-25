// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.numbers.N3;
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
}
