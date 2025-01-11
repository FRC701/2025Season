// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.Follower;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */

  private TalonFX intakeMotor1;
  private TalonFX intakeMotor2;
  public IntakeEnumState mIntakeEnumState;

  public enum IntakeEnumState {
    S_Empty, S_IntakeRun, S_IntakeEject
  }

  public Intake() {
    intakeMotor1 = new TalonFX(Constants.IntakeConstants.kIntakeMotor1);
    intakeMotor2 = new TalonFX(Constants.IntakeConstants.kIntakeMotor2);
    intakeMotor2.setControl(new Follower(Constants.IntakeConstants.kIntakeMotor1, false));
  }

  public void runIntakeState() {
    switch(mIntakeEnumState) {
      case S_Empty:
        Empty();
        break;
      case S_IntakeRun:
        IntakeRun();
        break;
      case S_IntakeEject:
        IntakeEject();
        break;
    }
  }

  public void Empty() {
    intakeMotor1.setVoltage(0);
    intakeMotor2.setVoltage(0);
  }

  public void IntakeRun() {
    intakeMotor1.set(0.1 /*placeholder*/);
    intakeMotor2.set(0.1);
  }

  public void IntakeEject() {
    intakeMotor1.set(0.1 /*placeholder*/);
    intakeMotor2.set(0.1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
