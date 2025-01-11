// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;

public class Roller extends SubsystemBase {
  /** Creates a new Roller. */

  private TalonFX rollerMotor;
  public RollerEnumState mRollerEnumState;

  public enum RollerEnumState {
    S_Empty, S_IntakeAlgae, S_Eject
  }

  public Roller() {
    rollerMotor = new TalonFX(Constants.RollerConstants.kRollerMotor);
  }

  public void runRollerState() {
    switch(mRollerEnumState) {
      case S_Empty:
        Empty();
        break;
      case S_IntakeAlgae:
        IntakeAlgae();
        break;
      case S_Eject:
        Eject();
        break;
    }
  }

  public void Empty() {
    //placeholder
  }

  public void IntakeAlgae() {
    //placeholder
  }

  public void Eject() {
    //placeholder
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
