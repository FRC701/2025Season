// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.ReverseLimitValue;
import frc.robot.subsystems.Elevator.ElevatorState;

import edu.wpi.first.units.measure.Power;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private TalonFX m_IntakeMotor;
  public static boolean enableRollers;
  public static Timer outtakeTimer;

  public static IntakeState intakeState;
  private final TalonFXConfiguration mTalonFXConfig;


  /** Creates a new Intake. */
  public Intake() {
    enableRollers = false;
    intakeState = IntakeState.S_Rolling;
    m_IntakeMotor = new TalonFX(Constants.IntakeConstants.kIntakeMotor1, "cani");
    var Brake = new MotorOutputConfigs();
    outtakeTimer = new Timer();




    Brake.NeutralMode = NeutralModeValue.Brake;
    
    mTalonFXConfig = new TalonFXConfiguration().withMotorOutput(new MotorOutputConfigs()
    .withDutyCycleNeutralDeadband(0));
    m_IntakeMotor.getConfigurator().apply(Brake);
    m_IntakeMotor.getConfigurator().apply(mTalonFXConfig);
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
    m_IntakeMotor.stopMotor();
  }

  public void Rolling(){
  if(HasCoral()){
      intakeState = IntakeState.S_Stopped;
    } else{
      m_IntakeMotor.setVoltage(1);
     }
   }

  public void Outtake(){
    outtakeTimer.start();
    if(outtakeTimer.hasElapsed(0.5)){
      intakeState = IntakeState.S_Rolling;
      outtakeTimer.stop();
      outtakeTimer.reset();
    }else{
    if(Elevator.mElevatorState == ElevatorState.S_L4){
      m_IntakeMotor.setVoltage(2.5);
    }else if(Elevator.mElevatorState == ElevatorState.S_L1){
      m_IntakeMotor.setVoltage(2.5);
    }else{
      m_IntakeMotor.setVoltage(4);
    }
  }
}


    // if(HasCoral()){
  //     m_IntakeMotor.setVoltage(4);
  //   } 
  //   else{
  //     intakeState = IntakeState.S_Rolling;
  // //   }
    // }



    public boolean HasCoral() {
      // return m_IntakeMotor.getForwardLimit().getValue() == ForwardLimitValue.ClosedToGround;
      return m_IntakeMotor.getStatorCurrent().getValueAsDouble() > 30;
    }



  @Override
  public void periodic() {

    RunIntakeStates();
    SmartDashboard.putString("IntakeState", intakeState.toString());
    SmartDashboard.putNumber("IntakeStatorCurrent", m_IntakeMotor.getStatorCurrent().getValueAsDouble());
    SmartDashboard.putBoolean("HasCoral", HasCoral());
    SmartDashboard.putBoolean("rollersenabled", enableRollers);

    SmartDashboard.putString("getState", intakeState.toString());

  }
}
