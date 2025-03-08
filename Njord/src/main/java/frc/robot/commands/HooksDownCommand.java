// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Climber.climberState;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class HooksDownCommand extends Command {
  private Climber mClimber;

  /** Creates a new ClimbCommand. */
  public HooksDownCommand(Climber mClimber) {
    this.mClimber = mClimber;

    addRequirements(this.mClimber);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Climber.mClimberstate = climberState.S_HooksDown;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Climber.mClimberstate = climberState.S_Stopped;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; 
  }
}
