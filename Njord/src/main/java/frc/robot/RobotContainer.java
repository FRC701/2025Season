
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Telemetry;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.fasterxml.jackson.databind.util.Named;
import com.ctre.phoenix6.swerve.SwerveRequest;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.AutonomousSequencing;
import frc.robot.commands.Autos;
import frc.robot.commands.ClimbNOW;
import frc.robot.commands.ElevatorPivot;
import frc.robot.commands.EnableRollers;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.Elevator;
import frc.robot.commands.Outtake;
import frc.robot.commands.ActivateIntake;
import frc.robot.generated.TunerConstants;
import frc.robot.
subsystems.PoseEstimateFeed;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.commands.HooksDownCommand;
import frc.robot.commands.ResetClimber;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Intake;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

// import com.ctre.phoenix6.mechanisms.swerve.LegacySwerveModule.DriveRequestType;
// import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

  // PoseEstimateFeed m_PoseEstimateFeed = new PoseEstimateFeed();
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
    private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric()
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    private final Telemetry logger = new Telemetry(MaxSpeed);

    //private final CommandXboxController joystick = new CommandXboxController(0);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

       private final SendableChooser<Command> autoChooser;
  // The robot's subsystems and commands are defined here...

  private final Elevator m_elevator = new Elevator();
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Climber m_climber = new Climber();
  private final Intake m_intakeSubsytem = new Intake();


  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController Driver = new CommandXboxController(
      Constants.OperatorConstants.kDriverControllerPort);

  private final CommandXboxController CoDriver = new CommandXboxController(
    OperatorConstants.kCoDriverControllerPort);

  private final CommandXboxController Tuner = new CommandXboxController(
    OperatorConstants.kTunerControllerPort);


  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  // Constructor
  public RobotContainer() {
    
    NamedCommands.registerCommand("Level4", new ElevatorPivot(4));
    NamedCommands.registerCommand("Outtake", new Outtake());

       autoChooser = AutoBuilder.buildAutoChooser("Auto Straight Taxi");
        SmartDashboard.putData("Auto Mode", autoChooser);
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
     // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-Driver.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-Driver.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-Driver.getRightX() * MaxAngularRate * 0.75) // Drive counterclockwise with negative X (left)
            )
        );

        // joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        // joystick.b().whileTrue(drivetrain.applyRequest(() ->
        //     point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        // ));

        Driver.pov(270).whileTrue(drivetrain.applyRequest(() ->
            forwardStraight.withVelocityX(0.375).withVelocityY(0))
        );
        Driver.pov(90).whileTrue(drivetrain.applyRequest(() ->
            forwardStraight.withVelocityX(-0.375).withVelocityY(0))
        );
        Driver.pov(0).whileTrue(drivetrain.applyRequest(() ->
            forwardStraight.withVelocityX(0.0).withVelocityY(-0.375))
        );
        Driver.pov(180).whileTrue(drivetrain.applyRequest(() ->
            forwardStraight.withVelocityX(0.0).withVelocityY(0.375))
        );

        Driver.rightTrigger().whileTrue(drivetrain.applyRequest(()-> 
            drive.withRotationalRate(-MaxAngularRate * 0.15)));
        Driver.leftTrigger().whileTrue(drivetrain.applyRequest(()-> 
            drive.withRotationalRate(MaxAngularRate * 0.15)));
        

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        Tuner.back().and(Tuner.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        Tuner.back().and(Tuner.a()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        Tuner.start().and(Tuner.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        Tuner.start().and(Tuner.a()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        Tuner.rightBumper().and(Tuner.x()).whileTrue(drivetrain.sysIdDynamic());
        Tuner.rightBumper().and(Tuner.b()).whileTrue(drivetrain.sysIdDynamic());
        Tuner.rightTrigger().and(Tuner.x()).whileTrue(drivetrain.);

        // reset the field-centric heading on left bumper press
        Driver.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        drivetrain.registerTelemetry(logger::telemeterize);

    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is
    // pressed,
    // cancelling on release.

    CoDriver.a().onTrue(new ElevatorPivot(1));
    CoDriver.x().onTrue(new ElevatorPivot(2));
    CoDriver.y().onTrue(new ElevatorPivot(3));
    CoDriver.b().onTrue(new ElevatorPivot(4));
    CoDriver.leftBumper().onTrue(new ElevatorPivot(5));

    Driver.y().whileTrue(new ClimbNOW());
    // Driver.a().onTrue(new ResetClimber(m_climber));


    Driver.x().onTrue(new ActivateIntake());
    Driver.b().onTrue(new Outtake());

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
    // return Autos.exampleAuto(m_exampleSubsystem);
    /* Run the path selected from the auto chooser */
    return autoChooser.getSelected();

  }
}
