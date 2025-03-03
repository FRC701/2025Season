
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ElevatorLevelsCommand;
import frc.robot.commands.EnableRollers;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.Elevator;
import frc.robot.commands.Outtake;
import frc.robot.commands.PivotLevelsCommand;
import frc.robot.commands.ReversePivot;
import frc.robot.commands.RunPivot;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.commands.ClimbCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */

// All code besides import goes into this
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final Elevator m_elevator = new Elevator();
  private final Pivot m_pivot = new Pivot();
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Climber m_climber = new Climber();
  private final Intake m_intakeSubsytem = new Intake();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController Driver = new CommandXboxController(
      Constants.OperatorConstants.kDriverControllerPort);

  private final CommandXboxController CoDriver = new CommandXboxController(OperatorConstants.kCoDriverControllerPort);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  // Constructor
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */

  // Binds Buttons To Actions
  private void configureBindings() {

    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is
    // pressed,
    // cancelling on release.

    CoDriver.x().onTrue(new ElevatorLevelsCommand(1));
    CoDriver.a().onTrue(new ElevatorLevelsCommand(2));
    CoDriver.y().onTrue(new ElevatorLevelsCommand(3));
    CoDriver.b().onTrue(new ElevatorLevelsCommand(4));
    CoDriver.leftBumper().onTrue(new ElevatorLevelsCommand(5));

    Driver.y().whileTrue(new ClimbCommand(m_climber, 7));
    Driver.a().whileTrue(new ClimbCommand(m_climber, -7));

    Driver.x().whileTrue(new EnableRollers());
    Driver.b().whileTrue(new Outtake());

    // Driver.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
    // Driver.y().whileTrue(new RunPivot());
    // Driver.a().whileTrue(new ReversePivot());

    // Driver.x().onTrue(new PivotLevelsCommand(1));
    // Driver.a().onTrue(new PivotLevelsCommand(2));
    // Driver.y().onTrue(new PivotLevelsCommand(3));
    // Driver.b().onTrue(new PivotLevelsCommand(4));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

  // For Auto
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
