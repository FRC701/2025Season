// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

public class Elevator extends SubsystemBase {
  /** Creates a new Elevator. */

  private TalonFX elevatorMotor1;
  private TalonFX elevatorMotor2;

  public ElevatorEnumState mElevatorEnumState;

  private int ElevatorLevel;
  public static int TargetLevel;

  //future magnetic limit outputs (idk how i do that)
  private DigitalInput Limit0 = new DigitalInput(0);
  private DigitalInput Limit1 = new DigitalInput(1);
  private DigitalInput Limit2 = new DigitalInput(2);
  private DigitalInput Limit3 = new DigitalInput(3);
  private DigitalInput Limit4 = new DigitalInput(4);

  public enum ElevatorEnumState {
    S_Bottom, S_L1, S_L2, S_L3, S_L4
  }
  //not sure if there should be more states, but we have 6 at least

  public Elevator() {
    elevatorMotor1 = new TalonFX(Constants.ElevatorConstants.kElevatorMotor1);
    elevatorMotor2 = new TalonFX(Constants.ElevatorConstants.kElevatorMotor2);
    elevatorMotor2.setControl(new Follower(Constants.ElevatorConstants.kElevatorMotor1, true));
    mElevatorEnumState = ElevatorEnumState.S_Bottom;

    CheckElevatorLevel();
    TargetLevel = ElevatorLevel;

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
    if (TargetLevel == ElevatorLevel){
    elevatorMotor1.set(0);
  } else if(TargetLevel >= ElevatorLevel){
    MoveUp();
  } else if(TargetLevel <= ElevatorLevel){
    //makes sure elevator stops at bottom
    elevatorMotor1.set(0);
  }
    //placeholder
  }

  public void L1() {
    ElevatorLogic();
    //placeholder
  }

  public void L2() {
    ElevatorLogic();
    //placeholder
  }

  public void L3() {
    ElevatorLogic();
    //placeholder
  }

  public void L4() {
    if (TargetLevel == ElevatorLevel){
      elevatorMotor1.set(0);
    } else if(TargetLevel >= ElevatorLevel){
      //makes sure elevator stops at top
      elevatorMotor1.set(0);
    } else if(TargetLevel <= ElevatorLevel){
      MoveDown();
    }
    //placeholder
  }

  private void MoveUp(){
    elevatorMotor1.set(0.2);
  }

  private void MoveDown(){
    elevatorMotor1.set(-0.2);
  }

  //move elevator to target level
  private void ElevatorLogic(){
    if (TargetLevel == ElevatorLevel){
      elevatorMotor1.set(0);
    } else if(TargetLevel >= ElevatorLevel){
      MoveUp();
    } else if(TargetLevel <= ElevatorLevel){
      MoveDown();
    }
  }

  //switch states bases on limit switches
  private void CheckElevatorLevel(){
    if(Limit0.get()){
      ElevatorLevel = 0;
      mElevatorEnumState = ElevatorEnumState.S_Bottom;
    } else if(Limit1.get()){
      mElevatorEnumState = ElevatorEnumState.S_L1;
      ElevatorLevel = 1;
    } else if(Limit2.get()){
      mElevatorEnumState = ElevatorEnumState.S_L2;
      ElevatorLevel = 2;
    } else if(Limit3.get()){
      mElevatorEnumState = ElevatorEnumState.S_L3;
      ElevatorLevel = 3;
    } else if(Limit4.get()){
      mElevatorEnumState = ElevatorEnumState.S_L4;
      ElevatorLevel = 4;
    }
  }

  @Override
  public void periodic() {
    CheckElevatorLevel();
    runElevatorState();
    // This method will be called once per scheduler run
  }
}
