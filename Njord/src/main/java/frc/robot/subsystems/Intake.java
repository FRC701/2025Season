// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
//import com.ctre.phoenix6.controls.Follower;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */

  private TalonFX intakeMotor1;
  public static boolean kIntakeMotor_current;
  public static IntakeEnumState mIntakeEnumState;
  private static boolean hasCoral;

  public enum IntakeEnumState {
    S_Empty, S_Loaded, S_IntakeEject
  }

  public Intake() {
    intakeMotor1 = new TalonFX(Constants.IntakeConstants.kIntakeMotor1);
    hasCoral = false;
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
    //if (coralChecker) {
      if(hasCoral()){
        intakeMotor1.setVoltage(1);
      } else {
        intakeMotor1.setVoltage(0);
      }
    }
      
        //Intake.mIntakeEnumState = IntakeEnumState.S_Loaded;
      
    //}
  

  public void Loaded() {
    intakeMotor1.setVoltage(0);
  }

  public void IntakeEject() {
    intakeMotor1.setVoltage(4);
    Intake.mIntakeEnumState = IntakeEnumState.S_Empty;
  }

  public boolean hasCoral() {
    return true;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    runIntakeState();
    SmartDashboard.putBoolean("hasCoral", hasCoral);
    SmartDashboard.putString("IntakeState", mIntakeEnumState.toString());
 




  }
}
