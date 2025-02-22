// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;



import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
  public TalonFX ClimbMotorL;
  private TalonFX ClimbMotorR;

  /** Creates a new Climb. */
  public Climber() {
    ClimbMotorL = new TalonFX(Constants.ClimberConstants.kClimbLeft);
    ClimbMotorR = new TalonFX(Constants.ClimberConstants.kClimbRight);
    
    ClimbMotorR.setControl(new Follower(Constants.ClimberConstants.kClimbLeft,
     true));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
