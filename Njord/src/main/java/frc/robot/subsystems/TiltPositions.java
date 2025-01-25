// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;

public class TiltPositions extends SubsystemBase {
  private TalonFX tiltMotor;
  /** Creates a new TiltPositions. */
  public TiltPositions() {
    tiltMotor = new TalonFX(Constants.TiltConstants.kTiltMotor);
  }

  public void changePosition(double degrees){

  }

  public void resetPosition(){

  }

  public boolean atStart(){
    return false;
  }

  public boolean atEnd(){
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
