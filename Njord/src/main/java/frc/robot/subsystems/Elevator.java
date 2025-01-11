// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

public class Elevator extends SubsystemBase {
  /** Creates a new Elevator. */

  private TalonFX elevatorMotor1;
  private TalonFX elevatorMotor2;
  public ElevatorEnumState mElevatorEnumState;

  public enum ElevatorEnumState {
    S_Bottom, S_L1, S_L2, S_L3, S_L4
  }
  //not sure if there should be more states, but we have 6 at least

  public Elevator() {
    elevatorMotor1 = new TalonFX(Constants.ElevatorConstants.kElevatorMotor1);
    elevatorMotor2 = new TalonFX(Constants.ElevatorConstants.kElevatorMotor2);
    elevatorMotor2.setControl(new Follower(Constants.ElevatorConstants.kElevatorMotor1, true));
    ElevatorEnumState = mElevatorEnumState.S_Bottom;
  }

  public void runElevatorState() {
    switch (mElevatorEnumState) {
      case S_Bottom:
        Bottom();
        break;
      case S_L1:
        L1();
        break;
      case S_L2:
        L2();
        break;
      case S_L3:
        L3();
        break;
      case S_L4:
        L4();
        break;
    }
  }
  //state not finished, needs more work

  public void Bottom() {
    //placeholder
  }

  public void L1() {
    //placeholder
  }

  public void L2() {
    //placeholder
  }

  public void L3() {
    //placeholder
  }

  public void L4() {
    //placeholder
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
