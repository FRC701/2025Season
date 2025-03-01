// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.StaticBrake;
import com.ctre.phoenix6.signals.ControlModeValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.ReverseLimitValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorConstants;


public class Elevator extends SubsystemBase {
  /** Creates a new Elevator. */
  private final TalonFX m_elevatorMotor;
  private final TalonFX m_elevatorMotorf;

  private final double GearRatio = 9/1;

  private final double kSproketCircumfrence = 1.887829 * Math.PI;

  private final double kFloorToCarriage = 19.876;

  private TalonFXConfiguration mTalonFXConfig;

  public static ElevatorState mElevatorState;


  public Elevator() {

   mElevatorState = ElevatorState.S_Reset;

    m_elevatorMotor = new TalonFX(ElevatorConstants.kElevatorMotor,"cani");
    m_elevatorMotorf = new TalonFX(ElevatorConstants.kElevatorMotor2, "cani");

    var fx_cfg = new MotorOutputConfigs();

    fx_cfg.NeutralMode = NeutralModeValue.Brake;
  
    m_elevatorMotor.getConfigurator().apply(fx_cfg);
    m_elevatorMotorf.getConfigurator().apply(fx_cfg);

        mTalonFXConfig = new TalonFXConfiguration().withVoltage(new VoltageConfigs()
        .withPeakForwardVoltage(4)
        .withPeakReverseVoltage(-4));

        m_elevatorMotor.getConfigurator().apply(mTalonFXConfig);
        m_elevatorMotorf.getConfigurator().apply(mTalonFXConfig);



     var Slot0Configs = new Slot0Configs();
    Slot0Configs.GravityType = GravityTypeValue.Elevator_Static;
    Slot0Configs.kS = 0;
    Slot0Configs.kG = 0.3;
    Slot0Configs.kP = 1;
    Slot0Configs.kI = 0;
    Slot0Configs.kD = 0;

    m_elevatorMotor.getConfigurator().apply(Slot0Configs, 0.05);


     m_elevatorMotorf.setControl(new Follower(ElevatorConstants.kElevatorMotor, true));
  }

  public enum ElevatorState {
    S_Reset,S_L1, S_L2, S_L3, S_L4, S_PickUp

  }

  public void runElevatorState(){
    switch (mElevatorState){
      case S_Reset:
      resetPosition();
      break;
      case S_L1:
      setPosition(ElevatorConstants.kLevel1Height);
      break;
      case S_L2:
      setPosition(ElevatorConstants.kLevel2Height); //placeholder
      break;
      case S_L3:
      setPosition(ElevatorConstants.kLevel3Height); //placeholder
      break;
      case S_L4:
      setPosition(ElevatorConstants.kLevel4Height); //placeholder
      break;
      case S_PickUp:
      setPosition(ElevatorConstants.kLevel5Height);
    }
  }
    

  public double rotationsToInches(double angle){
    return angle * kSproketCircumfrence;
  }

  public double getPosition(){
    return rotationsToInches(m_elevatorMotor.getPosition().getValueAsDouble() / GearRatio );
  }

  public void resetPosition(){ 
    if(atBottom()){
      m_elevatorMotor.setPosition(0.0);
      stop();
    }
    else {
      m_elevatorMotor.setVoltage(-1);
    }
  }

  public void setPosition(double position){
    PositionVoltage pos = new PositionVoltage(position).withSlot(0);
    m_elevatorMotor.setControl(pos);
  }

  public void stop(){
    m_elevatorMotor.setVoltage(0);
  }


  public void SpinPositive(){
    m_elevatorMotor.setVoltage(1);
  }

  public void SpinNegative(){
    m_elevatorMotor.setVoltage(-1);
  }

  public boolean atBottom(){
    return m_elevatorMotor.getReverseLimit().getValue() == ReverseLimitValue.ClosedToGround;
  }

  public double inchesToRotations(double Height){
    return (Height *GearRatio)/ kSproketCircumfrence ;
  }

  public boolean SetpointReached(double Setpoint){
   return (getPosition() + 1 <= Setpoint) && (getPosition() - 1 >=Setpoint); 
  }


  @Override
  public void periodic() {
   runElevatorState();
    SmartDashboard.putString("ElevatorState", mElevatorState.toString());
    SmartDashboard.putNumber("getRaw", m_elevatorMotor.getRotorPosition().getValueAsDouble());

    SmartDashboard.putNumber("isConfig", m_elevatorMotor.getDeviceID());
    // setPosition(inchesToRotations(SmartDashboard.getNumber("DesiredHeight", 0)));
    SmartDashboard.putNumber("Height", getPosition());
    // This method will be called once per scheduler run
  }
}
