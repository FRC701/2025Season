// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;



import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.ReverseLimitValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
  private TalonFX ClimbMotorBottom;
  private TalonFX ClimbMotorTop;

  public static climberState mClimberstate;

  private TalonFXConfiguration mTalonFXConfig;

  private static final double FORWARD_LIMIT = 1234.;

  /** Creates a new Climb. */
  public Climber() {
    ClimbMotorBottom = new TalonFX(Constants.ClimberConstants.kClimbBottom, "cani");
    ClimbMotorTop = new TalonFX(Constants.ClimberConstants.kClimbTop, "cani");
    
    ClimbMotorBottom.setControl(new Follower(Constants.ClimberConstants.kClimbTop,
     false));

     mClimberstate = climberState.S_Stopped;

     
    var fx_cfg = new MotorOutputConfigs();

    fx_cfg.NeutralMode = NeutralModeValue.Brake;
  
    ClimbMotorBottom.getConfigurator().apply(fx_cfg);
    ClimbMotorTop.getConfigurator().apply(fx_cfg);

    mTalonFXConfig = new TalonFXConfiguration().withVoltage((new VoltageConfigs()
    .withPeakForwardVoltage(7)
    .withPeakReverseVoltage(-7)))
    .withSoftwareLimitSwitch(
      new SoftwareLimitSwitchConfigs()
        .withForwardSoftLimitThreshold(FORWARD_LIMIT)
        .withForwardSoftLimitEnable(true));

    ClimbMotorBottom.getConfigurator().apply(mTalonFXConfig);
    ClimbMotorTop.getConfigurator().apply(mTalonFXConfig);

  
  }

  public enum climberState{
    S_HooksUp, S_HooksDown, S_Stopped, S_Reset
  }

  private void runClimberState(){
    switch(mClimberstate){
      case S_HooksUp:
      HooksUp();
      break;
      case S_HooksDown:
      HooksDown();
      break;
      case S_Stopped:
      Stopped();
      break;
      case S_Reset:
      Reset();
      break;
    }
  }

private void HooksUp(){
ClimbMotorTop.setVoltage(-6);
}

private void HooksDown(){
ClimbMotorTop.setVoltage(6);
}

private void Stopped(){
ClimbMotorTop.stopMotor();
}

private void Reset(){
if(FwdLimit()){
  ClimbMotorTop.setPosition(0);
  mClimberstate = climberState.S_Stopped;
} else{
  ClimbMotorTop.setVoltage(-4);
}
}

private double GetPose(){
  return ClimbMotorTop.getRotorPosition().getValueAsDouble();
}

private boolean FwdLimit(){
  return ClimbMotorTop.getForwardLimit().getValue() == ForwardLimitValue.ClosedToGround;
}


  @Override
  public void periodic() {
    runClimberState();
    SmartDashboard.putString("ClimberState", mClimberstate.toString());
    SmartDashboard.putNumber("ClimberPose", GetPose());
    SmartDashboard.putBoolean("FWDLimit down", FwdLimit());
    // This method will be called once per scheduler run
  }
}
