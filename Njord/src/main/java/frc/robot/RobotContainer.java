// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ElevateCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.commands.ClimbCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.Climber;
//import frc.robot.commands.ExampleCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */

//All code besides import goes into this
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final ElevatorSubsystem mElevator = new ElevatorSubsystem();
  private final Climber mClimber = new Climber();
  private final Intake m_intakeSubsytem = new Intake();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController Driver =
      new CommandXboxController(Constants.OperatorConstants.kDriverControllerPort);

  private final CommandXboxController CoDriver =
      new CommandXboxController(OperatorConstants.kCoDriverControllerPort);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  //Constructor
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }


  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */


   //Binds Buttons To Actions
  private void configureBindings() {
    /*
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.

    Driver.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

    CODriver.povCenter().onTrue(new ElevateCommand(mElevator, 0));
    CODriver.a().onTrue(new ElevateCommand(mElevator, 1));
    CODriver.x().onTrue(new ElevateCommand(mElevator, 2));
    CODriver.b().onTrue(new ElevateCommand(mElevator, 3));
    CODriver.y().onTrue(new ElevateCommand(mElevator, 4));
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

    CODriver.a().whileTrue(new ClimbCommand(mClimber, 0.7));
    */
    Driver.rightBumper().onTrue(new IntakeCommand(m_intakeSubsytem));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */


   //For Auto
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
