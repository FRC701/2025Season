// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.TiltPositions;
import frc.robot.subsystems.TiltPositions.TiltEnumState;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ChangeTilt extends InstantCommand {
  private TiltPositions m_tiltPositions;
  private String button;
  public ChangeTilt(TiltPositions m_tiltPositions, String button) {
    this.m_tiltPositions = m_tiltPositions;
    this.button = button;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.m_tiltPositions);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    switch(button){
      case "highButton":
        TiltPositions.mTiltEnumState = TiltEnumState.S_High;
        break;
      case "midButton":
        TiltPositions.mTiltEnumState = TiltEnumState.S_Mid;
        break;
      case "lowButton":
      TiltPositions.mTiltEnumState = TiltEnumState.S_Low;
        break;
    }
  }
}
