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
import com.ctre.phoenix6.signals.ReverseLimitValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Pivot extends SubsystemBase {
  /** Creates a new Elevator. */
  private final TalonFX m_PivotMotor;

  private TalonFXConfiguration mTalonFXConfig;

  public static PivotState pivotState;

  public Pivot() {

    pivotState = PivotState.S_Reset;

    m_PivotMotor = new TalonFX(Constants.PivotConstants.kPivotMotor, "cani");

    var fx_cfg = new MotorOutputConfigs();

    fx_cfg.NeutralMode = NeutralModeValue.Brake;

    mTalonFXConfig = new TalonFXConfiguration().withVoltage(new VoltageConfigs()
        .withPeakForwardVoltage(1)
        .withPeakReverseVoltage(-1));

    m_PivotMotor.getConfigurator().apply(fx_cfg);

    m_PivotMotor.getConfigurator().apply(mTalonFXConfig);

    var Slot0Configs = new Slot0Configs();
    Slot0Configs.GravityType = GravityTypeValue.Elevator_Static;
    Slot0Configs.kS = 0;
    Slot0Configs.kG = 0;
    Slot0Configs.kP = 1;
    Slot0Configs.kI = 0;
    Slot0Configs.kD = 0;

    m_PivotMotor.getConfigurator().apply(Slot0Configs, 0.05);

  }

  public enum PivotState {
    S_Reset, S_L1, S_L2, S_L3, S_L4, S_PickUp

  }

  public void runElevatorState() {
    switch (pivotState) {
      case S_Reset:
        resetPosition();
        break;
      case S_L1:
        setPosition(0);
        break;
      case S_L2:
        setPosition(10); // placeholder
        break;
      case S_L3:
        setPosition(20); // placeholder
        break;
      case S_L4:
        setPosition(27); // placeholder
        break;
      case S_PickUp:
        setPosition(0);
        break;
    }
  }

  public void resetPosition() {
    m_PivotMotor.setVoltage(-1);
    if (atBottom()) {
      m_PivotMotor.setPosition(0.0);
      stop();
    }
  }

  public void setPosition(double position) {
    PositionVoltage pos = new PositionVoltage(position).withSlot(0);
    m_PivotMotor.setControl(pos);
  }

  public void stop() {
    m_PivotMotor.setVoltage(0);
  }

  public boolean atBottom() {
    return m_PivotMotor.getReverseLimit().getValue() == ReverseLimitValue.ClosedToGround;
  }

  public void SpinPositive() {
    m_PivotMotor.setVoltage(1);
  }

  public void SpinNegative() {
    m_PivotMotor.setVoltage(-1);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("getRawPivot", m_PivotMotor.getRotorPosition().getValueAsDouble());

    SmartDashboard.putNumber("isConfigPivot", m_PivotMotor.getDeviceID());
    // setPosition(SmartDashboard.getNumber("DesiredPivot", 0));
    // This method will be called once per scheduler run
  }
}
