// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AlgaeIntake extends SubsystemBase {
  private TalonFX AlgaeIntake;/** Creates a new AlgaeIntake. */
  
  public static AlgaeEnumState mAlgaeEnumState; 
  public enum AlgaeEnumState {S_Full, S_Empty, S_Shoot}
  
  public AlgaeIntake() {
    AlgaeIntake = new TalonFX(Constants.AlgaeIntakeConstants.kAlgaeIntakeMotor);
    mAlgaeEnumState = AlgaeEnumState.S_Empty;
  }
 public void runAlgaeIntake(){
 switch(mAlgaeEnumState){
  case S_Empty:
  Empty();
  break;
  case S_Full:
  Full();
  break;
  case S_Shoot:
  Shoot();
  break;
  }
 }
  public void Empty(){
  AlgaeIntake.setVoltage(-4);
  }

  public void Full() {
  if(hasAlgae()) {
   AlgaeIntake.setVoltage(0);
  }
  
  }

  public void Shoot() {
    AlgaeIntake.setVoltage(4);
  }
  public boolean hasAlgae(){
    if(AlgaeIntake.getStatorCurrent().getValueAsDouble()> 4){
      return true;
    }else{
      return false;
    }
  }














 

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("hasAlgae", hasAlgae());
    // This method will be called once per scheduler run
  }
}
