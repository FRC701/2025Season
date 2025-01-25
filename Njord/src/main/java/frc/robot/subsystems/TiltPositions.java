// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
//import frc.robot.Constants;
import frc.robot.Constants.TiltConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitValue;
//import com.ctre.phoenix6.signals.ReverseLimitValue;

public class TiltPositions extends SubsystemBase {
  private TalonFX tiltMotor;
  private PIDController m_pidcontroller; 
  public static TiltEnumState mTiltEnumState;

  /** Creates a new TiltPositions. */
  public TiltPositions() {
    tiltMotor = new TalonFX(TiltConstants.kTiltMotor);
    m_pidcontroller = new PIDController(TiltConstants.kP, TiltConstants.kI, TiltConstants.kD);
  }

  public enum TiltEnumState {
    S_ResetPosition, S_High, S_Mid, S_Low
  }

  public void runTiltState() {
    switch(mTiltEnumState) {
      case S_ResetPosition:
        resetPosition();
        break;
      case S_High:
        changePosition(TiltConstants.HighPosition);
        break;
      case S_Mid:
        changePosition(TiltConstants.MidPosition);
        break;
      case S_Low:
        changePosition(TiltConstants.LowPosition);
        break;
    }
  }

  public void changePosition(double degrees){
    double output = m_pidcontroller.calculate(tiltMotor.getPosition().getValueAsDouble(), degrees);
    tiltMotor.setVoltage(output);
  }

  public void resetPosition(){
    tiltMotor.setVoltage(1);
    if(atStart()){
      tiltMotor.setVoltage(0);
      tiltMotor.setPosition(0);
    }
  }

  public boolean atStart(){
    return tiltMotor.getForwardLimit().getValue() == ForwardLimitValue.ClosedToGround;
  }

  /*
  public boolean atEnd(){
    return tiltMotor.getReverseLimit().getValue() == ReverseLimitValue.ClosedToGround;
  }
  */

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    runTiltState();
  }
}
