// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;



import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ReverseLimitValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
  private TalonFX ClimbMotorL;
  private TalonFX ClimbMotorR;

  public static climberState mClimberstate;

  private TalonFXConfiguration mTalonFXConfig;

  /** Creates a new Climb. */
  public Climber() {
    ClimbMotorL = new TalonFX(Constants.ClimberConstants.kClimbLeft, "cani");
    ClimbMotorR = new TalonFX(Constants.ClimberConstants.kClimbRight, "cani");
    
    ClimbMotorR.setControl(new Follower(Constants.ClimberConstants.kClimbLeft,
     true));

    mTalonFXConfig = new TalonFXConfiguration().withVoltage((new VoltageConfigs()
    .withPeakForwardVoltage(7)
    .withPeakReverseVoltage(-7)));

    ClimbMotorL.getConfigurator().apply(mTalonFXConfig);
    ClimbMotorR.getConfigurator().apply(mTalonFXConfig);

  
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
ClimbMotorL.setVoltage(7);
}

private void HooksDown(){
ClimbMotorL.setVoltage(-7);
}

private void Stopped(){
ClimbMotorL.setVoltage(0);
}

private void Reset(){
if(!RevLimit()){
  ClimbMotorL.setVoltage(-7);
} else{
  ClimbMotorL.setVoltage(0);
}
}

private boolean RevLimit(){
  return ClimbMotorL.getReverseLimit().getValue() == ReverseLimitValue.ClosedToGround;
}


  @Override
  public void periodic() {
    runClimberState();
    SmartDashboard.putString("ClimberState", mClimberstate.toString());
    // This method will be called once per scheduler run
  }
}
