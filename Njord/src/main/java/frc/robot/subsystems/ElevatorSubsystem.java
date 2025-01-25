
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSubsystem extends SubsystemBase {
  private TalonFX elevatorMotor1;
  private TalonFX elevatorMotor2;
  public static int TargetLevel;
  private int Counter = 0; 
private DigitalInput MagLimit = new DigitalInput(0);
  public ElevatorState mElevatorState;

  public enum ElevatorState {
    S_GoingUp, S_GoingDown, S_Stopped
    }
  /** Creates a new ElevatorSubsystem. */
  public ElevatorSubsystem() {
    elevatorMotor1 = new TalonFX(Constants.ElevatorConstants.kElevatorMotor1);
    elevatorMotor2 = new TalonFX(Constants.ElevatorConstants.kElevatorMotor2);
    elevatorMotor2.setControl(new Follower(Constants.ElevatorConstants.kElevatorMotor1, true));

    mElevatorState = ElevatorState.S_Stopped;
  }

  public void runElevatorState(){
    switch (mElevatorState){
      case S_GoingUp:
      GoingUp();
      break;
      case S_GoingDown:
      GoingDown();
      break;
      case S_Stopped:
      Stopped();
      break;
    }
  }

  private void GoingUp(){
   if(MagLimit.get()){
    Counter++;
    mElevatorState = ElevatorState.S_Stopped;
   } else{
    elevatorMotor1.setVoltage(4);
   }
  }

  private void GoingDown(){
    if(MagLimit.get()){
      Counter--;
      mElevatorState = ElevatorState.S_Stopped;
     } else{
      elevatorMotor1.setVoltage(-4);
     }
  }

  private void Stopped(){
    if(TargetLevel > Counter){
    elevatorMotor1.setVoltage(4);
    if(!MagLimit.get()){
      mElevatorState = ElevatorState.S_GoingUp;
    }
    } else if (TargetLevel < Counter){
    elevatorMotor1.setVoltage(-4);
    if(!MagLimit.get()){
      mElevatorState = ElevatorState.S_GoingDown;
    }
    } else{
      elevatorMotor1.setVoltage(0);
    }

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }
}
