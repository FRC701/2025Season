// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;

public class Arm extends SubsystemBase {

  private TalonFX armMotor;
  public ArmEnumState mArmEnumState;

  public enum ArmEnumState {
    S_Lower, S_Middle, S_Raise
  }
  //not sure if two or three states is okay, I'm keeping it at three for now

  /** Creates a new Arm. */
  public Arm() {
    armMotor = new TalonFX(Constants.ArmConstants.kArmMotor);
  }

  public void runArmState() {
    switch (mArmEnumState) {
      case S_Lower:
        Lower();
        break;
      case S_Middle:
        Middle();
        break;
      case S_Raise:
        Raise();
        break;
    }
  }

  public void Lower() {
    //placeholder
  }

  public void Middle() {
    //placeholder
  }

  public void Raise() {
    //placeholder
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
