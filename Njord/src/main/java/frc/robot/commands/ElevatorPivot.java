// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Constants.PivotConstants;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Pivot;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ElevatorPivot extends SequentialCommandGroup {

private int Level;


 
   /** Creates a new ElevatorPivot. */
  public ElevatorPivot(int level) {
    this.Level = level;

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands( 
    new PivotLevelsCommand(Level),
    new PivotCheck(Pivot.PivotArray[Level - 1]),
    new ElevatorLevelsCommand(Level),
    new ElevatorCheck(Elevator.ElevatorArray[Level - 1])
    );
   
  }
}
