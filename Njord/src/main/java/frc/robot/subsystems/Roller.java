// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static edu.wpi.first.units.Units.derive;

import com.ctre.phoenix6.hardware.TalonFX;

public class Roller extends SubsystemBase {
  /** Creates a new Roller. */

  private TalonFX rollerMotor;
  private static boolean RollerActive;
  public static RollerEnumState mRollerEnumState;

  public enum RollerEnumState {
    S_Empty, S_Loaded, S_Eject
  }

  public Roller() {
    rollerMotor = new TalonFX(Constants.RollerConstants.kRollerMotor);
    RollerActive = false;
    mRollerEnumState = RollerEnumState.S_Empty;
  }

  public void runRollerState() {
    switch(mRollerEnumState) {
      case S_Empty:
        Empty();
        break;
      case S_Loaded:
        Loaded();
        break;
      case S_Eject:
        Eject();
        break;
    }
  }

  public void Empty() {
   if (RollerActive) {
    rollerMotor.setVoltage(-4);
    if (hasAlgae()) {
      Roller.mRollerEnumState = RollerEnumState.S_Loaded;
    }
   }
   else{
    rollerMotor.setVoltage(0);
   }
  }

  public void Loaded() {
    rollerMotor.setVoltage(0);
  }

  public void Eject() {
    rollerMotor.setVoltage(4);
    Roller.mRollerEnumState = RollerEnumState.S_Empty;
  }
  //scores the piece!

  public boolean hasAlgae() {
    return true;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
