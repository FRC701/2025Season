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


public class Pivot extends SubsystemBase {
  /** Creates a new Elevator. */
  private final TalonFX m_PivotMotor;

  private final double GearRatio = 20/1;

  private final double kSproketCircumfrence = 1.887829 * Math.PI;

  private final double kFloorToCarriage = 19.876;

  private TalonFXConfiguration mTalonFXConfig;


  public Pivot() {
    m_PivotMotor = new TalonFX(Constants.PivotConstants.kPivotMotor, "cani");


    var fx_cfg = new MotorOutputConfigs();

    fx_cfg.NeutralMode = NeutralModeValue.Brake;

    mTalonFXConfig = new TalonFXConfiguration().withVoltage(new VoltageConfigs()
    .withPeakForwardVoltage(1)
    .withPeakReverseVoltage(-1));
  
    m_PivotMotor.getConfigurator().apply(fx_cfg);


     var Slot0Configs = new Slot0Configs();
    Slot0Configs.GravityType = GravityTypeValue.Elevator_Static;
    Slot0Configs.kS = 0;
    Slot0Configs.kG = 0;
    Slot0Configs.kP = 1;
    Slot0Configs.kI = 0;
    Slot0Configs.kD = 0;

    m_PivotMotor.getConfigurator().apply(Slot0Configs, 0.05);

  }

  public double rotationsToInches(double angle){
    return angle * kSproketCircumfrence;
  }

  public double getPosition(){
    return rotationsToInches(m_PivotMotor.getPosition().getValueAsDouble() / GearRatio );
  }

  public void resetPosition(){
    m_PivotMotor.setPosition(0);
    
  }

  public void setPosition(double position){
    PositionVoltage pos = new PositionVoltage(position).withSlot(0);
    m_PivotMotor.setControl(pos);
  }

  public void stop(){
    m_PivotMotor.setVoltage(0);
  }

  public void SpinPositive(){
    m_PivotMotor.setVoltage(1);
  }

  public void SpinNegative(){
    m_PivotMotor.setVoltage(-1);
  }
  @Override
  public void periodic() {
    SmartDashboard.putNumber("getRawPivot", m_PivotMotor.getRotorPosition().getValueAsDouble());

    SmartDashboard.putNumber("isConfigPivot", m_PivotMotor.getDeviceID());
    setPosition(SmartDashboard.getNumber("DesiredPivot", 0));
    SmartDashboard.putNumber("HeightPivot", getPosition());
    // This method will be called once per scheduler run
  }
}
