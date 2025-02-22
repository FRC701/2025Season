// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.StaticBrake;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ControlModeValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Elevator extends SubsystemBase {
  /** Creates a new Elevator. */
  private final TalonFX m_elevatorMotor;
  private final TalonFX m_elevatorMotorf;

  private final double GearRatio = 20/1;

  private final double kSproketCircumfrence = 1.887829 * Math.PI;

  private final double kFloorToCarriage = 19.876;

  private TalonFXConfiguration mTalonFXConfig;

  public static ElevatorState mElevatorState;


  public Elevator() {
    m_elevatorMotor = new TalonFX(Constants.ElevatorConstants.kElevatorMotor,"cani");
    m_elevatorMotorf = new TalonFX(Constants.ElevatorConstants.kElevatorMotor2, "cani");

    var fx_cfg = new MotorOutputConfigs();

    fx_cfg.NeutralMode = NeutralModeValue.Brake;
  
    m_elevatorMotor.getConfigurator().apply(fx_cfg);
    m_elevatorMotorf.getConfigurator().apply(fx_cfg);

        mTalonFXConfig = new TalonFXConfiguration().withVoltage(new VoltageConfigs()
        .withPeakForwardVoltage(4)
        .withPeakReverseVoltage(-4));



     var Slot0Configs = new Slot0Configs();
    Slot0Configs.GravityType = GravityTypeValue.Elevator_Static;
    Slot0Configs.kS = 0;
    Slot0Configs.kG = 0;
    Slot0Configs.kP = 1;
    Slot0Configs.kI = 0;
    Slot0Configs.kD = 0;

    m_elevatorMotor.getConfigurator().apply(Slot0Configs, 0.05);

     m_elevatorMotorf.setControl(new Follower(Constants.ElevatorConstants.kElevatorMotor, true));
  }
  public void runElevatorState(){
    switch (mElevatorState){
      case S_L1:
      S_L1();
      break;
      case S_L2:
      S_L2();
      break;
      case S_L3:
      S_L3();
      break;
      case S_L4:
      S_L4();
      break;




      }
    }
  

  
  public enum ElevatorState {
    S_L1, S_L2, S_L3, S_L4
  }
  private void S_L1(){
    setPositon(0); 
  }
  private void S_L2(){
    setPositon(2); //placeholder value
  }
  private void S_L3(){
    setPositon(2); //placeholder
  }
  private void S_L4(){
    setPositon(2); //placeholder
  }
  public double rotationsToInches(double angle){
    return angle * kSproketCircumfrence;
  }

  public double getPosition(){
    return rotationsToInches(m_elevatorMotor.getPosition().getValueAsDouble() / GearRatio );
  }

  public void resetPosition(){
    m_elevatorMotor.setPosition(0);
    
  }

  public void setPosition(double position){
    PositionVoltage pos = new PositionVoltage(position).withSlot(0);
    m_elevatorMotor.setControl(pos);
  }

  public void stop(){
    m_elevatorMotor.setVoltage(0);
  }

  @Override
  public void periodic() {
    runElevatorState();
    SmartDashboard.putNumber("getRaw", m_elevatorMotor.getRotorPosition().getValueAsDouble());

    SmartDashboard.putNumber("isConfig", m_elevatorMotor.getDeviceID());
    setPosition(SmartDashboard.getNumber("DesiredHeight", 0));
    SmartDashboard.putNumber("Height", getPosition());
    // This method will be called once per scheduler run
  }
}
