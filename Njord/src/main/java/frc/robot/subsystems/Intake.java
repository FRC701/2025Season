// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */

  private TalonFX intakeMotor;
  public static boolean kIntakeMotor_current;
  public static IntakeEnumState mIntakeEnumState;

  public enum IntakeEnumState {
    S_Empty, S_Loaded, S_IntakeEject
  }

  public Intake() {
    intakeMotor = new TalonFX(Constants.IntakeConstants.kIntakeMotor);
    mIntakeEnumState = IntakeEnumState.S_Empty;
  }
 
  

  public void runIntakeState() {
    switch(mIntakeEnumState) {
      case S_Empty:
        Empty();
        break;
      case S_Loaded:
        Loaded();
        break;
      case S_IntakeEject:
        IntakeEject();
        break;
    }
  }

  public void Empty() {
      if(!hasCoral()){
        intakeMotor.setVoltage(1);
      }
      else {
        Intake.mIntakeEnumState = IntakeEnumState.S_Loaded;
      }
  }
  

  public void Loaded() {
    intakeMotor.setVoltage(0);
  }

  public void IntakeEject() {
    intakeMotor.setVoltage(4);
    //Intake.mIntakeEnumState = IntakeEnumState.S_Empty;
  }

  public boolean hasCoral() {
    if (intakeMotor.getStatorCurrent().getValueAsDouble() > 5.0 /*placeholder value, will change later*/) {
      return true;
    } 
    else {
      return false;
    }
  }
  
  


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    runIntakeState();
    SmartDashboard.putBoolean("hasCoral", hasCoral());
    SmartDashboard.putString("IntakeState", mIntakeEnumState.toString());
    




  }
}
