// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Elevator.ElevatorState;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Pivot.PivotState;;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ElevatorLevelsCommand extends InstantCommand {
  int level = 0;
  public ElevatorLevelsCommand(int level) {
    this.level = level;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    switch(level){
      case 1:
        Elevator.mElevatorState = ElevatorState.S_L1;
                Pivot.pivotState = PivotState.S_L1;
        break;
      case 2:
        Elevator.mElevatorState = ElevatorState.S_L2;
        Pivot.pivotState = PivotState.S_L2;

        break;
      case 3:
        Elevator.mElevatorState = ElevatorState.S_L3;
        Pivot.pivotState = PivotState.S_L3;

        break;
      case 4:
        Elevator.mElevatorState = ElevatorState.S_L4;
        Pivot.pivotState = PivotState.S_L4;

        break;
        case 5:
        Elevator.mElevatorState = ElevatorState.S_PickUp;

        Pivot.pivotState = PivotState.S_PickUp;
        break;
    }
  }
}
