// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;

public class Pivot extends SubsystemBase {
  /** Creates a new Pivot. */

  private TalonFX pivotMotor;
  public static PivotEnumState mPivotEnumState;

  public enum PivotEnumState {
    S_Raise, S_Middle, S_Lower
  }
  //not sure if this is too much for the states that we have

  public Pivot() {
    pivotMotor = new TalonFX(Constants.PivotConstants.kPivotMotor);
  }

  public void runPivotState() {
    switch(mPivotEnumState) {
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
