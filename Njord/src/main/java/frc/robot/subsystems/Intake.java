// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private TalonFX m_IntakeMotor;
  public static boolean enableRollers;
  public static Timer outtakeTimer;
  
  public static IntakeState intakeState;

  /** Creates a new Intake. */
  public Intake() {
    enableRollers = false;
    intakeState = IntakeState.S_Stopped;
    m_IntakeMotor = new TalonFX(31);
    outtakeTimer = new Timer();
  }

  public enum IntakeState {
   S_Stopped, S_Rolling, S_Outtake
    }

  public void RunIntakeStates(){
    switch (intakeState){
      case S_Stopped:
      Stopped();
      break;
      case S_Rolling:
      Rolling();
      break;
      case S_Outtake:
      Outtake();
      break;
    }
  }

  public void Stopped(){
    enableRollers = false;
    m_IntakeMotor.setVoltage(0);
  }

  public void Rolling(){
    m_IntakeMotor.setVoltage(-1);
    if(HasCoral()){
      intakeState = IntakeState.S_Stopped;
    }
  }

  public void Outtake(){
    m_IntakeMotor.setVoltage(4);
    outtakeTimer.start();
    if(outtakeTimer.hasElapsed(0.5)){
      intakeState = IntakeState.S_Stopped;
      outtakeTimer.stop();
      outtakeTimer.reset();
    }
  }

    public boolean HasCoral() {
      if (m_IntakeMotor.getStatorCurrent().getValueAsDouble() > 10.0 ) {
        return true;
      } 
      else {
        return false;
      }
    }



  @Override
  public void periodic() {
    RunIntakeStates();
    SmartDashboard.putNumber("IntakeStatorCurrent", m_IntakeMotor.getStatorCurrent().getValueAsDouble());
    SmartDashboard.putBoolean("HasCoral", HasCoral());
    SmartDashboard.putBoolean("rollersenabled", enableRollers);

    SmartDashboard.putString("getState", intakeState.toString());
    // This method will be called once per scheduler run
  }
}
